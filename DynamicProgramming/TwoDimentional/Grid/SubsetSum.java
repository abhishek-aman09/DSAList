package DynamicProgramming.TwoDimentional.Grid;

import java.util.Arrays;

public class SubsetSum {
    static Boolean isSubsetSum(int arr[], int sum) {
        int n = arr.length;

        Boolean dp[][] = new Boolean[n][sum];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], null);
        }

        return solve(n - 1, sum, arr, dp);
    }
    
    static Boolean solve(int n, int sum, int arr[], Boolean dp[][]) {
        if (sum == 0) {
            return true;
        }

        if (n < 0 || sum < 0) {
            return false;
        }

        if (dp[n][sum - 1] != null) {
            return dp[n][sum - 1];
        }

        Boolean notIncluded = solve(n - 1, sum, arr, dp);
        Boolean included = false;

        if (arr[n] <= sum) {
            included = solve(n - 1, sum - arr[n], arr, dp);
        }

        return dp[n][sum - 1] = notIncluded || included;
    }
}
