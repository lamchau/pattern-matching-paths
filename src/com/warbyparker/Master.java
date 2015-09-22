package com.warbyparker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class Master {
  private static final int MAX_SUPPORTED_THREADS = Runtime.getRuntime().availableProcessors();

  private final List<SimplePattern> patterns;
  private final Map<Integer, List<SimplePattern>> partitions;

  public Master(List<String> rawPatterns) {
    this.patterns = toSimplePattern(rawPatterns);
    this.partitions = partition(this.patterns);
  }

  public void process(List<String> paths) {
    ExecutorService exec = Executors.newScheduledThreadPool(MAX_SUPPORTED_THREADS);
    List<Future<String>> results = new ArrayList<Future<String>>();
    for (String path : paths) {
      path = trimPath(path);

      // depth must match field count
      int depth = StringUtils.occurrences(path, SimplePattern.PATH_SEPARATOR) + 1;
      List<SimplePattern> list = this.partitions.get(depth);
      Worker worker = new Worker(list, path);
      results.add(exec.submit(worker));
    }

    try {
      for (Future<String> f : results) {
        try {
          System.out.println(f.get());
        } catch (ExecutionException e) {
          // should not happen, skipped failed results
          System.out.println("FAILED");
          e.printStackTrace();
        }
      }
    } catch (InterruptedException e) {
      System.out.println("Program interrupted");
      e.printStackTrace();
    } finally {
      exec.shutdownNow();
    }
  }

  // expose for testing
  static List<SimplePattern> toSimplePattern(List<String> rawPatterns) {
    if (rawPatterns == null || rawPatterns.isEmpty()) {
      return Collections.emptyList();
    }

    List<SimplePattern> patterns = new ArrayList<SimplePattern>();
    for (String p : rawPatterns) {
      SimplePattern pattern = new SimplePattern(p);
      patterns.add(pattern);
    }
    Collections.sort(patterns);
    return Collections.unmodifiableList(patterns);
  }

  // Creates a partition of a pattern list. This can be leveraged this later for
  // use with a load balancer.
  static Map<Integer, List<SimplePattern>> partition(List<SimplePattern> patterns) {
    if (patterns == null || patterns.isEmpty()) {
      return Collections.emptyMap();
    }

    final Map<Integer, List<SimplePattern>> map = new HashMap<Integer, List<SimplePattern>>();

    int size = patterns.size();
    int startIndex = 0;
    int previous = -1;
    for (int i = 0; i < size; i++) {
      SimplePattern p = patterns.get(i);
      if (i == 0) {
        previous = p.getFieldCount();
      } else if (previous != p.getFieldCount()) {
        map.put(previous, Collections.unmodifiableList(
            patterns.subList(startIndex, i)));

        previous = p.getFieldCount();
        startIndex = i;
      }
    }
    if (previous > 0) {
      map.put(previous, Collections.unmodifiableList(
          patterns.subList(startIndex, size)));
    }
    return Collections.unmodifiableMap(map);
  }

  static String trimPath(String str) {
    return StringUtils.trim(str, SimplePattern.PATH_SEPARATOR).trim();
  }
}
