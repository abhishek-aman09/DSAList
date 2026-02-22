package DynamicProgramming.TwoDimentional;

import java.util.Arrays;

public class RodCuttingProblem {

    // https://www.geeksforgeeks.org/problems/rod-cutting0840/1
    /*
    Given a rod of length n inches and an array price[], 
    where price[i] denotes the value of a piece of length i. 
    Your task is to determine the maximum value obtainable by 
    cutting up the rod and selling the pieces.
    
    Note: n = size of price, and price[] is 1-indexed array.
    
    Input: price[] = [3, 5, 8, 9, 10, 17, 17, 20]
    Output: 24
    Explanation: The maximum obtainable value is 24 by cutting the rod 
    into 8 pieces of length 1, i.e, 8*price[1] = 8*3 = 24.
    
    Approach : array ind + 1 will be treated as current length.
    if current length is greater than remaining length, return 0

    if len - i is >= 0, we can cut the rod and reuse the same length (Unbounded knapsack)
    
    */

    public int cutRod(int[] price) {
        int n = price.length;

        int dp[][] = new int[n + 1][n + 1];
        Arrays.stream(dp).forEach(row -> Arrays.fill(row, -1));

        return helper(1, n, price, dp);

    }
    
    private int helper(int i, int len, int price[], int dp[][]) {
        if (len <= 0) {
            return 0;
        }
        
        if (i > len) {
            return 0;
        }

        if (dp[i][len] != -1) {
            return dp[i][len];
        }

        int cut = 0;

        if (len - i  >= 0) {
            cut = helper(i, len - i, price, dp) + price[i - 1];
        }

        int notCut = helper(i + 1, len, price, dp);

        return dp[i][len] = Integer.max(cut, notCut);

    }
    
}
