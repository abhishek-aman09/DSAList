package DynamicProgramming.ThreeDimentional;

import java.util.Arrays;

public class BuySellStocksForKTimes {
    // this can be a general solution for k transaction allowed use case
    // We can buy and sell stock for at most k times.
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/description/

    public int maxProfit(int[] prices) {

        int n = prices.length;
        int transLeftState = 3;
        int canBuyState = 2;

        int dp[][][] = new int[n][transLeftState][canBuyState];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < transLeftState; j++) {
                Arrays.fill(dp[i][j] , -1);
            }
        }
        return solve(0,prices,  n, 1, transLeftState - 1, dp);
    }

    int solve(int i, int prices[], int n, int canBuy, int transLeft, int dp[][][]) {
        if(n == i || transLeft == 0) {
            return 0;
        }

        if(dp[i][transLeft][canBuy] != -1) {
            return dp[i][transLeft][canBuy];
        }

        if(canBuy == 1) {
            return dp[i][transLeft][canBuy] = Integer.max(
                    solve(i + 1, prices, n, 0, transLeft, dp) - prices[i],
                    solve(i + 1, prices, n, 1, transLeft, dp)
            );
        } else {
            return dp[i][transLeft][canBuy] = Integer.max(
                    solve(i + 1, prices, n, 1, transLeft - 1, dp) + prices[i],
                    solve(i + 1, prices, n, 0, transLeft, dp)
            );
        }
    }
}
