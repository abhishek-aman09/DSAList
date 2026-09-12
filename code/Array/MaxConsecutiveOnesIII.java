package Array;

import java.util.LinkedList;
import java.util.Queue;

public class MaxConsecutiveOnesIII {

    /* https://leetcode.com/problems/max-consecutive-ones-iii/description/
    Given a binary array nums and an integer k, return the maximum number of consecutive 1's in the array if you can flip at most k 0's.
    
    Input: nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3
    Output: 10
    Explanation: [0,0,{1,1,1,1,1,1,1,1,1,1},0,0,0,1,1,1,1]
    
    approach : queue based, if we encounter a one increment count, if we encounter zero, check if we have a flipped zero wiht its pos in queue that we have encountered.
    if yes, current window can go from last zero pos to i - 1 if we cannot flip the current, if yes flip it and store in queue, calculate max
    */

    public int longestOnes(int[] nums, int k) {

        int n = nums.length;
        Queue<Integer> zeroPos = new LinkedList<>(); // queue to store zero that has been flipped

        int ans = 0, temp = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) { // if it 1, increment count
                temp++;
            } else {
                if (k > 0) { // if we can flips remaining, flip the current and store it pos in queue
                    zeroPos.add(i);
                    k--;
                    temp++;
                } else { // else, we check if we have any pos in queue which we have flipped, if yes, calculate the current range and update it, else set temp to 0
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
