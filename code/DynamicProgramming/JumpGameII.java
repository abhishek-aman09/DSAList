package DynamicProgramming;

import java.util.Arrays;

public class JumpGameII {

    /* https://leetcode.com/problems/jump-game-ii
    
        You are given a 0-indexed array of integers nums of length n. You are initially positioned at index 0.
    
        Each element nums[i] represents the maximum length of a forward jump from index i. In other words,
        if you are at index i, you can jump to any index (i + j) where:
        
        0 <= j <= nums[i] and
        i + j < n
        Return the minimum number of jumps to reach index n - 1.
        The test cases are generated such that you can reach index n - 1.
    
        sol :
        the dp approach for the problem will take N^2 time. 
        this can be done in linear time using two pointer.
    
    */

    public int jump(int[] nums) {

        int n = nums.length;

        int dp[] = new int[n];
        Arrays.fill(dp, -1);


        return helper(nums, 0, n, dp);
    }
    
    int helper(int nums[], int i, int n, int dp[]) {

        if (i >= n - 1) {
            return 0;
        }

        if (dp[i] != -1) {
            return dp[i];
        }

        int minJumps = 10000000;

        for (int k = 1; k <= nums[i]; k++) {
            int jumpCost = 1 + helper(nums, k + i, n, dp);
            if (jumpCost < minJumps) {
                minJumps = jumpCost;
            }
        }

        return dp[i] = minJumps;
    }

    // two pointer
    // for each index we will calc the farthest we can reach 
    // update l to r + 1, i.e all index in current jump is covered.
    
    private int twoPointerJumps(int nums[]) {
        int n = nums.length;

        int ans = 0;

        int l = 0, r = 0;

        while (r < n - 1) {
            int farthestReach = 0;
            for (int index = l; index <= r; index++) {
                farthestReach = Integer.max(farthestReach, index + nums[index]);
            }
            l = r + 1;
            r = farthestReach;
            ans++;
        }

        return ans;
    }
    
    public static void main(String[] args) {
        int nums[] = { 2 };

        JumpGameII obj = new JumpGameII();

        System.out.println(obj.jump(nums));
    }
    
}
