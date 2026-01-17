package Array.SlidingWindow;

public class MinimumSizeSubarraySum {

    // https://leetcode.com/problems/minimum-size-subarray-sum/description/
    // Given an array of positive integers nums and a positive integer target,
    //  return the minimal length of a subarray whose sum is greater than or equal to target. If there is no such subarray, return 0 instead.


    public int minSubArrayLen(int target, int[] nums) {

        int n = nums.length;

        int l = 0, r = 0;

        int sum = nums[0];

        int minSize = n + 1;

        while (r < n) {
            if (nums[r] == target) {
                return 1;
            }

            if(sum >= target) {
                if (minSize > r - l + 1) {
                    minSize = r - l + 1;
                }
                sum -= nums[l];
                l++;
            } else {
                r++;
                if (r >= n) {
                    break;
                }
                sum += nums[r];
            }
        }

        return minSize == n + 1 ? 0 : minSize;

    }
    
    public static void main(String[] args) {
        int arr[] = { 1,2,3,4,5 };

        MinimumSizeSubarraySum obj = new MinimumSizeSubarraySum();

        System.out.println(obj.minSubArrayLen(11, arr));
    }
    
}
