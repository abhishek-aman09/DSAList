package DynamicProgramming.TwoDimentional.LIS;

import java.util.Arrays;

public class LISLenBinarySearch {

    /*
    https://leetcode.com/problems/longest-increasing-subsequence/description/
    
    Given an integer array nums, return the length of the longest strictly increasing subsequence.
    
    Input: nums = [10,9,2,5,3,7,101,18]
    Output: 4
    
    Approach : this is more efficient way to calculate length of longest increasing subsequence using binary search.
    Intution : create a sorted sequence array of same lenght and fill with large value. Now for each element in the array, check the smallest number greater than curr element
    in the sorted array and put it there.
    example : nums = 1, 7, 8, 4, 5, 6, 9, sorted array = inf, inf, inf, inf, inf, inf, inf
    for el 1 : sorArr = 1, inf, inf, inf, inf, inf, inf
    for el 7 : sorArr = 1, 7, inf, inf, inf, inf, inf
    for el 8 : sorArr = 1, 7, 8, inf, inf, inf, inf
    for el 4 : we will get pos as 1 and overwrite 7 : sorArr = 1, 4, 8, inf, inf, inf, inf
    for el 5 : sorArr = 1, 4, 5, inf, inf, inf, inf
    for el 6 and 9 : sorArr = 1, 4, 5, 6, 9, inf, inf
    
    int this example we got the actual LIS in the array but that won't be the case always but the length of non inf elements will give the len of the LIS
    
    time complexity : NlongN, space is N
    
    */

    public int lengthOfLIS(int[] nums) {

        int n = nums.length;

        int sortedSequence[] = new int[n];

        Arrays.fill(sortedSequence, Integer.MAX_VALUE);

        for (int el : nums) {
            int getPos = getSmallestNumberGreaterThanEqualToCurr(el, sortedSequence); // get pos of smallest element greater than equal to curr el

            sortedSequence[getPos] = el; // replace the previous el with curr
        }

        int ans = 0;

        for (int el : sortedSequence) {
            if (el == Integer.MAX_VALUE) { // sorted seq will always be sorted, so len of non zero elements will give us the len of LIS
                break;
            }

            ans++;
        }

        return ans;

    }
    
    private int getSmallestNumberGreaterThanEqualToCurr(int num, int sortedSeq[]) {

        int l = 0;
        int r = sortedSeq.length - 1;

        int ans = -1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (sortedSeq[mid] >= num) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return ans;
    }
    
    // LIS Recursive : Alternate method similar to knapsack.
    // for each index i, if it is bigger than its caller method index value,
    // we have two options, either to take it, or not take it in the LIS
    // a dp[n][n] will be needed to compute all the case.
    // if n = 10^5; overflow may occur.

    int LISRecursive(int ind, int prev_ind, int n, int nums[], int dp[][]) {
        if (ind == n) {
            return 0;
        }

        if (dp[ind][prev_ind + 1] != -1) {
            return dp[ind][prev_ind + 1];
        }
        // not including the current element in LIS, calling next el directly
        int notTaken = LISRecursive(ind + 1, prev_ind, n, nums, dp);

        // Including the curr el, if it is bigger than prev_ind or
        // if the prev_ind = -1, i.e ind points to the first element.
        int taken = 0;
        if (prev_ind == -1 || nums[ind] > nums[prev_ind]) {
            taken =  1 + LISRecursive(ind + 1, ind, n, nums, dp);
        }

        dp[ind][prev_ind + 1] = Integer.max(notTaken, taken);
        return dp[ind][prev_ind + 1];
    }


    
}
