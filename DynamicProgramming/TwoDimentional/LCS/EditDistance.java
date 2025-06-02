package DynamicProgramming.TwoDimentional.LCS;

import java.util.Arrays;

public class EditDistance {

    /*
     * You are given two strings 'S' and 'T' of lengths 'N' and 'M' respectively. Find the "Edit Distance" between the strings.
    
        Edit Distance of two strings is the minimum number of steps required to make one string equal to the other. In order to do so, you can perform the following three operations:
        
        1. Delete a character
        2. Replace a character with another one
        3. Insert a character
        Note:
        Strings don't contain spaces in between.
        
        Input: word1 = "intention", word2 = "execution"
        Output: 5
        Explanation: 
        intention -> inention (remove 't')
        inention -> enention (replace 'i' with 'e')
        enention -> exention (replace 'n' with 'x')
        exention -> exection (replace 'n' with 'c')
        exection -> execution (insert 'u')
        
        
        https://www.naukri.com/code360/problems/edit-distance_630420?leftPanelTabValue=PROBLEM
    
    */
    
    static int solve(String s1, String s2, int n, int m, int dp[][]) {

        // we need to convert "ros" to "horse"
        if (n == 0) {
            return m;
        }

        if (m == 0) {
            return n;
        }

        if (dp[n - 1][m - 1] != -1) {
            return dp[n - 1][m - 1];
        }

        if (s1.charAt(n - 1) == s2.charAt(m - 1)) {
            return dp[n - 1][m - 1] = solve(s1, s2, n - 1, m - 1, dp);
        }

        int insertion = solve(s1, s2, n, m - 1, dp); // suppose we insert 's' 
        // at end of "horse" and make it "horses" now, the last char of both are matching
        // so m decreases by 1 but n does not as length of horse has increased, because
        // 'e' may match with the char before 's' in "ros"

        int deletion = solve(s1, s2, n - 1, m, dp); // suppose we delete 'e' from "horse"
        // now we decrease n by one as it's length has been decreased. We don't decrease m
        // because now 's' in ros may match with the new character at n in "horse"

        int replacing = solve(s1, s2, n - 1, m - 1, dp); // we are replacing 'e' with 's' 
        // and moving both by one.
        
        return dp[n - 1][m - 1] = 1 + Integer.min(
                Integer.min(deletion, insertion), replacing);
        // take min of three and add cost of one operation

    }

    public static int editDistance(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();

        int dp[][] = new int[n][m];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }

        return solve(str1, str2, n, m, dp);
    }

    public static void main(String[] args) {
        System.out.println(EditDistance.editDistance("whgtdwhgtdg", "aswcfg"));
    }

}
