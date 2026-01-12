package DynamicProgramming.PartitionDP;

import java.util.Arrays;

public class MatrixChainMultiplication {

    /* https://www.geeksforgeeks.org/problems/matrix-chain-multiplication0303/1
    Given an array arr[] which represents the dimensions of a sequence of matrices where the 
    ith matrix has the dimensions (arr[i-1] x arr[i]) for i>=1,
    find the most efficient way to multiply these matrices together.
    The efficient way is the one that involves the least number of multiplications.
    
    Matrix can be multiplied in multiple ways say if we have ABC - (A(BC)) or (AB(C))
    

    */
    

    static int matrixMultiplication(int arr[]) {

        int n = arr.length;

        int dp[][] = new int[n + 1][n + 1];

        for (int row[] : dp) {
            Arrays.fill(row, -1);
        }

        return helper(arr, 1, n - 1, dp);

    }
    
    static int helper(int arr[], int i, int j, int dp[][]) {

        if (i == j) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int ans = Integer.MAX_VALUE;

        for (int k = i; k < j; k++) {
            int sum = (arr[i - 1] * arr[k] * arr[j]) + helper(arr, i, k, dp) + helper(arr, k + 1, j, dp);

            if (sum < ans) {
                ans = sum;
            }
        }

        return dp[i][j] = ans;
    }

    static int tabulation(int arr[]) {
        int n = arr.length;

        if (n <= 2) {
            return 0;
        }

        int dp[][] = new int[n][n];

        // for tabulation, outermost loop has to start from n - 1 and 
        // come in decreasin order as for each i, we have k = i which
        // goes to j. Thus we need the right value to be precomputed,
        // which is not possible if we start i from the left.
        for (int i = n - 1; i > 0; i--) { 
            for (int j = i + 1; j < n; j++) {
                int minSteps = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    int currSteps = (arr[i - 1] * arr[k] * arr[j]) + dp[i][k] + dp[k + 1][j];
                    if (currSteps < minSteps) {
                        minSteps = currSteps;
                    }
                }
                dp[i][j] = minSteps;
            }
        }

        return dp[1][n - 1];

    }
    

    public static void main(String[] args) {
        int arr[] = {1,2,3,4,3};

        System.out.println(tabulation(arr));
    }

}
