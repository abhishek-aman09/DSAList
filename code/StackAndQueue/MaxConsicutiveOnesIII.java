package StackAndQueue;

import java.util.LinkedList;
import java.util.Queue;

public class MaxConsicutiveOnesIII {
    /* https://leetcode.com/problems/max-consecutive-ones-iii/description/
    
    Given a binary array nums and an integer k, return the maximum number of consecutive 1's in the array if you can flip at most k 0's.
    
    Input: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
    Output: 6
    Explanation: [1,1,1,0,0,1,1,1,1,1,1]
    
    Approach - queue based approach where we store the position
    where each 0 which is flipped till size k. if queue size is
    greater than k, remove the first 0 and insert in current and calculate the size.
    
    */
   
    public int longestOnes(int[] nums, int k) {

        int n = nums.length;
        Queue<Integer> zeroPos = new LinkedList<>();

        int ans = 0;
        int temp = 0;

        for(int i = 0; i < n; i++) {
            if(nums[i] == 1) {
                temp++;
            } else {
                if(k > 0) {
                    zeroPos.add(i);
                    k--;
                    temp++;
                } else {
                    if(!zeroPos.isEmpty()) {
                        int firstZeroPos = zeroPos.poll();
                        temp = i - firstZeroPos;
                        zeroPos.add(i);
                    } else {
                        temp = 0;
                    }
                }
            }

            if(ans < temp) {
                ans = temp;
            }
        }

        return ans;
        
    }
}
