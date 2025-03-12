package slidingWindow.easy;
// https://leetcode.com/problems/longest-nice-substring/

public class LongestSubStr_1763 {
    public static void main(String[] args) {
        final String s = "yabABB";
        String result = new LongestSubStr_1763().longestNiceSubstring(s);
        System.out.println(result);

    }

    public String longestNiceSubstring(String s) {
        String maxSubStr = "";
        int maxLength = 0;
        for (int i = 0; i < s.length(); i++) {
            System.out.println("##########");
            String currentSub = String.valueOf(s.charAt(i));
            for (int j = i + 1; j < s.length(); j++) {
                currentSub += String.valueOf(s.charAt(j));
                if (isNice(currentSub) && currentSub.length() > maxLength) {
                    maxSubStr = currentSub;
                    maxLength = currentSub.length();
                }
            }
        }
        return maxSubStr;
    }

    private boolean isNice(String str) {
        for (int i = 0; i < str.length(); i++) {
            String strAt=String.valueOf(str.charAt(i));
            if(Character.isUpperCase(str.charAt(i))){
                if(!str.contains(strAt.toLowerCase())){
                    return false;
                }
            }else if(Character.isLowerCase(str.charAt(i))){
                if(!str.contains(strAt.toUpperCase())){
                    return false;
                }
            }

        }
        return true;
    }

}
