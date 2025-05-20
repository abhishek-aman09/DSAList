package DynamicProgramming.TwoDimentional;

public class EqualSumPartition {

    public boolean canPartition(int[] nums) {
        int n = nums.length;

        int sum = 0;
        for (int el : nums) {
            sum += el;
        }

        if (sum % 2 != 0) {
            return false;
        }

        sum /= 2;

        boolean[][] dp = new boolean[n + 1][sum + 1];
        
        dp[0][0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                // do not include
                dp[i][j] = dp[i - 1][j];

                // include
                if (nums[i - 1] <= j) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }

        return dp[n][sum];

    }
    

    public static void main(String[] args) {
        
    }
}
