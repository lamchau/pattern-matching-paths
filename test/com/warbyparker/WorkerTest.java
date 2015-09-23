package com.warbyparker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class WorkerTest {
  private static final List<SimplePattern> PATTERNS = Arrays.asList(new SimplePattern("a"));

  @Test
  public void testCallMatch() throws Exception {
    Worker worker = new Worker(PATTERNS, "a");
    Assert.assertEquals(worker.call(), "a");
  }

  @Test
  public void testCallNoMatch() throws Exception {
    Worker worker = new Worker(PATTERNS, "b");
    Assert.assertEquals(worker.call(), "NO MATCH");
  }

  @Test
  public void test() throws Exception {
    List<SimplePattern> patterns = Arrays.asList(
        new SimplePattern("a,b"),
        new SimplePattern("a,b,c"),
        new SimplePattern("foo,bar"),
        new SimplePattern("*,*"),
        new SimplePattern("*,bar")
    );
    Collections.sort(patterns);

    List<String> paths = Arrays.asList(
        "a/bar",
        "hello/world",
        "q",
        "a/b",
        "foo"
    );

    List<String> results = new ArrayList<String>();

    for (String s : paths) {
      Worker worker = new Worker(patterns, s);
      results.add(worker.call());
    }
    Assert.assertArrayEquals(new String[] {
        "*,bar",
        "*,*",
        "NO MATCH",
        "a,b",
        "NO MATCH"
    }, results.toArray());

  }
}
