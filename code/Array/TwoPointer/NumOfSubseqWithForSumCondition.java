package Array.TwoPointer;

import java.util.Arrays;

public class NumOfSubseqWithForSumCondition {

    /*
    https://leetcode.com/problems/number-of-subsequences-that-satisfy-the-given-sum-condition/description/
    
    You are given an array of integers nums and an integer target.
    Return the number of non-empty subsequences of nums such that the sum of the minimum and maximum element on it is less or equal to target. Since the answer may be too large, return it modulo 109 + 7.
    
    Example 1:
    
    Input: nums = [3,5,6,7], target = 9
    Output: 4
    Explanation: There are 4 subsequences that satisfy the condition.
    [3] -> Min value + max value <= target (3 + 3 <= 9)
    [3,5] -> (3 + 5 <= 9)
    [3,5,6] -> (3 + 6 <= 9)
    [3,6] -> (3 + 6 <= 9)
    
    Approach : sort the array and do two pointers approach.
    Number of subsequence with array of size N where we must take first element
    in each subsequence is 2 ^ (N - 1)
    
    
    */

    private static final int MOD = (int) 1e9 + 7;

    public int numSubseq(int[] nums, int target) {

        int n = nums.length;

        Arrays.sort(nums);

        long ans = 0;

        int left = 0, right = n - 1;

        while (left <= right) {
            long sum = (long) nums[left] + nums[right];
            //if sum of min and max is less than equal to target,
            // we count number of subsequence we can form with array of this size
            if (sum <= target) {
                long numOfSubseq = getNumOfSubsequence(right - left);
                ans = (ans + numOfSubseq) % MOD;
                left++;
            } else {
                right--;
            }
        }

        return (int) (ans % MOD);

    }
    
    private long getNumOfSubsequence(int power) {
        return powerOfTwo(2l, power);
    }

    private long powerOfTwo(long base, int power) {

        long ans = 1l;

        while (power > 0) {
            if ((power & 1) == 1) {
                ans = (ans * base) % MOD;
            }
            power = power >> 1;
            base = (base * base) % MOD;
        }

        return ans;
    }
    
}
