package DynamicProgramming.OneDimentional;

import java.util.Arrays;

/*
 * https://www.naukri.com/code360/problems/frog-jump_3621012?leftPanelTabValue=PROBLEM
 * There is a frog on the '1st' step of an 'N' stairs long staircase.
 *  The frog wants to reach the 'Nth' stair. 'HEIGHT[i]' is the height of the '(i+1)th' stair.
 * If Frog jumps from 'ith' to 'jth' stair, the energy lost in the jump is given by absolute value of ( HEIGHT[i-1] - HEIGHT[j-1] ).
 *  If the Frog is on 'ith' staircase, he can jump either to '(i+1)th' stair or to '(i+2)th' stair.
 *  Your task is to find the minimum total energy used by the frog to reach from '1st' stair to 'Nth' stair.

For Example
If the given ‘HEIGHT’ array is [10,20,30,10], the answer 20 as the frog can jump from 1st stair to 2nd stair (|20-10| = 10 energy lost)
 and then a jump from 2nd stair to last stair (|10-20| = 10 energy lost). So, the total energy lost is 20.
 * 
 * 
 * Sample Input 1:
 * 4
 * 10 20 30 10

 * Sample Output 1:
 * 20
 */

public class FrogJump {
    
    public static int frogJump(int n, int heights[]) {

        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);

        return solve(n, heights, dp);

    }

    private static int solve(int n, int[] heights, int[] dp) {
        if (n <= 1) {
            return 0;
        }

        if (dp[n] != -1) {
            return dp[n];
        }

        if (n - 3 >= 0) {
            return dp[n] = Integer.min(
                    solve(n - 1, heights, dp) + Math.abs((heights[n - 1] - heights[n - 2])),
                    solve(n - 2, heights, dp) + Math.abs((heights[n - 1] - heights[n - 3])));
        } else {
            return dp[n] = solve(n - 1, heights, dp) + Math.abs((heights[n - 1] - heights[n - 2]));
        }
    }

    /*
     * Extention of frog jump
     * To jump from the ith step to the jth step,
     * the frog requires abs(heights[i] - heights[j]) energy, where abs() denotes the absolute difference.
     *  The frog can jump from the ith step to any step in the range [i + 1, i + k], provided it exists.
     *  Return the minimum amount of energy required by the frog to go from the 0th step to the (n-1)th step.
     */

    public int frogJump2(int[] heights, int k) {
        int n = heights.length;

        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);

        return solve2(n - 1, heights, dp, k);
    }


    private static int solve2(int n, int[] heights, int[] dp, int k) {
        if (n < 0) {
            return 0;
        }
        if (n == 1 || n == 0) {
            return dp[n] = 0;
        }

        if (dp[n] != -1) {
            return dp[n];
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 1; i <= k; i++) {
            if (n - i >= 0) {
                int temp = solve2(n - i, heights, dp, k) + Math.abs(heights[n] - heights[n - i]);
                ans = Integer.min(temp, ans);
            }
        }

        return dp[n] = ans;
    }
    
    public static void main(String[] args) {

        int[] heights = {15, 4, 1, 14, 15};
        FrogJump obj = new FrogJump();

        System.out.println(obj.frogJump2(heights, 3));
    }
    

}
