package DynamicProgramming.OneDimentional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaximulSumOfNonAdjacentElements {

    /*
     * This problem is basically a variation of knapsack with constraints
     * https://www.naukri.com/code360/problems/maximum-sum-of-non-adjacent-elements_843261?leftPanelTabValue=PROBLEM
     * 
     * You are given an array/list of ‘N’ integers.
     * You are supposed to return the maximum sum of the subsequence with the constraint that no two elements are adjacent in the given array/list.
     * 
     * Input 
     * 9
     * 1 2 3 1 3 5 8 1 9
     * 
     * Output and Explaination
     * 24
     * out of all the possibilities, if we take the sum of 'ARR[0]', 'ARR[2]', 'ARR[4]', 'ARR[6]' and 'ARR[8]',
     *  i.e. 24 so, it will give the maximum sum of sequence in which no elements are adjacent in the given array/list.
     */

    public static int maximumNonAdjacentSum(ArrayList<Integer> nums) {
        int n = nums.size();

        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);

        return solve(n, nums, dp);
    }
    
    private static int solve(int n, ArrayList<Integer> nums, int[] dp) {
        if (n <= 0) {
            return 0;
        }

        if (dp[n] != -1) {
            return dp[n];
        }

        return dp[n] = Integer.max(
            solve(n - 2, nums, dp) + nums.get(n - 1),
            solve(n - 1, nums, dp)
        );
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(List.of(11, 55, 17, 23, 97, 11, 3, 64, 45, 25));

        System.out.println("Maximum sum is " + maximumNonAdjacentSum(list));
    }
}
