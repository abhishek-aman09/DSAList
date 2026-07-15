package Google.Graph;

import java.util.Arrays;

public class LongestIncresingPathInMatrix {

    /*
    https://leetcode.com/problems/longest-increasing-path-in-a-matrix/description/
    
    Given an m x n integers matrix, return the length of the longest increasing path in matrix.
    
    From each cell, you can either move in four directions: left, right, up, or down. 
    You may not move diagonally or move outside the boundary (i.e., wrap-around is not allowed).
    
    Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
    Output: 4
    Explanation: The longest increasing path is [1, 2, 6, 9].
    
    Approach : Standard dp. Note : we do not need visited array as dp itself will do a visited check
    */
    
    public int longestIncreasingPath(int[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;

        int dp[][] = new int[n][m];

        Arrays.stream(dp).forEach(row -> Arrays.fill(row, -1));

        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (dp[i][j] == -1) { // for each index we check if it is not visited
                    int curr = dfs(i, j, n, m, matrix, dp, Integer.MIN_VALUE); // we pass the parent to check if
                    maxLen = Integer.max(maxLen, curr); // current node is greater than its parent
                }
            }
        }

        return maxLen;
    }

    private int dfs(int i, int j, int n, int m, int[][] matrix, int[][] dp, int parent) {
        
        if (!isSafe(i, j, n, m) || matrix[i][j] <= parent) { // is index is out of bound or curr is less than par, return
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        // recursively call for all side
        int down = dfs(i + 1, j, n, m, matrix, dp, matrix[i][j]);
        int up = dfs(i - 1, j, n, m, matrix, dp, matrix[i][j]);
        int left = dfs(i, j - 1, n, m, matrix, dp, matrix[i][j]);
        int right = dfs(i, j + 1, n, m, matrix, dp, matrix[i][j]);

        return dp[i][j] = 1 + Math.max(up , Math.max(down, Math.max(left, right))); // retur 1 + max we got

    }
    
    private boolean isSafe(int i, int j, int n, int m) {
        return (i < n && i >= 0 && j < m && j >= 0);
    }
    
}
