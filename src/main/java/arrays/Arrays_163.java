package arrays;

import java.util.*;

//https://leetcode.com/problems/missing-ranges/
public class Arrays_163 {

    public static void main(String[] args) {
        int lower = -2;
        int upper = -1;
        int[] nums = {-1};
        findMissingRanges(nums, lower, upper);
    }

    // -2  [-2,-1]  -1
    public static List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> ranges = new ArrayList<>();
        if(lower== upper && nums.length==0){
            ranges.add(String.valueOf(lower));
            System.out.println(ranges);
            return ranges;
        }
        if(lower== upper && nums.length==1 && nums[0]==lower){
            System.out.println(ranges);
            return ranges;
        }

        if(lower != upper && nums.length==0){
            ranges.add(String.valueOf(lower) + "->"+ String.valueOf(upper));
            return ranges;
        }

        //lower
        if (nums.length>0 && nums[0] - lower > 1) {
            if (lower + 1 !=nums[0] - 1) {
                ranges.add(String.valueOf(lower + 1) + "->"+ String.valueOf(nums[0] - 1));
            }else{
                ranges.add(String.valueOf(lower + 1));
            }

        }
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] > 1) {
                if(nums[i - 1] + 1 != nums[i] - 1){
                    ranges.add(String.valueOf(nums[i - 1] + 1) + "->"+String.valueOf(nums[i] - 1));
                }else{
                    ranges.add(String.valueOf(nums[i - 1] + 1));
                }

            }

        }
        //uppper
        if (nums.length>0 && upper - nums[nums.length - 1] > 1) {
            if(nums[nums.length - 1] + 1 != upper){
                ranges.add(String.valueOf(nums[nums.length - 1] + 1) + "->"+String.valueOf(upper));

            }else if(nums.length>0){
                ranges.add(String.valueOf(nums[nums.length - 1] + 1));
            }

        }
        System.out.println(ranges);
        return ranges;
    }
}
