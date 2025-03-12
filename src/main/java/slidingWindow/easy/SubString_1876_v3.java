package slidingWindow.easy;

import java.util.HashSet;

//https://leetcode.com/problems/substrings-of-size-three-with-distinct-characters/
public class SubString_1876_v3 {

    public static void main(String[] args) {
        String s = "abcabcbb";
        int count = countGoodSubstrings(s);
        System.out.println(count);

    }

    public static int countGoodSubstrings(String s) {
        int count = 0;
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            int windowEnd = i + 3;
            String subStr = "";
            HashSet<Character> set = new HashSet<Character>();
            for (int j = i; j < windowEnd && windowEnd <= charArray.length; j++) {
                if (!set.contains(charArray[j])) {
                    set.add(charArray[j]);
                    subStr += charArray[j];
                    if (subStr.length() == 3) {
                        count++;
                        subStr = "";
                    }
                }


            }

        }
        return count;
    }

}
