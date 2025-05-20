package DynamicProgramming.TwoDimentional;

import java.util.Arrays;

public class Triangle {

    /*
     * You are given a triangular array/list 'TRIANGLE'. Your task is to return the minimum path sum to reach from the top to the bottom row.
    
        The triangle array will have N rows and the i-th row, where 0 <= i < N will have i + 1 elements.
        
        You can move only to the adjacent number of row below each step.
         For example, if you are at index j in row i,
         then you can move to i or i + 1 index in row j + 1 in each step.
        
        For Example :
        If the array given is 'TRIANGLE' = [[1], [2,3], [3,6,7], [8,9,6,1]] the triangle array will look like:
    
        Sample input 
        4
        2 
        3 4
        6 5 7
        4 1 8 3
    
        Sample output 
        11

        https://www.naukri.com/code360/problems/triangle_1229398?leftPanelTabValue=PROBLEM
    
    */
    

    public static int minimumPathSum(int[][] triangle, int n) {

        int dp[][] = new int[n + 1][n + 2];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }

        solve(0, 0, n, triangle, dp);

        return dp[0][0];
    }

    static int solve(int i, int j, int n, int triangle[][], int dp[][]) {
        if (i == n - 1 && j <= i) {
            return dp[i][j] = triangle[i][j];
        }

        if (i >= n || j > i) {
            return Integer.MAX_VALUE;
        }

        if (dp[i][j] != Integer.MAX_VALUE) {
            return dp[i][j];
        }

        return dp[i][j] = Integer.min(solve(i + 1, j, n, triangle, dp), solve(i + 1, j + 1, n, triangle, dp))
                + triangle[i][j];
    }
    
    public static void main(String[] args) {
        int arr[][] = {
            { 2 },
            { 3, 4},
            { 6, 5, 7 },
            { 4, 1, 8, 3 }
        };

        System.out.println(Triangle.minimumPathSum(arr, 4));
        
    }
}
