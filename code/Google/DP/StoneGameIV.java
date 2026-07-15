package Google.DP;

import java.util.Arrays;

public class StoneGameIV {

    /*
    https://leetcode.com/problems/stone-game-iv/description/
    
    Alice and Bob take turns playing a game, with Alice starting first.
    
    Initially, there are n stones in a pile. On each player's turn, that player makes a move consisting of 
    removing any non-zero square number of stones in the pile.
    
    Also, if a player cannot make a move, he/she loses the game.
    
    Given a positive integer n, return true if and only if Alice wins the game otherwise return false, 
    assuming both players play optimally.
    
    Input: n = 1
    Output: true
    Explanation: Alice can remove 1 stone winning the game because Bob doesn't have any moves.
    
    Approach : Best stratergy for both, for each number that can be removed, we check if next return true, 
            if it does not, we do not play, as it is alice turn first, we consider what helper returns us
    */

    public boolean winnerSquareGame(int n) {

        Boolean dp[] = new Boolean[n + 1];

        Arrays.fill(dp, null);

        return helper(n, dp);
    }
    
    private boolean helper(int stonesRemaining,  Boolean dp[]) {
        if (stonesRemaining == 0) {
            return false;
        }
        
        if (dp[stonesRemaining] != null) {
            return dp[stonesRemaining];
        }


        int maxLim = getCeilSqrt(stonesRemaining); // max we should go is to sqrt of rem stones as they only remove
        // perfect squares

        for (int i = 1; i <= maxLim; i++) {
            // if current square is in bound and removing them will result in defeat of next opponent, we consider it
            if ((i * i) <= stonesRemaining && helper(stonesRemaining - (i * i), dp) == false) { 
                return dp[stonesRemaining] = true;
            }
        }

        // if no move can make us win, we return false
        return dp[stonesRemaining] = false;
    }

    private int getCeilSqrt(int num) {
        return (int) (Math.ceil(Math.sqrt(num)));
    }
    
}
