package DynamicProgramming.PartitionDP;


public class BurstBaloon {

    /* https://leetcode.com/problems/burst-balloons/description/
    
    You are given n balloons, indexed from 0 to n - 1. Each balloon is painted with a number on it represented by an array nums. You are asked to burst all the balloons.
    
    If you burst the ith balloon, you will get nums[i - 1] * nums[i] * nums[i + 1] coins. If i - 1 or i + 1 goes out of bounds of the array, then treat it as if there is a balloon with a 1 painted on it.
    
    Return the maximum coins you can collect by bursting the balloons wisely.
    
    Input: nums = [3,1,5,8]
    Output: 167
    Explanation:
    nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
    coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 167
    
    Approach : Similar to Leetcode 1547 : MinimmumCostToCutTheStick
    
    we have to insert left lim = right lim = 1. We then traverse from
    index 1 to n + 1 (whole array) and calculate the curr cost with left
    and right recursive call. The main differnce is, When we burst a baloon,
    the left and right subarray does not become independent. Hence, the right
    of curr left baloon is the right of curr baloon, hence the codition
    cost = nums[i - 1] * nums[k] * nums[j + 1].
    
    for each k between i and j, we assume that k will the last baloon in the process to burst so that we are able to independently calculate left and right subpart.
    if k is last, we call left(i, k - 1) as helper(i, j) so that the current kth baloon becomes right end for the left subpart when we do (j + 1) while calculation
    similarly for right we pass right(k + 1, j) as i - 1 will point to current baloon as the left one.
    
    */
    

    public int maxCoins(int[] nums) {

        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }

        int numsWithBoundaries[] = new int[n + 2];
        numsWithBoundaries[0] = 1;
        numsWithBoundaries[n + 1] = 1;

        for (int i = 1; i <= n; i++) {
            numsWithBoundaries[i] = nums[i - 1];
        }

        int dp[][] = new int[n + 2][n + 2];
        
        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < n + 2; j++) {
                if (i > j) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = -1;
                }
            }
        }
        
        System.out.println(tabulation(numsWithBoundaries, n + 2, dp));

        return helper(numsWithBoundaries, 1, n, n + 2, dp);

    }

    private int helper(int nums[], int i, int j, int n, int dp[][]) {

        if (i > j) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int maxCost = Integer.MIN_VALUE;

        // We iterate over every possible balloon k in range [i, j]
        // and assume 'k' is the LAST balloon to be burst in this entire subarray [i, j].
        for (int k = i; k <= j; k++) {

            // WHY nums[i - 1] * nums[k] * nums[j + 1]?
            // Since 'k' is the LAST balloon standing in the interval [i, j]:
            // - All balloons between i and k-1 are already popped.
            // - All balloons between k+1 and j are already popped.
            // Therefore, the immediate unpopped neighbor to the left of k is nums[i - 1],
            // and the immediate unpopped neighbor to the right of k is nums[j + 1].
            int burstCost = nums[i - 1] * nums[k] * nums[j + 1];

            // SUBPROBLEM 1: helper(nums, i, k - 1, n, dp)
            // Solves the optimal cost of bursting all balloons in [i, k - 1].
            // - When k == i: calls helper(nums, i, i - 1), which hits (i > j) -> returns 0.
            //   This correctly reflects that there are no balloons to the left of k.
            int leftCost = helper(nums, i, k - 1, n, dp);

            // SUBPROBLEM 2: helper(nums, k + 1, j, n, dp)
            // Solves the optimal cost of bursting all balloons in [k + 1, j].
            // - When k == j: calls helper(nums, j + 1, j), which hits (i > j) -> returns 0.
            //   This correctly reflects that there are no balloons to the right of k.
            int rightCost = helper(nums, k + 1, j, n, dp);

            // Total cost if balloon k is popped last
            int currCost = burstCost + leftCost + rightCost;

            // Maximize across all candidates for the 'last' balloon
            if (currCost > maxCost) {
                maxCost = currCost;
            }
        }

        return dp[i][j] = maxCost;
    }
    
    private int tabulation(int nums[], int m, int dp[][]) {


        for (int i = m - 2; i > 0; i--) {
            for (int j = 1; j < m - 1; j++) {
                if (i > j) {
                    continue;
                }
                for (int k = i; k <= j; k++) {
                    int currCost = nums[i - 1] * nums[k] * nums[j + 1] + dp[i][k - 1] + dp[k + 1][j];
                    dp[i][j] = Integer.max(dp[i][j], currCost);
                }
            }
        }

        return dp[1][m - 2];
    }
    
    public static void main(String[] args) {
        BurstBaloon obj = new BurstBaloon();

        int arr[] = { 3, 1, 5, 8 };
        System.out.println(obj.maxCoins(arr));
    }
    
}
