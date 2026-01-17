package DynamicProgramming.ThreeDimentional;

import java.util.Arrays;

public class ChocolatePickup {

    /*
     * Ninja has a 'GRID' of size 'R' X 'C'.
     * Each cell of the grid contains some chocolates.
     * Ninja has two friends Alice and Bob, and he wants to collect as
     * many chocolates as possible with the help of his friends.
    
    Initially, Alice is in the top-left position i.e. (0, 0),
    and Bob is in the top-right place i.e. (0, ‘C’ - 1) in the grid.
    Each of them can move from their current cell to the cells just below them.
    When anyone passes from any cell, he will pick all chocolates in it,
    and then the number of chocolates in that cell will become zero.
    If both stay in the same cell, only one of them will pick the chocolates in it.
    
    If Alice or Bob is at (i, j)
    then they can move to (i + 1, j), (i + 1, j - 1) or (i + 1, j + 1). 
    They will always stay inside the ‘GRID’.
    
    Your task is to find the maximum number of chocolates Ninja can collect with the help of his friends by following the above rules.

    https://www.naukri.com/code360/problems/ninja-and-his-friends_3125885?leftPanelTabValue=PROBLEM
    */

    public static int maximumChocolates(int r, int c, int[][] grid) {

        int dp[][][] = new int[r][c][c];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        solve(0, 0, c - 1, r, c, grid, dp);

        return dp[0][0][c - 1];
    }
    
    static int solve(int i, int j, int k, int n, int m, int grid[][], int dp[][][]) {
        if (i == n - 1) {
            // if j and k are in bound
            if (j >= 0 && j < m && k >= 0 && k < m) {
                if (j != k) {
                    return grid[i][j] + grid[i][k];
                } else {
                    return grid[i][j];
                }
            } else if (k >= 0 && k < m) { // if j is out of bound
                return grid[i][k];
            } else if (j >= 0 && j < m) { // if k is out of bound
                return grid[i][j];
            }
        }
        
        if (i < 0 || j >= n || j < 0 || j >= m || k < 0 || k >= m) {
            return 0;
        }

        if (dp[i][j][k] != -1) {
            return dp[i][j][k];
        }

        // computing all nine possibilities of movement of both Alice and Bob
        // altenatively we can do it in a nested loop. -1 <= j <= 1 and -1 <= k <= 1
        int a = solve(i + 1, j + 1, k, n, m, grid, dp);
        int b = solve(i + 1, j + 1, k + 1, n, m, grid, dp);
        int c = solve(i + 1, j + 1, k - 1, n, m, grid, dp);
        int d = solve(i + 1, j, k, n, m, grid, dp);
        int e = solve(i + 1, j, k + 1, n, m, grid, dp);
        int f = solve(i + 1, j, k - 1, n, m, grid, dp);
        int g = solve(i + 1, j - 1, k, n, m, grid, dp);
        int h = solve(i + 1, j - 1, k + 1, n, m, grid, dp);
        int q = solve(i + 1, j - 1, k - 1, n, m, grid, dp);
        
        int maxChocolateFromAllPossibilities =  Integer.max(a, Integer.max(c, Integer.max(d, 
                Integer.max(e, Integer.max(f, Integer.max(g, Integer.max(h, 
                        Integer.max(q, b))))))));

        if (j == k) {
            return dp[i][j][k] = maxChocolateFromAllPossibilities + grid[i][j];
        } else {
            return dp[i][j][k] = maxChocolateFromAllPossibilities + grid[i][j] + grid[i][k];
        }   

    }
}
