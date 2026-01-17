package DynamicProgramming.TwoDimentional;

import java.util.Arrays;

public class CoinChange2 {

    /*
     * Input: amount = 5, coins = [1,2,5]
    Output: 4
    Explanation: there are four ways to make up the amount:
    5=5
    5=2+2+1
    5=2+1+1+1
    5=1+1+1+1+1
     */
    
    public int change(int amount, int[] coins) {

        int n = coins.length;

        int dp[][] = new int[n + 1][amount + 1];

        for (int arr[] : dp) {
            Arrays.fill(arr, -1);
        }

        return solve(coins, amount, n, dp);

    }
    

    int solve(int coins[], int amount, int n, int dp[][]) {

        if (amount == 0) {
            return 1;
        }

        if (n <= 0) {
            return 0;
        }

        if (dp[n][amount] != -1) {
            return dp[n][amount];
        }

        // doesNot include
        int notInclude = solve(coins, amount, n - 1, dp);

        //Include
        int include = 0;
        if (coins[n - 1] <= amount) {
            include = solve(coins, amount - coins[n - 1], n, dp);
        }

        return dp[n][amount] = include + notInclude;

    }
    
}
