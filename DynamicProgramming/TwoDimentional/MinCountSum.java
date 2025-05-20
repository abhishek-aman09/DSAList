package DynamicProgramming.TwoDimentional;

import java.util.Arrays;

public class MinCountSum {
    
    /*
     * You are given an array of ‘N’ distinct integers and an integer ‘X’
     * representing the target sum. You have to tell the minimum number of elements
     * you have to take to reach the target sum ‘X’.
     * 
     * If N=3 and X=7 and array elements are [1,2,3]. 
        Way 1 - You can take 4 elements  [2, 2, 2, 1] as 2 + 2 + 2 + 1 = 7.
        Way 2 - You can take 3 elements  [3, 3, 1] as 3 + 3 + 1 = 7.
        Here, you can see in Way 2 we have used 3 coins to reach the target sum of 7.
        Hence the output is 3.
        
        https://www.naukri.com/code360/problems/minimum-elements_3843091?leftPanelTab=0&leftPanelTabValue=PROBLEM
     */
    public static int minimumElements(int num[], int x) {

        int n = num.length;

        int dp[][] = new int[n + 1][x + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return solve(num, x, n, dp);
    }
    
    static int solve(int num[], int x, int n, int dp[][]) {

        if (x == 0) {
            return 0;
        }

        if (n <= 0) {
            return 100000000;
        }

        if (dp[n][x] != -1) {
            return dp[n][x];
        }

        int notIncluded = solve(num, x, n - 1, dp);

        int included = 100000000;

        if (num[n - 1] <= x) {
            included = 1 + solve(num, x - num[n - 1], n, dp);
        }

        return dp[n][x] = Integer.min(included, notIncluded);

    }
    
    public static void main(String[] args) {
        int arr[] = { 1, 2, 3 };

        System.out.println(MinCountSum.minimumElements(arr, 7));
    }
}
