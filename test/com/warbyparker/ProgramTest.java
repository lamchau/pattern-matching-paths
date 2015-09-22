package com.warbyparker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class ProgramTest {
  @Test
  public void testMain() {

  }

  @Test(expected = NumberFormatException.class)
  public void testProcessInput_Invalid() throws IOException {
    String str = String.join("\n", Arrays.asList(
        "a",
        "*,*",
        "1",
        "foo"
    ));
    byte[] data = str.getBytes(StandardCharsets.UTF_8);
    InputStream stream = new ByteArrayInputStream(data);
    Program.processInput(stream);
  }

  @Test
  public void testProcessInput() throws IOException {
    String str = String.join("\n", Arrays.asList(
        "1",
        "*,*",
        "2",
        "foo/bar",
        "foo"
    ));
    byte[] data = str.getBytes(StandardCharsets.UTF_8);
    InputStream stream = new ByteArrayInputStream(data);
    Program.Result result = Program.processInput(stream);
    Assert.assertArrayEquals(new String[]{"*,*"}, result.patterns.toArray());
    Assert.assertArrayEquals(new String[]{"foo/bar", "foo"}, result.paths.toArray());
  }

  @Test
  public void testProcessInput_ExtraPaths() throws IOException {
    String str = String.join("\n", Arrays.asList(
        "1",
        "*,*",
        "3",
        "foo/bar",
        "foo"
    ));
    byte[] data = str.getBytes(StandardCharsets.UTF_8);
    InputStream stream = new ByteArrayInputStream(data);
    Program.Result result = Program.processInput(stream);
    Assert.assertArrayEquals(new String[]{"*,*"}, result.patterns.toArray());
    Assert.assertArrayEquals(new String[]{"foo/bar", "foo"}, result.paths.toArray());
  }

  @Test
  public void testCollect_True() {
    Program.collect(Arrays.asList("foo"), "test", 1);
  }

  @Test
  public void testCollect_False() {
    Program.collect(new ArrayList<String>(), "test", 2);
  }
}