package arrays;

import java.util.Arrays;

public class BubbleSort {
    public static void main(String[] args) {
        int arr[] = {7,2, 4, 1, 3,7,10,0,5,23};
        System.out.println("input:"+ Arrays.toString(arr));
        sort(arr);
        System.out.println("sorted:"+Arrays.toString(arr));
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
