package DynamicProgramming.TwoDimentional;

import java.util.Arrays;

public class RodCutting {
    /*
     * Given a rod of length ‘N’ units. The rod can be cut into different sizes and each size has a cost associated with it.
     * Determine the maximum cost obtained by cutting the rod and selling its pieces.
    
        Note:
        1. The sizes will range from 1 to ‘N’ and will be integers.    
        2. The sum of the pieces cut should be equal to ‘N’.
        3. Consider 1-based indexing.
        
        5
        2 5 7 8 10
        Sample Output 1:
        12
        Explanation of sample input 1:
        Test case 1:
        
        All possible partitions are:
        1,1,1,1,1           max_cost=(2+2+2+2+2)=10
        1,1,1,2             max_cost=(2+2+2+5)=11
        1,1,3               max_cost=(2+2+7)=11
        1,4                 max_cost=(2+8)=10
        5                   max_cost=(10)=10
        2,3                 max_cost=(5+7)=12
        1,2,2               max _cost=(1+5+5)=12    
        
        Clearly, if we cut the rod into lengths 1,2,2, or 2,3, we get the maximum cost which is 12.
    
        https://www.naukri.com/code360/problems/rod-cutting-problem_800284?source=youtube&campaign=striver_dp_videos&utm_source=youtube&utm_medium=affiliate&utm_campaign=striver_dp_videos&leftPanelTabValue=PROBLEM
    
    */
    public static int cutRod(int price[], int rodLength) {
        int n = price.length;

        int dp[][] = new int[n + 1][rodLength + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return solve(price, rodLength, n, dp);

	}

    static int solve(int nums[], int rodLength, int n, int dp[][]) {
        if (rodLength == 0 || n <= 0) {
            return dp[n][rodLength] = 0;
        }

        if (dp[n][rodLength] != -1) {
            return dp[n][rodLength];
        }

        int notIncluded = solve(nums, rodLength, n - 1, dp);

        int included = 0;

        if (nums[n - 1] <= rodLength) {
            included = nums[n - 1] + solve(nums, rodLength - nums[n - 1], n, dp);
        }

        return dp[n][rodLength] = Integer.max(notIncluded, included);

    }
}
