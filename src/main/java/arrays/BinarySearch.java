package arrays;

/**
 * 1 2 3 4 5 6 7 8 9 10 11 1 2 3 4 5 6 7 8 9 10 11 12
 *
 * @author ven.ayya
 */

public class BinarySearch {

  private void search(int target, int[] nums) {
    int n = nums.length;
    System.out.println("n:" + n);
    int halfIndex = 0;
    if (n % 2 == 0) {
      halfIndex = n / 2;
    } else {
      halfIndex = (n + 1) / 2;
    }
    System.out.println("half:" + halfIndex);
    int start = 0;
    int end = n - 1;

    if (target == nums[end]) {
      System.out.println("found at nums[" + end + "]:" + nums[end]);
    } else if (target == nums[start]) {
      System.out.println("found at nums[" + start + "]:" + nums[start]);
    } else if (target == nums[halfIndex - 1]) {
      System.out.println("found at nums[" + (halfIndex - 1) + "]:" + nums[halfIndex - 1]);
    } else {
      System.out.println("not found");
    }
  }

  public static void main(String args[]) {
    int nums[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    int target = 11;
    new BinarySearch().search(target, nums);
  }
}
