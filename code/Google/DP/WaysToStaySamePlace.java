package Google.DP;

public class WaysToStaySamePlace {

    /*
    https://leetcode.com/problems/number-of-ways-to-stay-in-the-same-place-after-some-steps/description/
    
    You have a pointer at index 0 in an array of size arrLen. At each step, you can move 1 position to the left, 
    1 position to the right in the array, or stay in the same place (The pointer should not be placed outside the array at any time).
    
    Given two integers steps and arrLen, return the number of ways such that your pointer is still at index 0 
    after exactly steps steps. Since the answer may be too large, return it modulo 109 + 7.
    
    Input: steps = 3, arrLen = 2
    Output: 4
    Explanation: There are 4 differents ways to stay at index 0 after 3 steps.
    Right, Left, Stay
    Stay, Right, Left
    Right, Stay, Left
    Stay, Stay, Stay
    
    Approach : firstly, reduce the arrLen to steps (to avoid MLE), as max right we can go is steps.
    further, its standard dp
    */

    private static final int MOD = 1000000007;
    
    public int numWays(int steps, int arrLen) {

        arrLen = Math.min(arrLen, steps);

        long dp[][] = new long[arrLen][steps + 1];

        for(int i = 0; i < arrLen; i ++) {
            for(int j = 0; j <= steps; j++) {
                dp[i][j] = -1;
            }
        }

        return(int)(helper( 0, arrLen, steps, dp));
    }
    
    private long helper(int currPos, int arrLen, int steps, long dp[][]) {

        if (currPos < 0 || currPos >= arrLen || currPos > steps) { // if out of bounds or steps, return 0
            return 0l;
        }

        
        if (steps == 0) { // if we have 0 steps, check if we are at starting pos
            return currPos == 0 ? 1 : 0;
        }

        if (dp[currPos][steps] != -1) {
            return dp[currPos][steps];
        }

        long stay = helper(currPos, arrLen, steps - 1, dp); // we stay

        long left = helper(currPos - 1, arrLen, steps - 1, dp); // we go right

        long right = helper(currPos + 1, arrLen, steps - 1, dp); // we go left

        return dp[currPos][steps] = (stay + right + left) % MOD; // sum it up and mod it

    }
}
