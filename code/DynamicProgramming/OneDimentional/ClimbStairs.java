package DynamicProgramming.OneDimentional;

import java.util.Arrays;

/*
 * You are climbing a staircase. It takes n steps to reach the top.

Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

 

Example 1:

Input: n = 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
 */

public class ClimbStairs {

    int countWays(int n) {

        if (n == 1 || n == 0) {
            return 1;
        }

        int[] dp = new int[n + 1];
        Arrays.fill(dp, 0);

        dp[0] = 1;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        // solve(n, dp);

        return dp[n];
    }

    // memozize

    private int solve(int n, int[] dp) {

        if (n == 0) {
            return dp[n] = 1;
        }
        if (n < 0) {
            return 0;
        }

        if (dp[n] != 0) {
            return dp[n];
        }

        return dp[n] = solve(n - 2, dp) + solve(n - 1, dp);
    }

    public static void main(String[] args) {
        ClimbStairs obj = new ClimbStairs();

        System.out.println(obj.countWays(20));
    }
}
