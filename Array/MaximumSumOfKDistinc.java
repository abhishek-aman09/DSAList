package Array;

import java.util.HashMap;
import java.util.Map;

public class MaximumSumOfKDistinc {

    // https://leetcode.com/problems/maximum-sum-of-distinct-subarrays-with-length-k/description/

    /*You are given an integer array nums and an integer k. Find the maximum subarray sum of all the subarrays of nums that meet the following conditions:

        The length of the subarray is k, and
        All the elements of the subarray are distinct.
        Return the maximum subarray sum of all the subarrays that meet the conditions. If no subarray meets the conditions, return 0.
     * 
     */
    
    public long maximumSubarraySum(int[] nums, int k) {

        int n = nums.length;

        long ans = 0;

        if (k > n) {
            return 0;
        }

        Map<Integer, Integer> freq = new HashMap<>();

        int isRepeating = 0;

        long tempSum = 0l;
        int i = 0;

        while (i < k) {
            if (freq.containsKey(nums[i]) == false) {
                freq.put(nums[i], 0);
            }

            if (freq.get(nums[i]) > 0) {
                isRepeating++;
            }

            freq.put(nums[i], freq.get(nums[i]) + 1);

            tempSum += nums[i];
            i++;
        }

        if (isRepeating == 0) {
            ans = Long.max(ans, tempSum);
        }

        while (i < n) {

            if (freq.containsKey(nums[i]) == false) {
                freq.put(nums[i], 0);
            }

            // removing the left element 
            tempSum -= nums[i - k];

            // adding the right element
            tempSum += nums[i];

            // reducing the frequecy of left el by one
            freq.put(nums[i - k], freq.get(nums[i - k]) - 1);

            // checking if removal of left el is making the current subarray
            // distict
            if (freq.get(nums[i - k]) >= 1) {
                isRepeating--;
            }
            

            if (freq.get(nums[i]) > 0) {
                isRepeating++;
            }

            freq.put(nums[i], freq.get(nums[i]) + 1);

            if (isRepeating == 0) {
                ans = Long.max(ans, tempSum);
            }

            i++;

        }

        return ans;

    }
    
    public static void main(String[] args) {
        MaximumSumOfKDistinc obj = new MaximumSumOfKDistinc();

        int arr[] = {1,1,1,7,8,9};

        System.out.println(obj.maximumSubarraySum(arr, 3));
    }

}
