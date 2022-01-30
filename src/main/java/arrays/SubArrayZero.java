package arrays;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author ven.ayya
 * @see <a href= "http://www.techiedelight.com/find-pair-with-given-sum-array/"> Find pair with
 * given sum in the array</a>
 */
public class SubArrayZero {

  static int[] arr;
  static int sum;
  static int n;

  private static void subArray() {

  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    n = in.nextInt();
    arr = new int[n];
    for (int i = 0; i < n; i++) {
      arr[i] = in.nextInt();
    }

    sum = in.nextInt();

    System.out.println(Arrays.toString(arr));
    System.out.println(sum);

    in.close();

  }

}
