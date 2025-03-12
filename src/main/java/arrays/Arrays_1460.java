package arrays;

//https://leetcode.com/problems/make-two-arrays-equal-by-reversing-sub-arrays/
//Make Two Arrays Equal by Reversing Sub-arrays
public class Arrays_1460 {

    public static boolean canBeEqual(int[] target, int[] arr) {
        //check length
        if (target == null) return false;
        if (arr == null) return false;
        if (arr == target) return true;
        if (target.length != arr.length) return false;
        //check if array contains same elements
        sort(target);
        sort(arr);
        for (int i = 0; i < target.length; i++) {
            if (target[i] != arr[i]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int target[] = {1, 2, 3, 4,5};
        int arr[] = {5,2, 4,6, 1, 3};
        System.out.println(canBeEqual(target, arr));
    }

    public static void sort(int [] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (arr[i] > arr[j]){
                    int temp=arr[i];
                    arr[i]=arr[j];
                    arr[j]=temp;
                }
            }
        }
    }


}
