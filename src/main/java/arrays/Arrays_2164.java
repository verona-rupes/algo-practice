package arrays;

import java.util.ArrayList;
import java.util.Arrays;

//https://leetcode.com/problems/sort-even-and-odd-indices-independently/
/*
    odd - desc
    even - asec
 */
public class Arrays_2164 {
    public static void main(String[] args) {
        int[] nums = {4, 1, 2, 3,7,10,5};
        System.out.println(Arrays.toString(sortEvenOdd(nums)));

        int[] nums1 = {4, 1};
        System.out.println(Arrays.toString(sortEvenOdd(nums1)));

        int[] nums2 = {4};
        sortEvenOdd(nums2);
        System.out.println(Arrays.toString(sortEvenOdd(nums2)));

        int[] nums3 = {};
        sortEvenOdd(nums3);
        System.out.println(Arrays.toString(sortEvenOdd(nums3)));


        int[] nums4 = {-1};
        sortEvenOdd(nums4);
        System.out.println(Arrays.toString(sortEvenOdd(nums4)));


        int[] nums5 = {4, 1, 2, 3};
        sortEvenOdd(nums5);
        System.out.println(Arrays.toString(sortEvenOdd(nums5)));

    }

    public static int[] sortEvenOdd(int[] nums) {

        ArrayList<Integer> oddList = new ArrayList();
        ArrayList<Integer>  evenList = new ArrayList();

        for (int i = 0; i <nums.length ; i++) {
            boolean b = (i % 2 == 0) ? evenList.add(nums[i]) : oddList.add(nums[i]);
        }
        int even[]= new int[evenList.size()];
        int odd[] = new int[oddList.size()];
        for (int i = 0; i < evenList.size(); i++) {
            even[i]=evenList.get(i);
        }

        for (int i = 0; i < oddList.size(); i++) {
            odd[i]=oddList.get(i);
        }
        sortEvenOrOdd(even,true);
        sortEvenOrOdd(odd,false);

        int[] merge= new int[even.length+odd.length];

//        System.out.println(Arrays.toString(even));
//        System.out.println(Arrays.toString(odd));

        int evenIndex=0;
        int oddIndex=0;

        for (int i = 0; i <merge.length ; i++) {
            if(i%2==0){
               merge[i]= even[evenIndex];
               evenIndex++;
            }else{
                merge[i]=odd[oddIndex];
                oddIndex++;
            }
        }

        return merge;
    }

    public static void sortEvenOrOdd(int[] arr, boolean asec) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (asec && arr[i] > arr[j]) {
                    swap(arr, i, j);
                } else if (!asec && arr[i] < arr[j]) {
                    swap(arr, i, j);
                }
            }
        }

    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }
}
