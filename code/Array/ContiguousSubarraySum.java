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
        i.e pre[i] % k == pre[j] % k.
    
        special conditions 
        if there exist a 0 then pre[i] == pre[j] for j = i + 1 but prefix sum is not divisible by k;
        but if there exists two 0 then there does exist a subarray i.e. [0, 0]. So,
        check of freq of each pre[i] % k. if it is > 2, return true.
    */
    
    public boolean checkSubarraySum(int[] nums, int k) {

        int n = nums.length;

        int pre[] = new int[n];
        Map<Integer, Integer> freq = new HashMap<>();

        pre[0] = nums[0];

        for (int i = 1; i < n; i++) {
            pre[i] = pre[i - 1] + nums[i];
            // if the prefix sum is divisble by k, return true
            if (pre[i] % k == 0) {
                return true;
            }
        }

        for (int i = 0; i < n; i++) {
            pre[i] = pre[i] % k;
            // 0 repeats the pre sum so if there exist one 0, do not cosider it
            if (freq.containsKey(pre[i]) && nums[i] > 0) {
                return true;
            }

            freq.put(pre[i], freq.getOrDefault(pre[i], 0) + 1);
            // if freq of any pre sum is greater than 2, return true
            if (freq.get(pre[i]) > 2) {
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
