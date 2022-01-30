package search;


// [1,4,5,8,10,19,21,24,32,41,50]
public class BinarySearch {

  private static void binarysearch(int target, int nums[]) {

    int left = 0;
    int right = (nums.length);

    while (left <= right) {
      System.out.println("left:" + left);
      System.out.println("right:" + right);
      int mid = (left + (right - left) / 2);
      System.out.println("mid:" + mid);
      if (target < nums[mid]) {
        right = mid;
      } else if (target > nums[mid]) {
        left = mid;
      } else if (target == nums[mid]) {
        System.out.println("found at:" + mid);
      }
    }

  }

  public static void main(String args[]) {
    int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    int target = 11;
    binarysearch(target, nums);
  }

}
