package arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author ven.ayya
 * @see <a href="http://www.techiedelight.com/find-pair-with-given-sum-array/">Find pair with given
 * sum in the array</a>
 */
public class FindPair {

  static int[] arr;
  static int sum;
  static int n;


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

    usingMap();

  }

  //brute force approach
  private static void bruteForce() {
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        if (arr[i] + arr[j] == sum) {
          System.out.println("(" + arr[i] + "," + arr[j] + ")");
        }
      }
    }

  }

  private static void usingMap() {
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    for (int i = 0; i < n; i++) {
      if (map.containsKey(sum - arr[i])) {
        System.out.println("(" + arr[i] + "," + (sum - arr[i]) + ")");
      } else {
        map.put(arr[i], i);
      }
    }

  }


}
