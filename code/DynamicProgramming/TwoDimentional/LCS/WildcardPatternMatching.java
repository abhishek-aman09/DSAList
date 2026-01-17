package DynamicProgramming.TwoDimentional.LCS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class WildcardPatternMatching {
    /*
     * Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:
    
        '?' Matches any single character.
        '*' Matches any sequence of characters (including the empty sequence).
        The matching should cover the entire input string (not partial).
        
        
        
        Example 1:
        
        Input: s = "aa", p = "a"
        Output: false
        Explanation: "a" does not match the entire string "aa".

        https://leetcode.com/problems/wildcard-matching/description/
    
     */
    
    public static boolean wildcardMatching(String pattern, String text) {

        int m = text.length();
        int n = pattern.length();

        Boolean dp[][] = new Boolean[n][m];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], null);
        }

        return solve(n, m, pattern, text, dp);
    }

    static boolean solve(int n, int m, String pattern, String text, Boolean dp[][]) {
        // if both string reaches at 0 simultaneously, then we had atlest
        // one true case through out the tree.
        if (n == 0 && m == 0) {
            return true;
        }

        // special condition for case like pat = "*****?", text = "a" 
        // i.e all leading '*' signifies null character
        if(m == 0 && pattern.charAt(n - 1) == '*') {
            return solve(n - 1, m, pattern, text, dp);
        }

        // if first char of pattern is '*' and later all cases have matched
        // we return true as it can include all remaining chars of text
        if (n == 1 && pattern.charAt(n - 1) == '*') {
            return true;
        }

        if (n == 0) {
            return false;
        }

        if (m == 0) {
            return false;
        }

        if (Objects.nonNull(dp[n - 1][m - 1])) {
            return dp[n - 1][m - 1];
        }

        if (pattern.charAt(n - 1) == text.charAt(m - 1) || pattern.charAt(n - 1) == '?') {
            return dp[n - 1][m - 1] = solve(n - 1, m - 1, pattern, text, dp);
        }

        if (pattern.charAt(n - 1) == '*') {
            return dp[n - 1][m - 1] = solve(n - 1, m, pattern, text, dp) || solve(n, m - 1, pattern, text, dp);
        }

        return false;
    }
    
    public static void main(String[] args) {
        System.out.println(wildcardMatching("**", "abccfcd"));
    }

}
