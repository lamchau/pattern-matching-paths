package com.warbyparker;

import java.util.regex.Pattern;

public final class SimplePattern implements Comparable<SimplePattern> {
  public static final char PATH_SEPARATOR = '/';
  public static final char FIELD_DELIMITER = ',';
  public static final char WILDCARD = '*';
  private static final String WILDCARD_REGEX = "\\" + WILDCARD;
  private static final String PATH_REGEX = "[^" + PATH_SEPARATOR + PATH_SEPARATOR + "]+";

  private final String rawPattern;
  private final int fields;
  private final int wildcards;
  private final Pattern pattern;

  public SimplePattern(String pattern) {
    this.rawPattern = sanitize(pattern);
    this.fields = StringUtils.occurrences(this.rawPattern, FIELD_DELIMITER) + 1;
    this.wildcards = StringUtils.occurrences(this.rawPattern, WILDCARD);
    this.pattern = createRegEx(this.rawPattern);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SimplePattern)) {
      return false;
    }

    String s1 = this.rawPattern;
    String s2 = ((SimplePattern) o).rawPattern;
    return s1.equals(s2);
  }

  @Override
  public int compareTo(SimplePattern o) {
    return SimplePatternComparator.NATURAL.compare(this, o);
  }

  public int getFieldCount() {
    return this.fields;
  }

  public Pattern getPattern() {
    return this.pattern;
  }

  public String getRawPattern() {
    return this.rawPattern;
  }

  public int getWildCardCount() {
    return this.wildcards;
  }

  @Override
  public int hashCode() {
    return rawPattern.hashCode();
  }

  /**
   * Null-safe convenience method. Same as {@code this.getPattern().matcher(path).find()}.
   *
   * @param path the path to match
   * @return <code>true</code> if the path matches, <code>false</code> otherwise
   */
  public boolean match(String path) {
    if (path == null) {
      return false;
    }
    // could improve speed implementing by converting regex to NFA
    // https://en.wikipedia.org/wiki/Thompson%27s_construction_algorithm
    return this.pattern.matcher(path).find();
  }

  private Pattern createRegEx(String pattern) {
    // using native regex for brevity/time constraint
    String regex = pattern
        .replaceAll(String.valueOf(FIELD_DELIMITER), String.valueOf(PATH_SEPARATOR))
        .replaceAll(WILDCARD_REGEX, PATH_REGEX);

    regex = String.format("^%s$", regex);
    return Pattern.compile(regex);
  }

  private String sanitize(String str) {
    if (str == null) {
      throw new IllegalArgumentException("Pattern cannot be null");
    }
    // collapse multiple wildcards for ranking?
    str = str.trim().replaceAll(WILDCARD_REGEX + "+", String.valueOf(WILDCARD));
    return StringUtils.trim(str, FIELD_DELIMITER);
  }
}