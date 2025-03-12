package slidingWindow.easy;

import java.util.HashMap;
import java.util.HashSet;

//https://leetcode.com/problems/substrings-of-size-three-with-distinct-characters/
public class SubString_1876_v2 {

    public static void main(String[] args) {
        String s = "abcabcbb";
        int count = countGoodSubstrings(s);
        System.out.println(count);

    }

    public static int countGoodSubstrings(String s) {
        int count = 0;
        char[] charArray = s.toCharArray();
        //['x','y','z','z','a','z',x,a]
        for (int i = 0; i < charArray.length; i++) {
            int windowEnd = i + 3;
            String subStr = "";
            for (int j = i; j < windowEnd && windowEnd <= charArray.length; j++) {
                if (subStr.indexOf(charArray[j]) == -1) {
                    subStr += charArray[j];
                    if (subStr.length() == 3) {
                        System.out.println(j + ":" + subStr);
                        count++;
                        subStr = "";
                    }
                }


            }

        }
        return count;
    }

}
