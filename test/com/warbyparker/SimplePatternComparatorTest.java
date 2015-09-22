package com.warbyparker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class SimplePatternComparatorTest {
  private static final SimplePatternComparator COMPARATOR = SimplePatternComparator.NATURAL;
  private static final char WILDCARD = SimplePattern.WILDCARD;

  @Test
  public void testCalculateRank_Wildcard() {
    int rank = SimplePatternComparator.calculateRank(WILDCARD);
    Assert.assertNotEquals((int) WILDCARD, rank);
  }

  @Test
  public void testCalculateRank_WildcardComparison() {

    int rankA = SimplePatternComparator.calculateRank(WILDCARD);
    int rankB = SimplePatternComparator.calculateRank(Character.MAX_VALUE);
    Assert.assertTrue(rankA > rankB);
  }

  @Test
  public void testComparator() {
    SimplePattern shared = new SimplePattern("*,*,*,*");
    List<SimplePattern> list = Arrays.asList(
        new SimplePattern("b"),
        new SimplePattern("*,*,*"),
        new SimplePattern("*,*"),
        null,
        new SimplePattern("a"),
        new SimplePattern("*"),
        shared,
        new SimplePattern("foo,bar"),
        new SimplePattern("foo")
    );
    Collections.shuffle(list);
    list.sort(COMPARATOR);
    SimplePattern[] expected = {
        shared,
        new SimplePattern("*,*,*"),
        new SimplePattern("foo,bar"),
        new SimplePattern("*,*"),
        new SimplePattern("a"),
        new SimplePattern("b"),
        new SimplePattern("foo"),
        new SimplePattern("*"),
        null
    };
    Assert.assertArrayEquals(expected, list.toArray());
  }

  @Test
  public void testComparator_AfterNull() {
    SimplePattern a = new SimplePattern("a");
    Assert.assertEquals(-1, COMPARATOR.compare(a, null));
  }

  @Test
  public void testComparator_BeforeNull() {
    SimplePattern a = new SimplePattern("a");
    Assert.assertEquals(1, COMPARATOR.compare(null, a));
  }

  @Test
  public void testComparator_Same() {
    SimplePattern a = new SimplePattern("a");
    SimplePattern b = new SimplePattern("a");

    Assert.assertEquals(0, COMPARATOR.compare(a, b));
  }

  @Test
  public void testComparator_SameInstance() {
    SimplePattern a = new SimplePattern("a");
    Assert.assertEquals(0, COMPARATOR.compare(a, a));
  }
}