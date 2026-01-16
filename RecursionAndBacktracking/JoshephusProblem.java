package RecursionAndBacktracking;

public class JoshephusProblem {
    
    // https://leetcode.com/problems/find-the-winner-of-the-circular-game/description/

    /*
    There are n friends that are playing a game. The friends are sitting in a circle and are numbered from 1 to n in clockwise order. More formally, moving clockwise from the ith friend brings you to the (i+1)th friend for 1 <= i < n, and moving clockwise from the nth friend brings you to the 1st friend.
    
    The rules of the game are as follows:
    
    Start at the 1st friend.
    Count the next k friends in the clockwise direction including the friend you started at. The counting wraps around the circle and may count some friends more than once.
    The last friend you counted leaves the circle and loses the game.
    If there is still more than one friend in the circle, go back to step 2 starting from the friend immediately clockwise of the friend who just lost and repeat.
    Else, the last friend in the circle wins the game.
    Given the number of friends, n, and an integer k, return the winner of the game.
    
    Approach : Standard Joshephus Problem. 
    The solution of current f(n,k) lies in f(n - 1,k) when one person
    is out of the game. if there is only one person left, he is the winner.
    
    also the sword/count shift by k every time it is counted or drawn.
    hence every recursive call add k with mod n as it is a circle.
    The answer will be zero based.
    */

    public int findTheWinner(int n, int k) {

        int ans = helper(n, k);

        return ans + 1;
    }
    
    private int helper(int n, int k) {
        if (n == 1) {
            return 0;
        }

        return (helper(n - 1, k) + k) % n;
    }

    // Iterative approach
    private int iterative(int n, int k) {
        if (n == 1) {
            return 1;
        }

        int survivalIndex = 0;

        // for each i increase the survival index by k and mod with i (not n),
        // as i is the current loop lenght for each iteration.
        for (int i = 2; i <= n; i++) {
            survivalIndex = (survivalIndex + k) % i;
        }

        return survivalIndex + 1;
    }

}
