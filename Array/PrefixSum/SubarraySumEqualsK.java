package Array.PrefixSum;

import java.util.HashMap;
import java.util.Map;

public class SubarraySumEqualsK {
    /* https://leetcode.com/problems/subarray-sum-equals-k/description/
        Input: nums = [1,1,1], k = 2
        Output: 2

        Use hashmap 
    */
    
    public int subarraySum(int[] nums, int goal) {

        int n = nums.length;

        Map<Integer, Integer> freq = new HashMap<>();

        int ans = 0, sum = 0;

        freq.put(0, 1);

        for (int i = 0; i < n; i++) {
            sum += nums[i];

            int rem = sum - goal;

            if (freq.containsKey(rem)) {
                ans += freq.get(rem);
            }
            // System.out.println(sum);

            freq.put(sum, freq.getOrDefault(sum, 0) + 1);
        }

        return ans;

    }
    
}
