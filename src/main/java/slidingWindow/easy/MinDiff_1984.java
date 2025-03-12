package slidingWindow.easy;

import java.util.Arrays;

//https://leetcode.com/problems/minimum-difference-between-highest-and-lowest-of-k-scores/
public class MinDiff_1984 {
    public static void main(String[] args) {
        System.out.println("1:##########");
        int minDiff = minimumDifference1(new int[]{3, 4, 7, 1}, 3);
        System.out.println(minDiff);
        System.out.println("2:##########");
        int minDiff2 = minimumDifference2(new int[]{3, 4, 7, 1}, 3);
        System.out.println(minDiff2);

        System.out.println("3:##########");
        int minDiff3 = minimumDifference3(new int[]{3, 4, 7, 1}, 3);
        System.out.println(minDiff3);

    }
    //[1,3,4,7]
    //[1,3,4] [1,4,7]  [3,4,7]

    // 3,4,6,6
    public static int minimumDifference1(int[] nums, int k) {
        Arrays.sort(nums);
        int minDiff = Integer.MAX_VALUE;
        System.out.println(Arrays.toString(nums));
        for (int i = (k - 1); i <= nums.length - 1; i++) {
            int windowStart = i - (k - 1);
            System.out.println(nums[windowStart] + "," + nums[i]);
            minDiff = Math.min(minDiff, nums[i] - nums[windowStart]);
        }
        return minDiff;
    }

    public static int minimumDifference2(int[] nums, int k) {
        Arrays.sort(nums);
        int res = Integer.MAX_VALUE;
        for (int i = 0; i + k <= nums.length; i++) {
            int windowEnd = k + i - 1;
            System.out.println(nums[windowEnd] + "," + nums[i]);
            res = Math.min(res, nums[windowEnd] - nums[i]);
        }
        return res;

    }

    public static int minimumDifference3(int[] nums, int k) {
        int minDiff = Integer.MAX_VALUE;
        Arrays.sort(nums);
        int l=0;
        int r=k-1;
        while (r<nums.length){
            System.out.println(nums[l] + "," + nums[r]);
            minDiff=Math.min(minDiff,nums[r]-nums[l]);
            l++;
            r++;
        }
        return minDiff;
    }
}

