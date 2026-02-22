package DynamicProgramming.TwoDimentional;

import java.util.Arrays;

public class CoinChange {

    // https://leetcode.com/problems/coin-change/description/

    /*
    You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.
    
    Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.
    
    You may assume that you have an infinite number of each kind of coin.
    
    Input: coins = [1,2,5], amount = 11
    Output: 3
    Explanation: 11 = 5 + 5 + 1
    

    Approach : Unbounded knapsack
    */
    
    private static int MAX = 10000000;

    public int coinChange(int[] coins, int amount) {

        int n = coins.length;
        int dp[][] = new int[n][amount + 1];
        for (int row[] : dp) {
            Arrays.fill(row, -1);
        }
        
        int ans = helper(coins, 0, n, amount, dp);

        

        return  ans == MAX ? -1 : ans;
    }

    private int helper(int coins[], int i, int n, int amount, int dp[][]) {

        if (amount == 0) {
            return 0;
        }

        if (i >= n) {
            return MAX;
        }

        if (dp[i][amount] != -1) {
            return dp[i][amount];
        }

        if (coins[i] <= amount) {
            return dp[i][amount] = Math.min(
                // same coin can be used again, so no increment in index
                helper(coins, i, n, amount - coins[i], dp) + 1, 
                helper(coins, i + 1, n, amount, dp)    
            );
        } else {
            return dp[i][amount] = helper(coins, i + 1, n, amount, dp);
        }
    }
    
}
