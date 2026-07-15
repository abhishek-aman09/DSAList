package DynamicProgramming.ThreeDimentional;

import java.util.Arrays;

public class ValidParanthesisStringPath {
    
    /*

    https://leetcode.com/problems/check-if-there-is-a-valid-parentheses-string-path/
    A parentheses string is a non-empty string consisting only of '(' and ')'. It is valid if any of the following conditions is true:
    
    It is ().
    It can be written as AB (A concatenated with B), where A and B are valid parentheses strings.
    It can be written as (A), where A is a valid parentheses string.
    You are given an m x n matrix of parentheses grid. A valid parentheses string path in the grid is a path satisfying all of the following conditions:
    
    The path starts from the upper left cell (0, 0).
    The path ends at the bottom-right cell (m - 1, n - 1).
    The path only ever moves down or right.
    The resulting parentheses string formed by the path is valid.
    Return true if there exists a valid parentheses string path in the grid. Otherwise, return false.
    
    
    Input: grid = [["(","(","("],[")","(",")"],["(","(",")"],["(","(",")"]]
    Output: true
    Explanation: The above diagram shows two possible paths that form valid parentheses strings.
    The first path shown results in the valid parentheses string "()(())".
    The second path shown results in the valid parentheses string "((()))".
    Note that there may be other valid parentheses string paths.
    
    
    Approach : DP based - for each (i, j), we check with how many open braces have we reached till the grid, we maintain a dp[i][j][openBrackets] 
    for each grid having with different open braces and it will store the result, whether there is a path from the grid to end with given number of 
    braces.
    
    */

    public boolean hasValidPath(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        if(grid[n - 1][m - 1] == '(') { // if we have an open bracket at end, path is not possible
            return false;
        }

        int dp[][][] = new int[n][m][n + m + 1]; // last size is n + m + 1 as we can have at most (n + m + 1) / 2 open brackets in a valid path

        Arrays.stream(dp).forEach(mat -> Arrays.stream(mat).forEach(row -> Arrays.fill(row, -1)));

        int ans = helper(grid, n, m, 0, 0, 0, dp);

        return ans == 1;
    }

    private int helper(char[][] grid, int n, int m, int i, int j, int openCount, int[][][] dp) {

        if(i >= n || j >= m) {
            return 0;
        }

        if(i == n - 1 && j == m - 1) { // if we have reached the end, we check we only have one open bracket remaining to close
           return openCount == 1 ? 1 : 0;
        }

        openCount += grid[i][j] == ')' ? -1 : 1; // update the open count for given grid

        if(openCount < 0 || openCount > (n + m)) { // if update count goes negative or goes above upper limit, return false
            return 0;
        }

        if(dp[i][j][openCount] != -1) {
            return dp[i][j][openCount];
        }

        int bottom = helper(grid, n, m, i + 1, j, openCount, dp); // recursively call bottom 
        int right =  helper(grid, n, m, i, j + 1, openCount, dp); // recursive call right

        return dp[i][j][openCount] = (right + bottom) >= 1 ? 1 : 0; // its basically right || bottom operation.
    }
}
