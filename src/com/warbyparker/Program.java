package com.warbyparker;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class Program {
  static class Result {
    final List<String> paths;
    final List<String> patterns;
   
    public Result(List<String> patterns, List<String> paths) {
      this.patterns = patterns;
      this.paths = paths;
    }
  }

  private static final void closeQuietly(Closeable stream) {
    if (stream != null) {
      try {
        stream.close();
      } catch (IOException e) {
      }
    }
  }

  public static void main(String[] args) throws IOException {
    InputStream input = null;

    try {
      input = args.length == 1 ? new FileInputStream(args[0]) : System.in;

      Result result = processInput(input);
      Master master = new Master(result.patterns);
      master.process(result.paths);
    } catch (IOException e) {
      System.err.println("Invalid input");
      e.printStackTrace();
    } finally {
      closeQuietly(input);
    }
  }

  static Result processInput(InputStream inputStream) throws IOException {
    BufferedReader reader = null;
    List<String> patterns = new ArrayList<String>();
    List<String> paths = new ArrayList<String>();

    String line;
    int lineNumber = 0;
    int limit = -1;
    boolean collectPaths = false;
    try {
      reader = new BufferedReader(new InputStreamReader(inputStream));
      while ((line = reader.readLine()) != null && line.length() != 0) {
        if (lineNumber == 0) {
          limit = Integer.parseInt(line);
        } else if (collectPaths) {
          if (collect(paths, line, limit)) {
            break;
          }
        } else if (collect(patterns, line, limit)) {
          collectPaths = true;
          limit = Integer.parseInt(line);
        }
        lineNumber++;
      }
    } finally {
      closeQuietly(reader);
    }
    return new Result(patterns, paths);
  }

  static <T> boolean collect(List<T> list, T line, int limit) {
    assert list != null;
    if (list.size() == limit) {
      return true;
    }
    list.add(line);
    return false;
  }
}