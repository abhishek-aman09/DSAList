package Array.PrefixSum;

import java.util.HashMap;
import java.util.Map;

public class SubarraySumEqualsK {
    /* https://leetcode.com/problems/subarray-sum-equals-k/description/
        Input: nums = [1,1,1], k = 2
        Output: 2
    
        Approach : User prefix sum and hashmap based approach
        
        calculate prefix sum and strore them in map with frequency
        for any sum chech the rem you need to get the su of subarray
        if it exist in map, add the freq in the ans.
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

            freq.put(sum, freq.getOrDefault(sum, 0) + 1);
        }

        return ans;

    }
    
}
