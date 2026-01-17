package DynamicProgramming.TwoDimentional.LCS;

import java.util.Arrays;

public class DistinctSubsequence {

    private static int MOD = 1000000007;

    /*
     * We are given two strings, 'str' and 'sub'.
    Find the number of subsequences of 'str' which are equal to 'sub'.
    Since the answer can be very large, print it modulo 10 ^ 9 + 7.
    
    Example :
    Input: 'str' = “brootgroot” and 'sub' = “brt”
    
    Output: 3 
    Explanation: The following subsequences formed by characters at given indices (0-based) of 'str' are equal to 'sub' :
    
    str[0] = ‘b’, str[1] = ‘r’, str[4] = ‘t’  
    str[0] = ‘b’, str[1] = ‘r’, str[9] = ‘t’
    str[0] = ‘b’, str[6] = ‘r’, str[9] = ‘t’

    https://www.naukri.com/code360/problems/subsequence-counting_3755256?source=youtube&campaign=striver_dp_videos&utm_source=youtube&utm_medium=affiliate&utm_campaign=striver_dp_videos&leftPanelTabValue=PROBLEM
    
     */
    
    public static int distinctSubsequences(String str, String sub) {
        int n = str.length();
        int m = sub.length();

        int dp[][] = new int[n][m];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return solve(0, 0, n, m, str, sub, dp) % MOD;
    }

    static int solve(int i, int j, int n, int m, String str, String sub, int dp[][]) {
        if (i == n || j == m) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j] % MOD;
        }

        if (str.charAt(i) == sub.charAt(j)) {
            if (j == m - 1) {
                return dp[i][j] = 1 + solve(i + 1, j, n, m, str, sub, dp) % MOD;
            } else {
                return dp[i][j] = (solve(i + 1, j + 1, n, m, str, sub, dp) % MOD + solve(i + 1, j, n, m, str, sub, dp) % MOD) % MOD;
            }
        } else {
            return dp[i][j] = solve(i + 1, j, n, m, str, sub, dp) % MOD;
        }

    }
    
    public static void main(String[] args) {
        System.out.println(DistinctSubsequence.distinctSubsequences("dingdingdingding", "ing"));
    }

}
