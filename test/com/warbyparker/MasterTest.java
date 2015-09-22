package com.warbyparker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class MasterTest {
  @Test
  public void testToSimplePattern() {
    List<SimplePattern> result = Master.toSimplePattern(null);
    Assert.assertEquals(Collections.emptyList(), result);
  }

  @Test
  public void testToSimplePattern_Null() {
    List<SimplePattern> result = Master.toSimplePattern(null);
    Assert.assertEquals(Collections.emptyList(), result);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testAsSimplePattern_Unmodifiable() {
    List<SimplePattern> result = Master.toSimplePattern(
        Arrays.asList("a", "b", "c"));
    result.add(new SimplePattern("foo"));
  }

  @Test
  public void testPartition_Empty() {
    Map<Integer, List<SimplePattern>> partitions = Master.partition(null);
    Assert.assertEquals(Collections.emptyMap(), partitions);
  }

  @Test
  public void testPartition_Single() {
    Map<Integer, List<SimplePattern>> partitions = Master.partition(Arrays.asList(
        new SimplePattern("foo")
    ));
    Assert.assertEquals(1, partitions.size());
    Assert.assertEquals(1, partitions.get(1).size());
  }

  @Test
  public void testPartition_Many() {
    List<SimplePattern> patterns = Arrays.asList(
        new SimplePattern("a,b,c"),
        new SimplePattern("a,*,c"),
        new SimplePattern("*,b,c"),
        new SimplePattern("1,2"),
        new SimplePattern("1,*"),
        new SimplePattern("*,*"),
        new SimplePattern("a"),
        new SimplePattern("*")
    );

    Map<Integer, List<SimplePattern>> partitions = Master.partition(patterns);
    SimplePattern[] three = partitions.get(3).toArray(new SimplePattern[3]);
    Assert.assertArrayEquals(new SimplePattern[] {
        new SimplePattern("a,b,c"),
        new SimplePattern("a,*,c"),
        new SimplePattern("*,b,c"),
    },three);

    SimplePattern[] two = partitions.get(2).toArray(new SimplePattern[2]);
    Assert.assertArrayEquals(new SimplePattern[] {
        new SimplePattern("1,2"),
        new SimplePattern("1,*"),
        new SimplePattern("*,*")
    },two);

    SimplePattern[] one = partitions.get(1).toArray(new SimplePattern[1]);
    Assert.assertArrayEquals(new SimplePattern[] {
        new SimplePattern("a"),
        new SimplePattern("*"),
    },one);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testPartition_UnmodifiableKey() {
    List<SimplePattern> patterns = Arrays.asList(
        new SimplePattern("a,b,c")
    );

    Map<Integer, List<SimplePattern>> partitions = Master.partition(patterns);
    partitions.put(1, Collections.<SimplePattern>emptyList());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testPartition_UnmodifiableValue() {
    List<SimplePattern> patterns = Arrays.asList(
        new SimplePattern("a,b,c")
    );

    Map<Integer, List<SimplePattern>> partitions = Master.partition(patterns);
    for (Map.Entry<Integer, List<SimplePattern>> entry : partitions.entrySet()) {
      entry.getValue().add(new SimplePattern("foo"));
    }
  }

  @Test
  public void testProcess() {

  }

  @Test
  public void testTrimPath_Both() {
    String path = Master.trimPath("/a/b/c/");
    Assert.assertEquals("a/b/c", path);
  }

  @Test
  public void testTrimPath_End() {
    String path = Master.trimPath("a/b/c/");
    Assert.assertEquals("a/b/c", path);
  }

  @Test
  public void testTrimPath_None() {
    String path = Master.trimPath("a/b/c");
    Assert.assertEquals("a/b/c", path);
  }

  @Test
  public void testTrimPath_Null() {
    String path = Master.trimPath(null);
    Assert.assertEquals("", path);
  }

  @Test
  public void testTrimPath_Spaces() {
    String path = Master.trimPath(" a/b/c  ");
    Assert.assertEquals("a/b/c", path);
  }

  @Test
  public void testTrimPath_Start() {
    String path = Master.trimPath("/a/b/c");
    Assert.assertEquals("a/b/c", path);
  }
}