package com.warbyparker;

import java.util.Comparator;

enum SimplePatternComparator implements Comparator<SimplePattern> {
  NATURAL {
    @Override
    public int compare(SimplePattern p1, SimplePattern p2) {
      if (p1 == p2) {
        return EQUAL;
      } else if (p1 == null) {
        return AFTER;
      } else if (p2 == null) {
        return BEFORE;
      }

      // rank by number of fields (descending)
      int fields = p2.getFieldCount() - p1.getFieldCount();
      if (fields != EQUAL) {
        return fields;
      }

      // rank by number of wildcards (ascending)
      int wildcards = p1.getWildCardCount() - p2.getWildCardCount();
      if (wildcards != EQUAL) {
        return wildcards;
      }

      String a = p1.getRawPattern();
      String b = p2.getRawPattern();

      int lengthA = a.length();
      int lengthB = b.length();
      int limit = Math.min(lengthA, lengthB);
      for (int i = 0; i < limit; i++) {
        int c1 = calculateRank(a.charAt(i));
        int c2 = calculateRank(b.charAt(i));
        if (c1 != c2) {
          return c1 - c2;
        }
      }
      return lengthA - lengthB;
    }
  };

  private static final int BEFORE = -1;
  private static final int EQUAL = 0;
  private static final int AFTER = 1;
  // leverage max char + 1 rollover to integer
  private static final int MAX_CHAR_VALUE = Character.MAX_VALUE + 1;

  public static int calculateRank(char ch) {
    switch (ch) {
      case SimplePattern.WILDCARD:
        return MAX_CHAR_VALUE;
    }
    return ch;
  }
}