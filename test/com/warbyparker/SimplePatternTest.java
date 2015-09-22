package com.warbyparker;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class SimplePatternTest {

  @Test
  public void sanitize() {
    SimplePattern pattern = new SimplePattern(",a,*,c,*****,");
    Assert.assertEquals("a,*,c,*", pattern.getRawPattern());
  }

  @Test
  public void sanitize_Both() {
    SimplePattern pattern = new SimplePattern(",a,");
    Assert.assertEquals("a", pattern.getRawPattern());
  }

  @Test
  public void sanitize_End() {
    SimplePattern pattern = new SimplePattern("a,");
    Assert.assertEquals("a", pattern.getRawPattern());
  }

  @Test
  public void sanitize_Start() {
    SimplePattern pattern = new SimplePattern(",a");
    Assert.assertEquals("a", pattern.getRawPattern());
  }

  @Test
  public void testCompareTo() {
    SimplePattern[] list = {
      new SimplePattern("a,*"),
      new SimplePattern("*,b"),
      new SimplePattern("a,b")
    };
    Arrays.sort(list);
    SimplePattern[] expected = {
      new SimplePattern("a,b"),
      new SimplePattern("a,*"),
      new SimplePattern("*,b")
    };
    Assert.assertArrayEquals(expected, list);
  }

  @Test
  public void testCreateRegEx() {
    SimplePattern pattern = new SimplePattern("a,b,c,*");
    String regex = pattern.getPattern().pattern();
    Assert.assertEquals("^a/b/c/[^//]+$", regex);
  }

  @ Test(expected = IllegalArgumentException.class)
  public void testCreationNull() {
    new SimplePattern(null);
  }

  @Test
  public void testEquals_Equal() {
    SimplePattern p1 = new SimplePattern("a,b,c");
    SimplePattern p2 = new SimplePattern("a,b,c");
    Assert.assertEquals(p1, p2);
  }

  @Test
  public void testEquals_EqualInstance() {
    SimplePattern p1 = new SimplePattern("a,b,c");
    Assert.assertTrue(p1.equals(p1));
  }

  @Test
  public void testEquals_NotEqual() {
    SimplePattern p1 = new SimplePattern("a,b,c");
    SimplePattern p2 = new SimplePattern("a,b");
    Assert.assertNotEquals(p1, p2);
  }

  @Test
  public void testEquals_Null() {
    SimplePattern p1 = new SimplePattern("a,b,c");
    Assert.assertFalse(p1.equals(null));
  }

  @Test
  public void testEquals_Symmetric() {
    SimplePattern x = new SimplePattern("foo,baz");
    SimplePattern y = new SimplePattern("foo,baz");
    Assert.assertTrue(x.equals(y) && y.equals(x));
    Assert.assertTrue(x.hashCode() == y.hashCode());
  }

  @Test
  public void testGetFieldCount_None() {
    SimplePattern pattern = new SimplePattern("a");
    Assert.assertEquals(1, pattern.getFieldCount());
  }

  @Test
  public void testGetFieldCount_Some() {
    SimplePattern pattern = new SimplePattern("a,b,c");
    Assert.assertEquals(3, pattern.getFieldCount());
  }

  @Test
  public void testGetPattern() {

  }

  @Test
  public void testGetRawPattern() {

  }

  @Test
  public void testGetWildCardCount_Consecutive() {
    SimplePattern pattern = new SimplePattern("**");
    Assert.assertEquals(1, pattern.getWildCardCount());
  }


  @Test
  public void testMatch_False() {
    SimplePattern pattern = new SimplePattern("abc");
    Assert.assertFalse(pattern.match(null));
  }

  @Test
  public void testMatch_True() {
    SimplePattern pattern = new SimplePattern("abc");
    Assert.assertTrue(pattern.match("abc"));
  }

  @Test
  public void testRawPattern() {
    SimplePattern pattern = new SimplePattern(" some pattern ");
    Assert.assertEquals("some pattern", pattern.getRawPattern());
  }
}