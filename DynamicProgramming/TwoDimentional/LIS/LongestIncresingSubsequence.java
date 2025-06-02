package DynamicProgramming.TwoDimentional.LIS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import Pair.Pair;

public class LongestIncresingSubsequence {

    /*
     * Input: nums = [10,9,2,5,3,7,101,18]
        Output: 4
        Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
        
        https://leetcode.com/problems/longest-increasing-subsequence/description/

     */
    
    public int lengthOfLIS(int[] nums) {

        int n = nums.length;

        int result = 0;

        int dp[] = new int[n];

        Arrays.fill(dp, -1);

        // Iteretively call solve block to get LIS for each index.
        for (int i = 0; i < n; i++) {
            result = Integer.max(result, solve(i, n, nums, dp));
        }

        // Tabulation for the above memozisation

        int tabDp[] = new int[n];
        int ans = 0;
        Arrays.fill(tabDp, 1);
        int lisIndex[] = new int[n];

        for (int i = 0; i < n; i++) {
            lisIndex[i] = i;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (tabDp[j] + 1 > tabDp[i]) {
                        tabDp[i] = tabDp[j] + 1;
                        lisIndex[i] = j;
                    }
                }
                ans = Integer.max(ans, tabDp[i]);
            }
        }

        int maxLisIndex = 0;

        for (int i = 0; i < n; i++) {
            if (ans == tabDp[i]) {
                maxLisIndex = i;
                break;
            }
        }



        // Printing Longest Increasing Subsequence

        ArrayList<Integer> lis = new ArrayList<>();
        
        int temp[] = new int[ans];
        int j = ans - 1;
        
        temp[j] = nums[maxLisIndex];
        j--;

        while (lisIndex[maxLisIndex] != maxLisIndex && j >= 0) {
            temp[j] = nums[lisIndex[maxLisIndex]];
            maxLisIndex = lisIndex[maxLisIndex];
            j--;
        }
        
        for(int i = 0; i < ans; i++) {
            lis.add(temp[i]);
        }
        



        return ans;

    }

    // Memozization
    int solve(int i, int n, int nums[], int dp[]) {

        if (i == n) {
            return 0;
        }

        if (dp[i] != -1) {
            return dp[i];
        }

        int maxLis = 1;

        // for each el from 0 to i, we need to get its LIS
        // then keep a track of max using maxLis.
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                maxLis = Integer.max(maxLis, solve(j, n, nums, dp) + 1);
            }
        }

        return dp[i] = maxLis;
    }

    // Alternate method similar to knapsack.
    // for each index i, if it is bigger than its caller method index value,
    // we have two options, either to take it, or not take it in the LIS
    // a dp[n][n] will be needed to compute all the case.
    // if n = 10^5; overflow may occur.

    int LISRecursive(int ind, int prev_ind, int n, int nums[]) {
        if (ind == n) {
            return 0;
        }
        // not including the current element in LIS, calling next el directly
        int maxLis = LISRecursive(ind + 1, prev_ind, n, nums);

        // Including the curr el, if it is bigger than prev_ind or
        // if the prev_ind = -1, i.e ind points to the first element.
        if (prev_ind == -1 || nums[ind] > nums[prev_ind]) {
            maxLis = Integer.max(maxLis, LISRecursive(ind + 1, ind, n, nums));
        }

        return maxLis;
    }

    int LISInterative(int nums[]) {

        int n = nums.length;

        int dp[][] = new int[n + 1][n + 1];

        for (int ind = n - 1; ind >= 0; ind--) {
            for (int prev_ind = ind - 1; prev_ind >= -1; prev_ind--) {
                int len = dp[ind + 1][prev_ind + 1];

                if (prev_ind == -1 || nums[ind] > nums[prev_ind]) {
                    len = Integer.max(len, dp[ind + 1][ind + 1] + 1);
                }
                dp[ind][prev_ind + 1] = len;
            }
        }

        return dp[0][0];

    }


    public static void main(String[] args) {
        LongestIncresingSubsequence obj = new LongestIncresingSubsequence();

        int arr[] = { 13, 10, 4, 4 };

        System.out.println(obj.lengthOfLIS(arr));
    }
}
 