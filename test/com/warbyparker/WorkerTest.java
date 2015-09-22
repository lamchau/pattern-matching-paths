package com.warbyparker;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
}