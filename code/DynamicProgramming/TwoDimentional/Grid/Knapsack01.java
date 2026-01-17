package DynamicProgramming.TwoDimentional.Grid;

public class Knapsack01 {

    // memoization
    private static int solve(int val[], int wt[], int capacity, int n, int[][] dp) {
        if (capacity <= 0 || n <= 0) {
            return 0;
        }

        if (dp[n][capacity] != -1) {
            return dp[n][capacity];
        }

        // do not take
        dp[n][capacity] = solve(val, wt, capacity, n - 1, dp);

        // take
        if (wt[n - 1] <= capacity) {
            dp[n][capacity] = Math.max(val[n - 1] + solve(val, wt, capacity - wt[n - 1], n - 1, dp), dp[n][capacity]);
        }

        return dp[n][capacity];

    }
    
    // tabulation
    static int knapSack(int capacity, int val[], int wt[]) {
        int n = val.length;

        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= capacity; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = -1;
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= capacity; j++) {
                dp[i][j] = dp[i - 1][j];

                if (wt[i - 1] <= j) {
                    dp[i][j] = Math.max(dp[i][j], val[i - 1] + dp[i - 1][j - wt[i - 1]]);
                }
            }
        }



        return dp[n][capacity];
    }

    public static void main(String[] args) {
        int capacity = 4;
        int[] val = {1, 2, 3};
        int[] wt = {4, 5, 1};

        System.out.println(knapSack(capacity, val, wt));
    }
}
