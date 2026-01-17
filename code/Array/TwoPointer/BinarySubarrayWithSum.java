package Array.TwoPointer;

public class BinarySubarrayWithSum {
    
    /*
    https://leetcode.com/problems/binary-subarrays-with-sum/description/
        Given a binary array nums and an integer goal,
        return the number of non-empty subarrays with a sum goal.
    
        Input: nums = [1,0,1,0,1], goal = 2
        Output: 4
        
        Input: nums = [0,0,0,0,0], goal = 0
        Output: 15
    
        Approach - find number of subarrays for sum <= k and
        sum <= k - 1 and subtract both.
    */

    public int numSubarraysWithSum(int[] nums, int goal) {

        return helper(nums, goal) - helper(nums, goal - 1);
        
    }

    private int helper(int nums[], int goal) {
        if (goal < 0) {
            return 0;
        }

        int n = nums.length;

        int sum = 0;
        int  count = 0;

        int l = 0;
        int  r = 0;

        while (r < n) {
            sum += nums[r];

            while (sum > goal) {
                sum -= nums[l];
                l++;
            }

            count += (r - l + 1);
            r++;
        }

        return count;
    }
    
}
