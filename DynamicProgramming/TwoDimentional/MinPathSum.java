package DynamicProgramming.TwoDimentional;

import java.util.Arrays;

public class MinPathSum {

    /*
     * Find a path from top left i.e. (0, 0) to the bottom right i.e. ('N' - 1, 'M' - 1)
     *  which minimizes the sum of the cost of all the numbers along the path.
     *  You need to tell the minimum sum of that path.
     * 
     * https://www.naukri.com/code360/problems/minimum-path-sum_985349?leftPanelTabValue=PROBLEM
     */

    public static int minSumPath(int[][] grid) {
    	int n = grid.length;
        int m = grid[0].length;

        int dp[][] = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -1);
        }

        solve(0, 0, n, m, grid,  dp);

        return dp[0][0];
    }

    static int solve(int i, int j, int n, int m, int mat[][], int dp[][]) {
        if (i == n - 1 && j == m - 1) {
            return mat[i][j];
        }

        if (i >= n || j >= m) {
            return Integer.MAX_VALUE;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }
  
        return dp[i][j] = Integer.min(solve(i + 1, j, n, m, mat, dp), solve(i, j + 1, n, m, mat, dp)) + +mat[i][j];
    }
    
    public static void main(String[] args) {
        int grid[][] = {
                { 8, 1, 6 },
                { 4, 4, 16 },
                { 2, 7, 20 },
                { 20, 7, 20 }
            };

        System.out.println(MinPathSum.minSumPath(grid));
    }
}
