package slidingWindow.easy;

import java.util.HashSet;

//https://leetcode.com/problems/substrings-of-size-three-with-distinct-characters/
public class SubString_1876_v4 {

    public static void main(String[] args) {
        String s = "aababcabc";
        System.out.println("len:"+s.length());
        int count = countGoodSubstrings(s);
        System.out.println("###:"+count);

    }

    public static int countGoodSubstrings(String s) {
        int result = 0;
        for(int i=0;i<s.length()-2;i++) {
            System.out.println(i+":"+Character.toString(s.charAt(i))+Character.toString(s.charAt(i+1))+Character.toString(s.charAt(i+2)));
            if(s.charAt(i)!=s.charAt(i+1) && s.charAt(i+1)!=s.charAt(i+2)&&s.charAt(i+2)!=s.charAt(i)){
                result++;
            }

        }
        return result;
    }


}

