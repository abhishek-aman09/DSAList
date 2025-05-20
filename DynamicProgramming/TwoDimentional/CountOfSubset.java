package DynamicProgramming.TwoDimentional;

public class CountOfSubset {

    public static int findWays(int nums[], int sum) {
        int n = nums.length;

        int[][] dp = new int[n + 1][sum + 1];

        int mod = 1000000007;

        dp[0][0] = 1;

        for (int i = 1; i <= n; i++) {
            // The inner loop starts from 0 and not 1 because
            // as each 0 doubles the number of subset
            //explainatin below
            for (int j = 0; j <= sum; j++) {
                // do not include
                dp[i][j] = dp[i - 1][j] % mod;

                // include
                if (nums[i - 1] <= j) {
                    dp[i][j] += (dp[i - 1][j - nums[i - 1]] % mod) % mod;
                }
            }
        }

        return dp[n][sum] % mod;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 6, 9, 8, 4, 2, 8, 4, 3, 10, 9, 8, 1, 6, 3, 9, 8, 10, 1, 3, 10, 3, 9, 9, 5, 9, 8, 10, 7, 7, 2,
                4, 9, 4, 8, 9, 10, 8, 9, 4, 2, 4, 8, 3, 5, 8, 1, 2, 5, 6, 1, 7, 8, 7, 4, 6, 10, 2, 10, 1, 1, 9, 9, 8, 8,
                10, 5, 5, 5, 10, 3, 5, 9, 2, 4, 1, 9, 6, 3, 8, 1, 3, 2, 7, 8, 10, 6, 2, 3, 10, 6, 5, 4, 2, 8, 2, 1, 3 };
        int sum = 40;

        System.out.println("Number of subsets are " + findWays(nums, sum));
    }

    /**
     * basically what it does is
     * 
     * sum = 0 1 2 3 ....
     * num 0 1
     *  0  1 2
     *  1  2
     *  2  4
     *     .
     *     .
     *     .
     *  For sum = 0 and num = {0}, the ans should be 2 [{} and {0}].
     * starting inner loop from 0 ensures that this condition is taken care of
     * and everytime a zero is encountered, the previous value is doubled
     */

}
