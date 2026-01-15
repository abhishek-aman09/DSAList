package DynamicProgramming.TwoDimentional.Grid;

import java.util.Arrays;

public class UniquePaths {
    
    // https://leetcode.com/problems/unique-paths/description/

    /*
    There is a robot on an m x n grid. 
    The robot is initially located at the top-left corner (i.e., grid[0][0]).
     The robot tries to move to the bottom-right corner
    (i.e., grid[m - 1][n - 1]). The robot can only move either down or right
    at any point in time.
    
    Given the two integers m and n, 
    return the number of possible unique paths that the robot can take 
    to reach the bottom-right corner.
    
    Input: m = 3, n = 7
    Output: 28
    
    Approach : Standard dp problem. mark curr i,j visited and count the
    number of ways by calling right and down recursively.
    
    */

    public int uniquePaths(int n, int m) {

        boolean isVisited[][] = new boolean[n][m];
        int dp[][] = new int[n][m];

        for (int row[] : dp) {
            Arrays.fill(row, -1);
        }

        return helper(0, 0, n, m, isVisited, dp);

    }
    
    private int helper(int i, int j, int n, int m, boolean isVisited[][], int dp[][]) {
        if (i >= n || j >= m || i < 0 || j < 0) {
            return 0;
        }

        if (i == n - 1 && j == m - 1) {
            return 1;
        }

        if (isVisited[i][j]) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        isVisited[i][j] = true;

        int count = helper(i + 1, j, n, m, isVisited, dp) + helper(i, j + 1, n, m, isVisited, dp);

        isVisited[i][j] = false;

        return dp[i][j] = count;
    }

    public static void main(String[] args) {
        UniquePaths obj = new UniquePaths();

        System.out.println(obj.uniquePaths(3, 7));
    }
}
