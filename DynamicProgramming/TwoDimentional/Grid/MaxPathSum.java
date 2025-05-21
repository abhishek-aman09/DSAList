package DynamicProgramming.TwoDimentional.Grid;

import java.util.Arrays;

public class MaxPathSum {

    public static int getMaxPathSum(int[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;

        if (n == 1 && m == 1) {
            return matrix[0][0];
        }

        int answer = Integer.MIN_VALUE;

        int dp[][] = new int[n][m];

        for (int i = 0; i < m; i++) {
            resetDpArray(dp, n);
            answer = Integer.max(answer, solve(0, i, n, m, matrix, dp));
        }

        return answer;
    }
    
    static int solve(int i, int j, int n, int m, int matrix[][], int dp[][]) {

        if (i == n - 1 && j >= 0 && j < m) {
            return dp[i][j] = matrix[i][j];
        }

        if (i >= n || j < 0 || j >= m) {
            return Integer.MIN_VALUE;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        return dp[i][j] = Integer.max(
                solve(i + 1, j, n, m, matrix, dp),
                Integer.max(
                        solve(i + 1, j + 1, n, m, matrix, dp),
                        solve(i + 1, j - 1, n, m, matrix, dp)))
                + matrix[i][j];
    }
    
    private static void resetDpArray(int dp[][], int n) {

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }
    }

}
