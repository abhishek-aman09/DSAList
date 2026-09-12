package Array.SlidingWindow;

public class MinimumSizeSubarraySum {

    // https://leetcode.com/problems/minimum-size-subarray-sum/description/
    /*
    Given an array of positive integers nums and a positive integer target, 
    return the minimal length of a subarray whose sum is greater than or equal to target. If there is no such subarray, return 0 instead.
    
    
    Input: target = 7, nums = [2,3,1,2,4,3]
    Output: 2
    Explanation: The subarray [4,3] has the minimal length under the problem constraint.
    
    approach : two pointer, keep adding nums on right index into the sum, run inner loop till sum >= target, shrink the window from left
    and update minimum in each step. Increment the right
    
    
    */


    public int minSubArrayLen(int target, int[] nums) {

        int n = nums.length;

        int l = 0, r = 0;

        int sum = 0;

        int minSize = n + 1;

        while (r < n) {
            if (nums[r] >= target) { // if a single element is greater than or equal to target, return one
                return 1;
            }

            sum += nums[r];

            while (sum >= target) { // shring window from left and update the minimum
                int currSize = r - l + 1;
                minSize = Math.min(currSize, minSize);
                sum -= nums[l];
                l++;
            }
            
            r++;
        }

        return minSize == n + 1 ? 0 : minSize;

    }
    
    public static void main(String[] args) {
        int arr[] = { 1,2,3,4,5 };

        MinimumSizeSubarraySum obj = new MinimumSizeSubarraySum();

        System.out.println(obj.minSubArrayLen(11, arr));
    }
    
}
