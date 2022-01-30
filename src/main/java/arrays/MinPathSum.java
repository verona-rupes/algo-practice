package arrays;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/minimum-path-sum/
 *
 * @author ven.ayya [ [1,3,1], [1,5,1 ], [4,2,1], [1,2,1 ] ]
 */
public class MinPathSum {


  public static int minPathSum(int[][] grid) {

    return 0;

  }

  public static void main(String args[]) {
    int[][] grid = {{3, 3, 1}, {1, 5, 1}, {4, 2, 1}, {5, 2, 1}};

    int sum;
    int last = 0;
    int iIndex = 0;
    int jIndex = 0;

    for (int i = 0; i < grid.length; i++) {
      System.out.println(Arrays.toString(grid[i]));
      for (int j = 0; j < grid[i].length; j++) {
        if (last == 0 || grid[i][j] < last) {
          last = grid[i][j];
          jIndex = j;
          iIndex = i;
          System.out.println("[" + i + "," + j + "]:" + last);

        }

      }

    }

  }

}
