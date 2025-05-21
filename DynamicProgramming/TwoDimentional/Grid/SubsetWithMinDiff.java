package DynamicProgramming.TwoDimentional.Grid;

import java.util.Arrays;

public class SubsetWithMinDiff {

    /*
     * Given an array arr[]  containing non-negative integers, 
     * the task is to divide it into two sets set1 and set2 
     * such that the absolute difference between their sums is minimum and find the minimum difference.
     * 
     * Approach - find sumset sum till sum/2 using TABULATION ONLY^
     * for nth row, check from sum to 0th column. First true will be the maximum sum that can
     * be acheived that is closest to sum/2.
     * 
     * ^ - Tablulation gives you result for all the sub-problems from sum to 0.
     * Memozisation need not to evaluate every scenario as it only make recursive calls which 
     * can be obtained by subtraction array elements from sum. eg. if sum is 30 and
     * array elements are 10, 11, 12, 13, 14. hence no recursive calls will be made for
     * sum value 20 to 29 as it cannot be acheived.
     * 
     * https://www.geeksforgeeks.org/problems/minimum-sum-partition3317/1
     * 
     * => Altenatively, this can be solved using memozization, we will need to run 
     * loop from sum/2 to 0 and the first subset that exist. time complexity falls to 
     *     n * sum * sum
     * 
     */
    
    int minDifference(int arr[]) {
        int n = arr.length;
        int sum = 0;

        for (int i = 0; i < n; i++) {
            sum += arr[i];
        }
        int finalSum = sum;
        sum /= 2;

        Boolean dp[][] = new Boolean[n + 1][sum + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], false);
        }

        solve(n, sum, arr, dp);

        for (int i = sum; i >= 0; i--) {
            if (dp[n][i]) {
                sum = i;
                break;
            }
        }

        return finalSum - (2 * sum);
    }

    static Boolean solve(int n, int sum, int arr[], Boolean dp[][]) {
        
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (arr[i - 1] <= j) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - arr[i - 1]];
                }
            }
        }

        return dp[n][sum];
    }

    public static void main(String[] args) {
        int arr[] = { 10, 11, 12, 13, 14 };
        SubsetWithMinDiff obj = new SubsetWithMinDiff();
        System.out.println("Min diff is " + obj.minDifference(arr));
    }
}
