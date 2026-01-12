package Array.PrefixSum;

import java.util.HashMap;
import java.util.Map;

public class SubarraySumDivisibleByK {
    /* https://leetcode.com/problems/subarray-sums-divisible-by-k/description/
    
        Given an integer array nums and an integer k, 
        return the number of non-empty subarrays that have a sum divisible by k.
    
        Input: nums = [4,5,0,-2,-3,1], k = 5
        Output: 7
        Explanation: There are 7 subarrays with a sum divisible by k = 5:
        [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
    
        approach is similar to subarray sum equals to k, except instead of
        doing rem = sum - nums[i] we need to do rem = sum % nums[i] as we
        need to count frequency of a remainder as we move on the array.
    */
   

    public int subarraysDivByK(int[] nums, int k) {

        int n = nums.length;

        int sum = 0;

        // instead of map we can use an array of size k as remainder 
        // will always be 0 <= remainder < k.
        Map<Integer, Integer> freqOfRemainder = new HashMap<>();

        freqOfRemainder.put(0, 1);

        int ans = 0;

        for (int i = 0; i < n; i++) {
            sum += nums[i];
            // need to do this to handle negative remainders.
            int remainder = ((sum % k) + k) % k;

            if (freqOfRemainder.containsKey(remainder)) {
                ans += freqOfRemainder.get(remainder);
            }

            freqOfRemainder.put(remainder, freqOfRemainder.getOrDefault(remainder, 0) + 1);
        }

        return ans;

    }
    
    public static void main(String[] args) {
        int arr[] = { -1, 2, 9 };

        SubarraySumDivisibleByK obj = new SubarraySumDivisibleByK();

        System.out.println(obj.subarraysDivByK(arr, 2));
    }

}
