package DynamicProgramming.PartitionDP;

import java.util.Arrays;

public class PartitionArrayForMaximumSum {

    // https://leetcode.com/problems/partition-array-for-maximum-sum/description/

    /*
    Given an integer array arr, partition the array into 
    (contiguous) subarrays of length at most k. After partitioning, 
    each subarray has their values changed to become the maximum value 
    of that subarray.
    
    Return the largest sum of the given array after partitioning. 
    Test cases are generated so that the answer fits in a 32-bit integer.
    
    Input: arr = [1,15,7,9,2,5,10], k = 3
    Output: 84
    Explanation: arr becomes [15,15,15,9,10,10,10]
    
    Approach : 
    For each i, we can atmost go to i + k index as a
    atmost of k length. number of subarrays can be any number.
    for each i, we will loop from i to i + k and calculate the max we can obtain
    in the window. and recursively call the right side.
    
    */
    
    public int maxSumAfterPartitioning(int[] arr, int k) {
    
        int n = arr.length;

        // dp[i] stores the maximum sum obtainable by partitioning 
        // the suffix subarray arr[i ... n - 1].
        int dp[] = new int[n];
        Arrays.fill(dp, -1);
        
        // Start solving the subproblem starting from the 0th index
        return helper(arr, k, 0, n, dp);
    }

    private int helper(int arr[], int k, int i, int n, int dp[]) {
        // BASE CASE:
        // If the starting index reaches or exceeds array bounds, 
        // no elements remain to partition -> contribution is 0.
        if (i >= n) {
            return 0;
        }

        // MEMOIZATION CHECK:
        // Return precomputed maximum sum for the suffix starting at index i.
        if (dp[i] != -1) {
            return dp[i];
        }

        int maxSum = 0;
        int maxInWin = 0;

        // A partition starting at index i can have a length of at most k.
        // The candidate partition ending index 'j' cannot exceed n - 1.
        int lim = (i + k < n ? i + k : n);

        // EXPLORE ALL VALID PARTITION SIZES FROM 1 TO k:
        // Form a partition arr[i ... j].
        for (int j = i; j < lim; j++) {

            // RUNNING MAXIMUM:
            // By problem definition, every value in the partition is changed to 
            // the maximum value present in that partition.
            // As we expand j, we maintain the maximum element in arr[i ... j] in O(1).
            if (arr[j] > maxInWin) {
                maxInWin = arr[j];
            }

            // 1. PARTITION CONTRIBUTION:
            //    Length of this partition is (j - i + 1).
            //    Total sum of this partition = (length) * (maximum element).
            //
            // 2. RECURSIVE SUBPROBLEM:
            //    helper(arr, k, j + 1, n, dp) solves the optimal partition sum
            //    for the remaining suffix arr[j + 1 ... n - 1].
            int part = ((j - i + 1) * maxInWin) + helper(arr, k, j + 1, n, dp);
            
            // Track the maximum total sum among all partition lengths [1 ... k]
            if (maxSum < part) {
                maxSum = part;
            }
        }

        return dp[i] = maxSum;
    }


    public static void main(String[] args) {
        int nums[] = { 1,4,1,5,7,3,6,1,9,9,3};

        PartitionArrayForMaximumSum obj = new PartitionArrayForMaximumSum();

        System.out.println(obj.maxSumAfterPartitioning(nums, 4));
    }

}
