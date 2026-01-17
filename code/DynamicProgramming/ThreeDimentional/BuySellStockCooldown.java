package DynamicProgramming.ThreeDimentional;

import java.util.Arrays;

public class BuySellStockCooldown {
    /*
     * You are given a list of stock prices of size 'n' called ‘prices’, where ‘prices[i]’ represents the price on ‘i’th day.
    Your task is to calculate the maximum profit you can earn by trading stocks if you can only hold one stock at a time.
    After you sell your stock on the ‘i’th day, you can only buy another stock on ‘i + 2’ th day or later.

    https://www.naukri.com/code360/problems/best-time-to-buy-and-sell-stock-with-cooldown_3125969?leftPanelTabValue=PROBLEM
     */
    public int maxProfit(int[] prices) {
        int n = prices.length;


        int dp[][][] = new int[n][2][2];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < 2; j++) {
                Arrays.fill(dp[i][j] , -1);
            }
        }

        return solve(0,prices,  n, 1, 0, dp);
    }

    int solve(int i, int prices[], int n, int canBuy, int cooldown, int dp[][][]) {
        if(n == i) {
            return 0;
        }

        if(dp[i][cooldown][canBuy] != -1) {
            return dp[i][cooldown][canBuy];
        }

        if(cooldown == 0) {
            if(canBuy == 1) {
                return dp[i][cooldown][canBuy] = Integer.max(
                        solve(i + 1, prices, n, 0, 0, dp) - prices[i],
                        solve(i + 1, prices, n, 1, 0, dp)
                );
            } else {
                return dp[i][cooldown][canBuy] = Integer.max(
                        solve(i + 1, prices, n, 1, 1, dp) + prices[i],
                        solve(i + 1, prices, n, 0, 0, dp)
                );
            }
        } else {
            return solve(i + 1, prices, n, 1, 0, dp);
        }
    }
}
