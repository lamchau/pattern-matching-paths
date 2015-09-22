package com.warbyparker;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

class Worker implements Callable<String> {
  private final List<SimplePattern> patterns;
  private final String path;

  public Worker(List<SimplePattern> patterns, String path) {
    this.patterns = patterns == null ? Collections.<SimplePattern>emptyList() : patterns;
    this.path = path;
  }

  @Override
  public String call() throws Exception {
    for (SimplePattern pattern : this.patterns) {
      if (pattern.match(path)) {
        return pattern.getRawPattern();
      }
    }
    return "NO MATCH";
  }
}
