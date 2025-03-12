package slidingWindow.easy;

import java.util.HashSet;

public class SubString_1876_v1 {
    public static void main(String[] args) {
        String s = "abcabcbb";
        int count = countGoodSubstrings(s);
        System.out.println(count);

    }
    public static int countGoodSubstrings(String s) {

        int windowEnd=3;
        int windowStart=0;
        int count=0;
        while(windowEnd<=s.length()){
            String subStr=s.substring(windowStart,windowEnd);
            System.out.println(subStr);
            if(!hasDuplicates(subStr.toCharArray())) {
                count++;
            }
            windowStart++;
            windowEnd++;

        }

        return count;
    }

    private static boolean hasDuplicates(char[] s){
        HashSet set = new HashSet();

        for (int i = 0; i < s.length; i++) {
            if(set.contains(s[i])){
                return true;
            }
            set.add(s[i]);
        }
        return false;
    }


}
