package DynamicProgramming.TwoDimentional.LCS;

import java.util.Arrays;

public class LCSCount {

    /*
     * Input: s1 = "ABCDGH", s2 = "AEDFHR"
    Output: 3
    Explanation: The longest common subsequence of "ABCDGH" and "AEDFHR" is "ADH", which has a length of 3.
    
    https://www.geeksforgeeks.org/problems/longest-common-subsequence-1587115620/1
     */
    static int lcs(String s1, String s2) {

        int n = s1.length();
        int m = s2.length();

        int dp[][] = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], 0);
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Integer.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[n][m];

    }

    /*
     * Find the minimum number of characters needed to insert in 'str' to make it a palindromic string.
     * Insertion can happen anywhere
     * 
     * https://www.naukri.com/code360/problems/minimum-insertions-to-make-palindrome_985293?leftPanelTabValue=PROBLEM
     */

    public static int minInsertion(String str) {
        StringBuilder temp = new StringBuilder();
        temp.append(str);

        temp.reverse();

        String str1 = temp.toString();

        int lengthOfLCS = lcs(str, str1);

        return str.length() - lengthOfLCS;
    }

    /*
     * https://www.naukri.com/code360/problems/longest-palindromic-subsequence_842787?leftPanelTabValue=PROBLEM
     * For longest palindromic subsequence, s2 will be reverse of s1.
     */
    
    public static int longestPalindromeSubsequence(String s) {
        StringBuilder s2 = new StringBuilder();
        s2.append(s);

        s2.reverse();

        String s1 = s2.toString();

        return lcs(s, s1);
    }

    /*
     * You are given 2 non-empty strings 's1' and 's2' consisting of lowercase English alphabets only.
    
    In one operation you can do either of the following on 's1':
    
    (1) Remove a character from any position in 's1'.
    
    (2) Add a character to any position in 's1'.
    
    Example:
    Input: 's1' = "abcd", 's2' = "anc"
    
    Output: 3
    
    Explanation:
    Here, 's1' = "abcd", 's2' = "anc".
    In one operation remove 's1[3]', after this operation 's1' becomes "abc".    
    In the second operation remove 's1[1]', after this operation 's1' becomes "ac".
    In the third operation add 'n' in 's1[1]', after this operation 's1' becomes "anc"
    
    https://www.naukri.com/code360/problems/minimum-number-of-deletions-and-insertions_4244510?leftPanelTabValue=PROBLEM
    
     */
    
    public static int canYouMake(String s1, String s2) {
        
        int lcs = lcs(s1, s2);

        return s1.length() + s2.length() - (2 * lcs);
    }
}
