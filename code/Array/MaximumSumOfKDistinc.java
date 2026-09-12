package Array;

import java.util.HashMap;
import java.util.Map;

public class MaximumSumOfKDistinc {

    // https://leetcode.com/problems/maximum-sum-of-distinct-subarrays-with-length-k/description/

    /*You are given an integer array nums and an integer k. Find the maximum subarray sum of all the subarrays of nums that meet the following conditions:
    
    The length of the subarray is k, and
    All the elements of the subarray are distinct.
    Return the maximum subarray sum of all the subarrays that meet the conditions. If no subarray meets the conditions, return 0.
    
    A subarray is a contiguous non-empty sequence of elements within an array.
    
    Input: nums = [1,5,4,2,9,9,9], k = 3
    Output: 15
    Explanation: The subarrays of nums with length 3 are:
    - [1,5,4] which meets the requirements and has a sum of 10.
    - [5,4,2] which meets the requirements and has a sum of 11.
    - [4,2,9] which meets the requirements and has a sum of 15.
    - [2,9,9] which does not meet the requirements because the element 9 is repeated.
    - [9,9,9] which does not meet the requirements because the element 9 is repeated.
    We return 15 because it is the maximum subarray sum of all the subarrays that meet the conditions
    
    Approach : create a hashmap to store frequency of elements of window of size k. First push k elements into the map, if the size of map is k, we have all unique
    continue this approach from k + 1 to n, maintain the left pointer, decrease the freq of el at left pointer, if freq reaches 0, remove it from map.

    if the size of map is k after operation in each iteration, calculate the max sum
     * 
     */
    
    public long maximumSubarraySum(int[] nums, int k) {

        int n = nums.length;

        long ans = 0;

        if (k > n) {
            return 0;
        }

        Map<Integer, Integer> map = new HashMap<>();

        long sum = 0;
        // push the freq of the elements into the hashmap
        for(int i = 0; i < k; i++) {
            sum += nums[i];
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        if(map.size() == k) { // if size is k, we have all unique elements
            ans = sum;
        }

        int left = 0; // we have left pointer

        for(int i = k; i < n; i++, left++) { // for each element from k to n - 1
            map.put(nums[left], map.get(nums[left]) - 1); // reduce frequency
            sum -= nums[left]; // decrease the sum

            if(map.get(nums[left]) == 0) { // if freq reached zero, remove it from map
                map.remove(nums[left]);
            }

            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1); // add current el into map and update the sum
            sum += nums[i];

            if(map.size() == k) { // if size of map is k, we have k elements
                ans = Math.max(ans, sum);
            }
        }

        return ans;

    }
    
    public static void main(String[] args) {
        MaximumSumOfKDistinc obj = new MaximumSumOfKDistinc();

        int arr[] = {1,1,1,7,8,9};

        System.out.println(obj.maximumSubarraySum(arr, 3));
    }

}
