package Array;

public class JumpGame {

    // https://leetcode.com/problems/jump-game/description/
    /*
    You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position.
    
    Return true if you can reach the last index, or false otherwise.
    
    Input: nums = [2,3,1,1,4]
    Output: true
    Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
    
    
    */
    // Just for each index, keep a track of max index you can reach
    // if your curr index matches max index, you are stuck.
    
    public boolean canJump(int[] nums) {

        int n = nums.length;
        int maxLength = 0;

        for (int i = 0; i < n; i++) {

            maxLength = Integer.max(maxLength, i + nums[i]);

            if (maxLength >= nums.length - 1) {
                return true;
            }

            if (i >= maxLength) {
                return false;
            }

        }

        return false;

    }

    public static void main(String[] args) {
        JumpGame obj = new JumpGame();

        int arr[] = {3,2,1,1,4};

        System.out.println(obj.canJump(arr));
    }
    
}
