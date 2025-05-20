package DynamicProgramming.TwoDimentional;

import java.util.Arrays;

public class CountNumberOfWays {

    /*
     * You are given an infinite supply of coins of each of denominations
     * D = {D0, D1, D2, D3, ...... Dn-1}. You need to figure out the total number of ways W,
     * in which you can make a change for value V using coins of denominations from D. 
     * Print 0, if a change isn't possible.
     * 
     * 
     * Sample Input 1 :
        3
        1 2 3
        4
        Sample Output 1:
        4
        Explanation for Sample Output 1:
        We can make a change for the value V = 4 in four ways.
        1. (1,1,1,1), 
        2. (1,1, 2), [One thing to note here is, (1, 1, 2) is same as that of (2, 1, 1) and (1, 2, 1)]
        3. (1, 3), and 
        4. (2, 2)
    
        https://www.naukri.com/code360/problems/ways-to-make-coin-change_630471?source=youtube&campaign=striver_dp_videos&utm_source=youtube&utm_medium=affiliate&utm_campaign=striver_dp_videos&leftPanelTabValue=PROBLEM
        
     */
    
    public static long countWaysToMakeChange(int denominations[], int value) {
        
        int n = denominations.length;

        int dp[][] = new int[n + 1][value + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return solve(denominations, value, n, dp);

	}

    static int solve(int nums[], int value, int n, int dp[][]) {
        if (value == 0) {
            return dp[n][value] = 1;
        }

        if (n <= 0) {
            return 0;
        }

        if (dp[n][value] != -1) {
            return dp[n][value];
        }

        int notIncluded = solve(nums, value, n - 1, dp);

        int included = 0;

        if (nums[n - 1] <= value) {
            included = solve(nums, value - nums[n - 1], n, dp);
        }

        return dp[n][value] = included + notIncluded;

    }
    
    public static void main(String[] args) {
        int arr[] = { 5, 3, 2 };

        System.out.println(CountNumberOfWays.countWaysToMakeChange(arr, 30));
    }
}
