package Google.DP;

import java.util.Arrays;

public class EggDrop {

    /*
    https://leetcode.com/problems/super-egg-drop/description/
    
    You are given k identical eggs and you have access to a building with n floors labeled from 1 to n.
    
    You know that there exists a floor f where 0 <= f <= n such that any egg dropped at a floor higher than f 
    will break, and any egg dropped at or below floor f will not break.
    
    Each move, you may take an unbroken egg and drop it from any floor x (where 1 <= x <= n). 
    If the egg breaks, you can no longer use it. However, if the egg does not break, you may reuse it in future moves.
    
    Return the minimum number of moves that you need to determine with certainty what the value of f is.
    
    Input: k = 1, n = 2
    Output: 2
    Explanation: 
    Drop the egg from floor 1. If it breaks, we know that f = 0.
    Otherwise, drop the egg from floor 2. If it breaks, we know that f = 1.
    If it does not break, then we know f = 2.
    Hence, we need at minimum 2 moves to determine with certainty what the value of f is.
    
    Approach  : Standard egg dropping problem, varian to MCM
    
    */
    

    public int superEggDrop(int k, int n) {

        int dp[][] = new int[k + 1][n + 1];

        for (int row[] : dp) {
            Arrays.fill(row, -1);
        }

        return helper(k, n, dp);
    }
    
    private int helper(int eggs, int floors, int dp[][]) {

        if (eggs == 1 || floors <= 1) { // If we have one egg, we go linearly to find the floor
            return floors; // if we have less than 2 floors, we have 1 or 0 attempt
        }

        if (dp[eggs][floors] != -1) {
            return dp[eggs][floors];
        }

        int minAttempts = Integer.MAX_VALUE;

        int l = 0, r = floors; //binary search as floors are in increasing order

        while (l <= r) {

            int mid = l + (r - l) / 2; // find the middle flooe

            int doBreak = helper(eggs - 1, mid - 1, dp); // recursively call if the egg breaks from mid
            int noBreak = helper(eggs, floors - mid, dp); // recursively call if egg does not break

            if (doBreak > noBreak) { // if break count is more, we decrease the number of floors
                r = mid - 1;
            } else { // we look for larger half
                l = mid + 1;
            }

            minAttempts = Integer.min(minAttempts, 1 + Integer.max(doBreak, noBreak)); // we return the min of ans vs
            // one attempt + max of what we got from break and no break (worst case scenario)
        }

        return dp[eggs][floors] = minAttempts;
    }
    
    // standard egg drop method time N * (K * K)
    private int standardEggDrop(int eggs, int floors, int dp[][]) {

        if (eggs == 1 || floors <= 1) {
            return floors;
        }

        if (dp[eggs][floors] != -1) {
            return dp[eggs][floors];
        }

        int minAttempts = Integer.MAX_VALUE;

        int l = 0, r = floors;

        for (int i = 0; i <= floors; i++) {



            int doBreak = helper(eggs - 1, i, dp);
            int noBreak = helper(eggs, floors - i, dp);
            

            minAttempts = Integer.min(minAttempts, 1 + Integer.max(doBreak, noBreak));
        }

        return dp[eggs][floors] = minAttempts;
    }

}
