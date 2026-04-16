package StackAndQueue;

import java.util.Stack;

public class SumOfSubarraysMin {

    /*
    https://leetcode.com/problems/sum-of-subarray-minimums/description/
    
    Given an array of integers arr, find the sum of min(b), where b ranges over every (contiguous) subarray of arr. Since the answer may be large, return the answer modulo 109 + 7.
    
    
    
    Example 1:
    
    Input: arr = [3,1,2,4]
    Output: 17
    Explanation: 
    Subarrays are [3], [1], [2], [4], [3,1], [1,2], [2,4], [3,1,2], [1,2,4], [3,1,2,4]. 
    Minimums are 3, 1, 2, 4, 1, 1, 2, 1, 1, 1.
    Sum is 17.
    
    
    Approach - maintain a stack in increasin order. wheneever encounter a value
    smaller than stack top, pop it and count the number of subarrays 
    smallest element for. 
    
    Maths - 
    1. The Mathematical Formula
    If an element at curr has:
    
    L elements to its left before hitting a smaller value (or the start of the array).
    
    R elements to its right before hitting a smaller value (or the end of the array).
    
    The number of subarrays where arr[curr] is the minimum is:
    
    (L+1)×(R+1)
    
    
    */

    public int sumSubarrayMins(int[] arr) {

        int n = arr.length;

        Stack<Integer> stk = new Stack<>();

        final int MOD = 1000000007;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            while (!stk.isEmpty() && arr[stk.peek()] > arr[i]) {
                int curr = stk.pop();
                int prev = stk.isEmpty() ? -1 : stk.peek();
                int numOfLeftEls = curr - prev;
                int numOfRightEls = i - curr;
                int numOfSubarrays = numOfLeftEls * numOfRightEls;
                ans = (int) ((ans + (long) numOfSubarrays * arr[curr]) % MOD);
            }

            stk.add(i);
        }

        while (!stk.isEmpty()) {
            int curr = stk.pop();
            int prev = stk.isEmpty() ? -1 : stk.peek();
            int numOfLeftEls = curr - prev;
            int numOfRightEls = n - curr;
            int numOfSubarrays = numOfLeftEls * numOfRightEls;
            ans = (int) ((ans + (long) numOfSubarrays * arr[curr]) % MOD);
        }

        return ans;

    }
    
    public static void main(String[] args) {
        SumOfSubarraysMin obj = new SumOfSubarraysMin();

        int arr[] = { 11, 81, 94, 43, 3 };
        System.out.println(obj.sumSubarrayMins(arr));
    }
    
}
