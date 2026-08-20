package Google.DP;

import java.util.Arrays;

public class DungeonGame {

    /*
    https://leetcode.com/problems/dungeon-game/description/
    
    The demons had captured the princess and imprisoned her in the bottom-right corner of a dungeon. 
    The dungeon consists of m x n rooms laid out in a 2D grid. Our valiant knight was initially positioned 
    in the top-left room and must fight his way through dungeon to rescue the princess.
    
    The knight has an initial health point represented by a positive integer. 
    If at any point his health point drops to 0 or below, he dies immediately.
    
    Some of the rooms are guarded by demons (represented by negative integers), 
    so the knight loses health upon entering these rooms; other rooms are either empty (represented as 0) 
    or contain magic orbs that increase the knight's health (represented by positive integers).
    
    To reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.
    
    Return the knight's minimum initial health so that he can rescue the princess.
    
    Note that any room can contain threats or power-ups, even the first room the knight enters 
    and the bottom-right room where the princess is imprisoned.
    
    Input: dungeon = [[-2,-3,3],[-5,-10,1],[10,30,-5]]
    Output: 7
    Explanation: The initial health of the knight must be at least 7 if he follows the optimal path: 
    RIGHT-> RIGHT -> DOWN -> DOWN.
    
    Approach : Standard dp with few conditions, you can go to a cell only if you have atleast one unit of energy
    left. If the cell has positive value, you only need one (minimum) unit to enter, else you would need 
    1 - (negative cell value) to enter into the cell.
    
    */
    private static final int MAX_VAL = Integer.MAX_VALUE / 10000;
    public int calculateMinimumHP(int[][] dungeon) { // standard dp block

        int n = dungeon.length;
        int m = dungeon[0].length;

        int dp[][] = new int[n][m];

        for (int row[] : dp) {
            Arrays.fill(row, -1);
        }

        return helper(0, 0, n, m, dungeon, dp);

    }

    private int helper(int i, int j, int n, int m, int[][] dungeon, int[][] dp) {

        if ( i >= n || j >= m ) { // if we are out of bounds, return some max value which cannot be breached
            return MAX_VAL; 
        }
        
        if (i == n - 1 && j == m - 1) { // when we have reached the last cell, if it is a positive cell, return one
            return Math.max(1, 1 - dungeon[i][j]); // else, 1 - cellVal will be returned.
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int right = helper(i, j + 1, n, m, dungeon, dp); // check of right cell
        int down = helper(i + 1, j, n, m, dungeon, dp); // check of down cell

        return dp[i][j] = Integer.max(1, Integer.min(right, down) - dungeon[i][j]); // return min of 1 (for positive cell)
        // and min(right, down) - currentCellValue (for positive cells we will check what min value we need for future
        // and the value of curr cell, if left is more than right, we get a positive value, if currVal is negative, we
        // have a positive val)
    }
    
}
