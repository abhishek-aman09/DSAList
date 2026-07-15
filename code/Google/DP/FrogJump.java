package Google.DP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FrogJump {

    /*
    https://leetcode.com/problems/frog-jump/description/
    
    A frog is crossing a river. The river is divided into some number of units, and at each unit, 
    there may or may not exist a stone. The frog can jump on a stone, but it must not jump into the water.
    
    Given a list of stones positions (in units) in sorted ascending order, 
    determine if the frog can cross the river by landing on the last stone. Initially, 
    the frog is on the first stone and assumes the first jump must be 1 unit.
    
    If the frog's last jump was k units, its next jump must be either k - 1, k, or k + 1 units. 
    The frog can only jump in the forward direction.
    
    Input: stones = [0,1,3,5,6,8,12,17]
    Output: true
    Explanation: The frog can jump to the last stone by jumping 1 unit to the 2nd stone, then 2 units to the 3rd stone, 
    then 2 units to the 4th stone, then 3 units to the 6th stone, 4 units to the 7th stone, and 5 units to the 8th stone.
    
    
    Approach : create a map of stonePos -> index in arr. Standing at current position, we check if k - 1, k and k + 1
    exist in the map and they are greater than 0 (to avoid infinite recursion)
    
    if it does we call the three scenarios and return the OR of them.
    
    for dp we have size len * len as we are traversing indices, we cannot go beyond len
    
    why stepSize limit is len? 
    the first step is 0, next max 1, next max 2, next max 3... similarly the n - 1th step can be max of n - 1.
    and on nth step, you should either be reaching end or out of bounds, hence we can say step size will stay within len limits
    */
    public boolean canCross(int[] stones) {

        Map<Integer, Integer> mapStones = new HashMap<>();

        for (int i = 0; i < stones.length; i++) { // create map stonePos -> index
            mapStones.put(stones[i], i);
        }

        int dp[][] = new int[stones.length][stones.length];

        for (int row[] : dp) {
            Arrays.fill(row, -1);
        }

        return helper(0, stones, mapStones, 0, dp);

    }
    
    private boolean helper(int currIndx, int[] stones, Map<Integer, Integer> mapStones, int currStep, int[][] dp) {

        if (currIndx < 0 || currIndx >= stones.length) { // if we have moved out of bound, return false
            return false;
        }

        if (currIndx == stones.length - 1) { // if we have reached end stone, return true
            return true;
        }

        if (dp[currIndx][currStep] != -1) {
            return dp[currIndx][currStep] == 1;
        }

        boolean sameStep = false;

        if (currStep > 0) { // if we are planning to take k steps, we check if any stone exist in that position
            int nextTargetStep = stones[currIndx] + currStep;
            if (mapStones.containsKey(nextTargetStep)) {
                sameStep = helper(mapStones.get(nextTargetStep), stones, mapStones, currStep, dp);
            }
        }


        boolean oneLessThanCurr = false;

        if (currStep - 1 > 0) { // same check for k - 1
            int nextTargetStep = stones[currIndx] + currStep - 1;
            if (mapStones.containsKey(nextTargetStep)) {
                oneLessThanCurr = helper(mapStones.get(nextTargetStep), stones, mapStones, currStep - 1, dp);
            }
        }
        
        
        boolean oneMoreThanCurr = false;

        if (currStep + 1 > 0) { // similar for k + 1
            int nextTargetStep = stones[currIndx] + currStep + 1;
            if (mapStones.containsKey(nextTargetStep)) {
                oneMoreThanCurr = helper(mapStones.get(nextTargetStep), stones, mapStones, currStep + 1, dp);
            }
        }

        boolean result = oneLessThanCurr || sameStep || oneMoreThanCurr; // get their result, store in dp and return

        dp[currIndx][currStep] = result ? 1 : 0;

        return result;
    }
    
    public static void main(String[] args) {
        FrogJump obj = new FrogJump();

        int arr[] = new int[] { 0, 1, 3, 5, 6, 8, 12, 17 };

        System.out.println(obj.canCross(arr));
    }
}
