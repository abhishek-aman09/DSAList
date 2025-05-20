package DynamicProgramming.TwoDimentional;

import java.util.Arrays;

public class TotalUniquePaths {

    /*
     * You are present at point ‘A’ which is the top-left cell of an M X N matrix,
     *  your destination is point ‘B’, which is the bottom-right cell of the same matrix.
     *  Your task is to find the total number of unique paths from point ‘A’ to point ‘B’.
     * In other words, you will be given the dimensions of the matrix as integers ‘M’ and ‘N’,
     *  your task is to find the total number of unique paths from the cell MATRIX[0][0] to MATRIX['M' - 1]['N' - 1].
    
        To traverse in the matrix,
        you can either move Right or Down at each step.
        For example in a given point MATRIX[i] [j], you can move to either MATRIX[i + 1][j] or MATRIX[i][j + 1].
        
        https://www.naukri.com/code360/problems/total-unique-paths_1081470?leftPanelTabValue=PROBLEM
     */
    
    public static int uniquePaths(int m, int n) {

        int dp[][] = new int[m + 1][n + 1];
        
        for (int i = 0; i <= m; i++) {
            Arrays.fill(dp[i], -1);
        }

        solve(0, 0, m, n, dp);

        return dp[0][0];

    }
    
    static int solve(int i, int j, int m, int n, int dp[][]) {
        if (i == m - 1 && j == n - 1) {
            return dp[i][j] = 1;
        }

        if (i >= m || j >= n) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        return dp[i][j] = solve(i + 1, j, m, n, dp) + solve(i, j + 1, m, n, dp);
    }
    
    public static void main(String[] args) {
        System.out.println(TotalUniquePaths.uniquePaths(3, 2));
    }

}
