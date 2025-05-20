package DynamicProgramming.TwoDimentional;

import java.util.Arrays;

/* 
 * Given an array arr[], partition it into two subsets(possibly empty)
 * such that each element must belong to only one subset. 
 * Let the sum of the elements of these two subsets be sum1 and sum2. 
 * Given a difference d, count the number of partitions in which 
 * sum1 is greater than or equal to sum2 and the difference between sum1 and sum2 is equal to d.
 * 
 * Approach
 * s1 + s2 = sum
 * s1 - s2 = diff
 * ----------------
 * 2s1     = sum + diff
 * s1      = (sum + diff) / 2;
 * 
 * find number of subsets with sum = s1
 * https://www.geeksforgeeks.org/problems/partitions-with-given-difference/1
*/

public class CountSubsetWithDiff {
    int countPartitions(int[] arr, int diff) {

        int n = arr.length;
        int sum = Arrays.stream(arr).sum();

        if ((diff + sum) % 2 != 0) {
            return 0;
        }

        sum = (sum + diff) / 2;

        int dp[][] = new int[n + 1][sum + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], 0);
        }

        return solve(arr, n, sum, dp);

    }
    
    private int solve(int arr[], int n, int sum, int dp[][]) {

        for (int i = 0; i <= n; i++){
            dp[i][0] = 1;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (arr[i - 1] <= j) {
                    dp[i][j] += dp[i - 1][j - arr[i - 1]];
                }
            }
        }

        return dp[n][sum];

    }
    

    public static void main(String[] args) {
        int arr[] = { 5, 2, 6, 4 };
        
        CountSubsetWithDiff obj = new CountSubsetWithDiff();
        obj.countPartitions(arr, 3);
    }
    

}
