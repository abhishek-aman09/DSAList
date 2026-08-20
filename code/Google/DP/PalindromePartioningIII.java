package Google.DP;

import java.util.Arrays;

public class PalindromePartioningIII {

    /*
    https://leetcode.com/problems/palindrome-partitioning-iii/description/
    
    ou are given a string s containing lowercase letters and an integer k. You need to :
    
    First, change some characters of s to other lowercase English letters.
    Then divide s into k non-empty disjoint substrings such that each substring is a palindrome.
    Return the minimal number of characters that you need to change to divide the string.
    
    Input: s = "abc", k = 2
    Output: 1
    Explanation: You can split the string into "ab" and "c", and change 1 character in "ab" to make it palindrome.
    
    Approch : More of a straight implementation of MCM, you run outer loop from 0 to n - 1, for each i, form a string
    from starting index to i, check how many characters need to change to make it palindrome, and call the right with one partition less.

    return the min number of partitions required.
    */

    private static final int MAX_VAL = Integer.MAX_VALUE / 100;
    
    public int palindromePartition(String s, int k) {

        int len = s.length();

        int[][] dp = new int[len][k + 1];

        for (int i = 0; i < len; i++) {
            Arrays.fill(dp[i], -1);
        }

        return helper(0, len, k, s, dp);

    }

    private int helper(int curr, int n, int remK, String str, int[][] dp) {

        if (curr >= n) { // if we have reached to the end, check if we have used up all the partitions
            return remK == 0 ? 0 : MAX_VAL;
        }

        if (remK < 0) { // if no partitions are left, return a max num
            return MAX_VAL;
        }
        
        if (dp[curr][remK] != -1) {
            return dp[curr][remK];
        }

        int minSwaps = MAX_VAL; // number to store minimun number of swaps needed

        for (int i = curr + 1; i <= n; i++) {

            String left = str.substring(curr, i); // form a substring from start index to curr

            int countSwaps = checkForPalindrome(left) + helper(i, n, remK - 1, str, dp); // check for swaps 
            // needed for curr string and call recursively with pone less partition

            minSwaps = Math.min(countSwaps, minSwaps); // keep checking minimum
        }

        return dp[curr][remK] = minSwaps; // return the min
    }
    
    private int checkForPalindrome(String str) {

        int l = 0;
        int r = str.length() - 1;
        int count = 0;

        while (l < r) {
            if (str.charAt(l) != str.charAt(r)) {
                count++;
            }
            l++;
            r--;
        }

        return count;
    }
    
    
    
}
