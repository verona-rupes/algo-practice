package slidingWindow.easy;

/**
 * Find max sum of subarray of fixed size K
 */
public class MaxSumSubArray {

    public static void main(String args[]){
        int maxSum=findMaxSumOfSubArray(new int []{4,2,1,7,8,1,2,8,1,0}, 3);
        System.out.println(maxSum);
    }

    public static  int findMaxSumOfSubArray(int[] num, int k){
        int maxSum=Integer.MIN_VALUE;
        int currentMaxSum = 0;
        for (int i = 0; i < num.length; i++) {
            System.out.println("i:"+i+",num[i]:"+num[i]);
            currentMaxSum+=num[i];
            System.out.println("currentMaxSum:"+currentMaxSum);
            if(i>=k-1){
                maxSum=Math.max(maxSum,currentMaxSum);
                System.out.println("maxSum:"+maxSum);
                int next=i-(k-1);
                System.out.println("next:"+next +",num[next]:"+num[next]);
                currentMaxSum-=num[next];
                System.out.println("currentMaxSum-:"+currentMaxSum);
                System.out.println("#######");
            }
        }
        return maxSum;
    }
}
