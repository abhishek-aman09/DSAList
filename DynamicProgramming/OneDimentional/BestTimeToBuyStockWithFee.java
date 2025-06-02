package DynamicProgramming.OneDimentional;

import java.util.Arrays;

public class BestTimeToBuyStockWithFee {

    public int maxProfit(int[] prices, int fee) {

        int n = prices.length;

        int dp[][] = new int[n][2];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return solve(0, n, prices, 1, fee, dp);

    }
    
    int solve(int i, int n, int prices[], int canBuy, int fee, int dp[][]) {
        if (i == n) {
            return 0;
        }

        if (dp[i][canBuy] != -1) {
            return dp[i][canBuy];
        }

        if (canBuy == 1) {
            return dp[i][canBuy] = Integer.max(
                    solve(i + 1, n, prices, 0, fee, dp) - prices[i],
                    solve(i + 1, n, prices, 1, fee, dp));
        } else {
            return dp[i][canBuy] = Integer.max(
                    solve(i + 1, n, prices, 1, fee, dp) + prices[i] - fee,
                    solve(i + 1, n, prices, 0, fee, dp));
        }
    }
    
    public static void main(String[] args) {
        int arr[] = { 1, 3, 2, 8, 4, 9 };
        BestTimeToBuyStockWithFee obj = new BestTimeToBuyStockWithFee();
        System.out.println(obj.maxProfit(arr, 2));

    }
    
}
