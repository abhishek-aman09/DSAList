package DynamicProgramming.OneDimentional;

import java.util.Arrays;

public class DecodeWays {

    // https://leetcode.com/problems/decode-ways/submissions/1895697391/

    /*
    ou have intercepted a secret message encoded as a string of numbers. The message is decoded via the following mapping:
    
    "1" -> 'A'
    ...
    "26" -> 'Z'
    
    However, while decoding the message, you realize that there are many different ways you can decode the message because some codes are contained in other codes ("2" and "5" vs "25").
    
    For example, "11106" can be decoded into:
    
    "AAJF" with the grouping (1, 1, 10, 6)
    "KJF" with the grouping (11, 10, 6)
    The grouping (1, 11, 06) is invalid because "06" is not a valid code (only "6" is valid).
    Note: there may be strings that are impossible to decode.
    
    Given a string s containing only digits, return the number of ways to decode it. If the entire string cannot be decoded in any valid way, return 0.
    
    The test cases are generated so that the answer fits in a 32-bit integer.
    
    
    Approach : (Variation of fibonacci) If curr index has reached string length, return 1
    
    if 0 is encountered, we cannot treat it as independent num, return 0
    
    make recursive call to ind + 1, check ind + 2 is within range and
    less than 26, make recursive call to ind + 2.

    return the sum.
    
    */

    public int numDecodings(String s) {

        int n = s.length();

        int dp[] = new int[n];
        Arrays.fill(dp, -1);

        return helper(0, s.length(), s, dp);
    }
    
    private int helper(int i, int n, String str, int dp[]) {

        if (i == n) {
            return 1;
        }

        if (str.charAt(i) == '0') {
            return 0;
        }

        if (dp[i] != -1) {
            return dp[i];
        }

        int takeOne = helper(i + 1, n, str, dp);
        int takeTwo = 0;

        if (i + 1 < n) {
            int ascii = Integer.parseInt(str.substring(i, i + 2));

            if (ascii <= 26) {
                takeTwo = helper(i + 2, n, str, dp);
            }
        }

        return dp[i] = takeOne + takeTwo;
    }
    
}
