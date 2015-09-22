package com.warbyparker;

public enum StringUtils {
  ;
  public static int occurrences(String str, char ch) {
    if (str == null) {
      return 0;
    }

    int length = str.length();
    int occurrences = 0;
    for (int i = 0; i < length; i++) {
      if (str.charAt(i) == ch) {
        occurrences++;
      }
    }
    return occurrences;
  }

  // assumption: unix style path separators only
  public static String trim(String str, char ch) {
    if (str == null) {
      return "";
    }

    int length = str.length();
    if (length == 1 && str.charAt(0) == ch) {
      return "";
    }

    int startIndex = str.indexOf(ch) == 0 ? 1 : 0;
    int endIndex = str.lastIndexOf(ch);
    endIndex = (endIndex == length - 1) ? endIndex : length;

    return str.substring(startIndex, endIndex);
  }
}