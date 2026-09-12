package Array;

import java.util.HashMap;
import java.util.Map;

public class ContiguousSubarraySum {
    // https://leetcode.com/problems/continuous-subarray-sum/description/
    /* Given an integer array nums and an integer k, return true if nums has a good subarray or false otherwise.
    
        A good subarray is a subarray where:
    
        its length is at least two, and
        the sum of the elements of the subarray is a multiple of k.
        Note that:
    
        A subarray is a contiguous part of the array.
        An integer x is a multiple of k if there exists an integer n such that x = n * k. 0 is always a multiple of k.
        
        maths : we need to find if there exist an i,j (i < j) where (pre[i] - pre[j]) % k == 0.
        i.e pre[i] % k == pre[j] % k and j != i + 1.

    */
    
    public boolean checkSubarraySum(int[] nums, int k) {

        int n = nums.length;

        int pre[] = new int[n];

        // map to store the raminder of the prefix sum
        Map<Integer, Integer> remFreq = new HashMap<>();
        
        pre[0] = nums[0] % k;

        remFreq.put(pre[0], 1); // put the remainder of to the map

        for(int i = 1; i < n; i++) {
            pre[i] = (pre[i - 1] + nums[i]) % k;
            if(pre[i] == 0) { // check if the prefix is a multiple of k
                return true;
            }

            int currRem = pre[i]; 

            if (remFreq.containsKey(currRem) && pre[i - 1] != currRem) { // if the map contains same prefix but it was not in last index. To handle conditions like 3, 1, 12 divisible by 6
                // or 2, 1, 0 divisble by 4
                return true;
            }

            remFreq.put(currRem, remFreq.getOrDefault(currRem, 0) + 1);

            if (remFreq.get(currRem) > 2) { // if total count of prefix after adding current is more than two even if pre[i] == pre[i - 1], we return true.
                return true;
            }
        }

        return false;

    }
    
    public static void main(String[] args) {
        ContiguousSubarraySum obj = new ContiguousSubarraySum();

        int nums[] = { 1, 2, 12 };
        int k = 6;

        System.out.println(obj.checkSubarraySum(nums, k));
    }
}
