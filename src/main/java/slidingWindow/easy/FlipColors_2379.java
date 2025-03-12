package slidingWindow.easy;

//https://leetcode.com/problems/minimum-recolors-to-get-k-consecutive-black-blocks/
public class FlipColors_2379 {
    public static void main(String[] args) {
        int minFlips = minimumRecolors2("WBBWWBBWBW", 7);
        System.out.println("Min Flips:" + minFlips);
    }

    public static int minimumRecolors(String blocks, int k) {
        int MIN_FLIPS = Integer.MAX_VALUE;
        int l = 0;
        int r = k - 1;
        char[] charArray = blocks.toCharArray();
        while (r < charArray.length) {
            // System.out.println(charArray[l] + "," + charArray[r]);
            System.out.println(blocks.substring(l, r + 1));
            String subStr = blocks.substring(l, r + 1);
            int i = 0;
            int flip = 0;
            while (i < subStr.length()) {
                if (subStr.charAt(i) == 'W') {
                    flip++;
                }
                i++;
            }
            // System.out.println();
            System.out.println("flips:" + flip);
            MIN_FLIPS = Math.min(MIN_FLIPS, flip);
            //  System.out.println("################");
            l++;
            r++;
        }
        return MIN_FLIPS;
    }

    public static int minimumRecolors2(String blocks, int k) {
        int n = blocks.length(), minOps = Integer.MAX_VALUE, flips = 0, count = 0, i = 0;
        for (int j = 0; j < n; ++j) {
            if (blocks.charAt(j) == 'W') {
                flips++;
                count++;
            } else if (blocks.charAt(j) == 'B') {
                count++;
            }
            if (count == k) {
                minOps = Math.min(minOps, flips);
                if (blocks.charAt(i) == 'W') {
                    flips--;
                    count--;
                } else {
                    count--;
                }
                i++;
            }
        }
        return minOps;
    }

    public static int minimumRecolors3(String blocks, int k) {
        int MIN_FLIPS = Integer.MAX_VALUE;
        int l = 0;
        int r = k - 1;
        char[] charArray = blocks.toCharArray();
        while (r < charArray.length) {
            // System.out.println(blocks.substring(l, r + 1));
            int flip = 0;
            String str = "";
            for (int i = l; i <= r; i++) {
                if (charArray[i] == 'W') {
                    flip++;
                }
                str += charArray[i];
            }
//            System.out.println(str);
//            System.out.println("flips:"+flip);
            MIN_FLIPS = Math.min(MIN_FLIPS, flip);
            //  System.out.println("################");
            l++;
            r++;
        }
        return MIN_FLIPS;
    }

}
