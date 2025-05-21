package DynamicProgramming.TwoDimentional.Grid;

import java.util.Arrays;

public class TotalUniquePathsWithObstacles {

    /*
     * You are given an m x n integer array grid.
     *  There is a robot initially located at the top-left corner (i.e., grid[0][0]).
     *  The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]).
     *  The robot can only move either down or right at any point in time.
     * 
     * An obstacle and space are marked as 1 or 0 respectively in grid.
     * A path that the robot takes cannot include any square that is an obstacle.
     * 
     * https://leetcode.com/problems/unique-paths-ii/description/
     */

    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {

        int n = obstacleGrid.length;
        int m = obstacleGrid[0].length;

        if(n == 1 && m == 1) {
            return obstacleGrid[0][0] == 1 ? 0 : 1;
        }

        if(obstacleGrid[0][0] == 1) return 0;

        int dp[][] = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -1);
        }

        solve(0, 0, n, m, obstacleGrid, dp);

        return dp[0][0];
    }
    
    static int solve(int i, int j, int n, int m, int mat[][], int dp[][]) {
        if (i == n - 1 && j == m - 1) {
            if (mat[i][j] == 0) {
                return 1;
            }
            return 0;
        }

        if (i >= n || j >= m) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        if (mat[i][j] == 1) {
            return 0;
        }

        return dp[i][j] = solve(i + 1, j, n, m, mat, dp) + solve(i, j + 1, n, m, mat, dp);
    }


    public static void main(String[] args) {

        int mat[][] = {
            {0,0,0},{0,1,0},{0,0,0}
        };

        System.out.println(TotalUniquePathsWithObstacles.uniquePathsWithObstacles(mat));
    }

}

