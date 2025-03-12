package arrays;

import java.util.HashMap;

public class Contains_Duplicate_217
{
    public static void main(String[] args) {
        int [] nums =  {1,2,3,1};

        int k=3;

        System.out.println(nums.length);
        boolean contains=containsNearbyDuplicate(nums,k);
        System.out.println(contains);
    }

    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        HashMap<Integer,Integer> map= new HashMap<Integer,Integer>();
        for (int i = 0; i < nums.length; i++) {
            if(map.containsKey(nums[i])){
                return true;
            }else{
                map.put(nums[i],i);
            }

        }
        return false;
    }
}
