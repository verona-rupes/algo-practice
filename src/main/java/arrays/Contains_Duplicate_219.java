package arrays;

import java.util.HashMap;

import static java.lang.Math.abs;

//https://leetcode.com/problems/contains-duplicate-ii/
public class Contains_Duplicate_219 {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3,5,6,7,8,2,3,1};
        int k = 3;
        System.out.println(nums.length);
        boolean contains = containsNearbyDuplicate(nums, k);
        System.out.println(contains);

    }

    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                if (abs(map.get(nums[i]) - i) <= k) {
                    return true;
                } else {
                    map.put(nums[i], i);
                }
            } else {
                map.put(nums[i], i);
            }

        }
        return false;
    }

}
