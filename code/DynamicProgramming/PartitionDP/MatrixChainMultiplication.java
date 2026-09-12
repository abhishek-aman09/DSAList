package DynamicProgramming.PartitionDP;

import java.util.Arrays;

public class MatrixChainMultiplication {

    /* https://www.geeksforgeeks.org/problems/matrix-chain-multiplication0303/1
    Given an array arr[] which represents the dimensions of a sequence of matrices where the 
    ith matrix has the dimensions (arr[i-1] x arr[i]) for i>=1,
    find the most efficient way to multiply these matrices together.
    The efficient way is the one that involves the least number of multiplications.
    
    Matrix can be multiplied in multiple ways say if we have ABC - (A(BC)) or (AB(C))
    Input: arr[] = [2, 1, 3, 4]
    Output: 20
    Explanation: There are 3 matrices of dimensions 2 × 1, 1 × 3, and 3 × 4, Let this 3 input matrices be M1, M2, and M3. There are two ways to multiply: ((M1 x M2) x M3) and (M1 x (M2 x M3)), note that the result of (M1 x M2) is a 2 x 3 matrix and result of (M2 x M3) is a 1 x 4 matrix. 
    ((M1 x M2) x M3)  requires (2 x 1 x 3) + (2 x 3 x 4) = 30 
    (M1 x (M2 x M3))  requires (1 x 3 x 4) + (2 x 1 x 4) = 20. 
    The minimum of these two is 20.
    
    
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

        // BASE CASE: Single matrix
        // When i == j, we are considering only one matrix (e.g., matrix M_i).
        // Multiplying a single matrix with itself requires 0 scalar operations.
        if (i == j) {
            return 0;
        }

        // MEMOIZATION CHECK:
        // dp[i][j] stores the minimum multiplication cost to compute 
        // the product of matrices M_i through M_j.
        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int ans = Integer.MAX_VALUE;

        // SPLIT LOOP:
        // Try placing the final outer parenthesis at every possible split point 'k'.
        // The chain (M_i * ... * M_j) is partitioned into two sub-chains:
        //   Left sub-chain:  (M_i * ... * M_k)
        //   Right sub-chain: (M_{k+1} * ... * M_j)
        // Here, k can range from i to j - 1.
        for (int k = i; k < j; k++) {

            // 1. DIMENSIONS OF RESULTING MATRICES:
            // - Left chain (i to k) produces a single matrix of dimension:
            //     arr[i - 1] x arr[k]
            // - Right chain (k + 1 to j) produces a single matrix of dimension:
            //     arr[k] x arr[j]
            //
            // 2. COST TO MULTIPLY THE TWO RESULTING MATRICES:
            // Multiplying (arr[i-1] x arr[k]) by (arr[k] x arr[j]) costs:
            //     arr[i - 1] * arr[k] * arr[j]
            //
            // 3. RECURSIVE SUB-PROBLEMS:
            // - helper(arr, i, k, dp):     min cost to form the left matrix
            // - helper(arr, k + 1, j, dp): min cost to form the right matrix
            int sum = (arr[i - 1] * arr[k] * arr[j]) 
                    + helper(arr, i, k, dp) 
                    + helper(arr, k + 1, j, dp);

            // Track the optimal (minimum) parenthesization
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
        // come in decreasing order as for each i, we have k = i which
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
