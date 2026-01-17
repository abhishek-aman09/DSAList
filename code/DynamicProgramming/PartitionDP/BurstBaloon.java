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

        for (int k = i; k <= j; k++) {

            int currCost = (nums[i - 1] * nums[k] * nums[j + 1]) + helper(nums, i, k - 1, n, dp)
                    + helper(nums, k + 1, j, n, dp);

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
