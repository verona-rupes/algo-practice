package strings;

//https://leetcode.com/problems/longest-substring-without-repeating-characters/
public class LongestSubstring {

  public static int lengthOfLongestSubstring(String s) {
    if (s == null || s.length() == 0) {
      return 0;
    }
    if (s.length() == 1) {
      return 1;
    }

    String longPair = null;

    for (int k = 0; k < s.length(); k++) {
      String pair = s.substring(k, k + 1);
      System.out.println("Start Pair:" + pair);
      for (int i = k + 1; i < s.length(); i++) {
        String sub = s.substring(i, i + 1);
        System.out.println("Chars:" + sub);
        if (pair.contains(sub)) {
          System.out.println("#####");
          break;
        }
        pair += sub;

      }
      System.out.println("##Pair:" + pair);

      if (longPair == null || (pair.length() > longPair.length())) {
        longPair = pair;
      }
    }

    System.out.println("LongestPair:" + longPair);
    return longPair.length();
  }

  public static void main(String args[]) {
    String input = "abcdef";
    int len = lengthOfLongestSubstring(input);
  }
}
