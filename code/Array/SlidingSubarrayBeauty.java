package Array;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

/*
https://leetcode.com/problems/sliding-subarray-beauty/

Given an integer array nums containing n integers, find the beauty of each subarray of size k.

The beauty of a subarray is the xth smallest integer in the subarray if it is negative, or 0 if there are fewer than x negative integers.

Return an integer array containing n - k + 1 integers, which denote the beauty of the subarrays in order from the first index in the array.

A subarray is a contiguous non-empty sequence of elements within an array.

Input: nums = [-1,-2,-3,-4,-5], k = 2, x = 2
Output: [-1,-2,-3,-4]
Explanation: There are 4 subarrays with size k = 2.
For [-1, -2], the 2nd smallest negative integer is -1.
For [-2, -3], the 2nd smallest negative integer is -2.
For [-3, -4], the 2nd smallest negative integer is -3.
For [-4, -5], the 2nd smallest negative integer is -4.

Approach for this problem : as nums[i] is between -50 and 50. we could maintain a freq array for the negative numbers. first for window of size k, we calculate the freq and store it.
then we iterate the freq array in reverse order and calculate the xth smallest. following from kth element to the nth element, we pop the left, insert right and adjust the frequency accordingly.

Approach for more general problem : If -10^5 <= nums[i] <= 10^5, this method won't work, time complexity will be quadratic. What can we do then, we can follow a two heap method.
Similar to median of a data stream. A max heap of size x will maintain the x largest element in window, if size go above x, we pop the element and push it into the min heap.
going through the window, we invalidate the elements from both the heaps if the element is out of range.

*/

public class SlidingSubarrayBeauty {
    
    public int[] getSubarrayBeauty(int[] nums, int k, int x) {

        int n = nums.length;

        // array of size 50 as it is given nums[i] >= 50 to count freq of negative numbers
        int freqOfNegatives[] = new int[51];
        // for window of size k, count freq of negative numbers
        for (int i = 0; i < k; i++) {
            if (nums[i] < 0) {
                int ind = nums[i] * -1;
                freqOfNegatives[ind]++;
            }
        }
        // ans array of size [n - k + 1]
        int ans[] = new int[n - k + 1];

        ans[0] = findXthNegativeNumInWin(freqOfNegatives, x); 

        int left = 0;

        for (int i = k; i < n; i++, left++) {
            if (nums[i] < 0) { // push the right el if it is negative
                int ind = nums[i] * -1;
                freqOfNegatives[ind]++;
            }

            if (nums[left] < 0) { // pop the left element if it is negative
                int ind = nums[left] * -1;
                freqOfNegatives[ind]--;
            }
            ans[left + 1] = findXthNegativeNumInWin(freqOfNegatives, x);
        }
        
        return ans;

    }
    
    private int findXthNegativeNumInWin(int freq[], int x) {
        // we go in reverse as we are storing freq of negative elements in positive form.
        for (int i = 50; i > 0; i--) {
            x -= (freq[i]);
            if (x <= 0) {
                return i * -1;
            }
        }

        return 0;
    }
    
}
