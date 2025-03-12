package slidingWindow.easy;

import java.util.ArrayList;

//https://leetcode.com/problems/sliding-window-maximum/
public class SlidingWindowMax_239 {

    public static void main(String[] args) {
        int nums[] = {1, 3, -1, -3, 5, 3, 6, 8};
        int k = 3;
        new SlidingWindowMax_239().maxSlidingWindow(nums, k);
        System.out.println("###################");
        new SlidingWindowMax_239().maxSlidingWindow1(nums, k);
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        ArrayList<Integer> cuurentMaxInts = new ArrayList<Integer>();
        int windowEnd=0;
        int windowStart=0;
        for (int i = 0; i < nums.length && windowEnd< (nums.length-1); i++) {
            windowStart = i;
            windowEnd = windowStart + (k-1);
            System.out.println("["+nums[windowStart]+","+nums[windowEnd]+"]");
        }

        return cuurentMaxInts.stream().mapToInt(i -> i.intValue()).toArray();
    }

    public int[] maxSlidingWindow1(int[] nums, int k){
        ArrayList<Integer> cuurentMaxInts = new ArrayList<Integer>();
        int windowStart=0;
        for (int windowEnd = 0; windowEnd < nums.length ; windowEnd++) {
             if(windowEnd >=k){
                windowStart++;

             }
            System.out.println("["+nums[windowStart]+","+nums[windowEnd]+"]");

        }

        return cuurentMaxInts.stream().mapToInt(i -> i.intValue()).toArray();
    }
}
