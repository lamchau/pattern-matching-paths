package com.warbyparker;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

  @Test
  public void testOccurrences() {
    int occurrences = StringUtils.occurrences("a,b,c,d,e", ',');
    Assert.assertEquals(4, occurrences);
  }

  @Test
  public void testOccurrences_Null() {
    int occurrences = StringUtils.occurrences(null, ',');
    Assert.assertEquals(0, occurrences);
  }

  @Test
  public void testTrim() {
    String str = StringUtils.trim("/a/b/c/", '/');
    Assert.assertEquals("a/b/c", str);
  }

  @Test
  public void testTrim_Null() {
    String str = StringUtils.trim(null, '/');
    Assert.assertEquals("", str);
  }

  @Test
  public void testTrim_Same() {
    String str = StringUtils.trim("x", '/');
    Assert.assertEquals("x", str);
  }

  @Test
  public void testTrim_Start() {
    String str = StringUtils.trim("/ab", '/');
    Assert.assertEquals("ab", str);
  }

  @Test
  public void testTrim_End() {
    String str = StringUtils.trim("ab/", '/');
    Assert.assertEquals("ab", str);
  }

  @Test
  public void testTrim_SingleCharacter() {
    String str = StringUtils.trim("/", '/');
    Assert.assertEquals("", str);
  }

  @Test
  public void testTrim_Space() {
    String str = StringUtils.trim(" / a / b / c / ", ' ');
    Assert.assertEquals("/ a / b / c /", str);
  }
}