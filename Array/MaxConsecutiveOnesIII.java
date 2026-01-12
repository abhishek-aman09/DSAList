package Array;

import java.util.LinkedList;
import java.util.Queue;

public class MaxConsecutiveOnesIII {
    
    /* https://leetcode.com/problems/max-consecutive-ones-iii/description/
    Given a binary array nums and an integer k, return the maximum number of consecutive 1's in the array if you can flip at most k 0's.

    Input: nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3
    Output: 10
    Explanation: [0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
    Bolded numbers were flipped from 0 to 1. The longest subarray is underlined.
    */

    public int longestOnes(int[] nums, int k) {

        int n = nums.length;
        Queue<Integer> zeroPos = new LinkedList<>();

        int ans = 0, temp = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                temp++;
            } else {
                if (k > 0) {
                    zeroPos.add(i);
                    k--;
                    temp++;
                } else {
                    if (!zeroPos.isEmpty()) {
                        int firstZeroPos = zeroPos.poll();
                        temp = i - firstZeroPos;
                        zeroPos.add(i);
                    } else {
                        temp = 0;
                    }
                }
            }

            if (ans < temp) {
                ans = temp;
            }
        }

        return ans;

    }
    
}
