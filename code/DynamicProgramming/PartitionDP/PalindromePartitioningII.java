package DynamicProgramming.PartitionDP;

import java.util.Arrays;

public class PalindromePartitioningII {

    //
    /*
    Given a string s, partition s such that every substring of the partition is a palindrome.
    
    Return the minimum cuts needed for a palindrome partitioning of s.
    
    Input s = "abcbaedfde"
    Output = 1 (abcba | edfde)
    
    Approach : 
    starting for each index from i (0 -> n), check starting from each index,
    how many palindrome substrings can be formed, if any can be formed,
    we recursively call the right of the substring, and add 1. 
    at end return min of all the possibilities.
    
    Q. Why to do ans - 1 at the end of iteration.
    A. The logic counts an extraa partition at end of string
        e.g abc = a | b | c | . To get rid of this extra partition, we
        do ans - 1.
    */

    public int minCut(String s) {

        int n = s.length();

        // dp[i] stores the minimum number of palindromic pieces needed 
        // to partition the suffix string s[i ... n-1].
        int dp[] = new int[n];
        Arrays.fill(dp, -1);

        // If the entire string can be split into M palindromic pieces, 
        // it requires exactly (M - 1) cuts.
        // Example: "a|b" -> 2 palindromic parts, (2 - 1) = 1 cut.
        //          "aba" -> 1 palindromic part,  (1 - 1) = 0 cuts.
        return helper(s, 0, n, dp) - 1;
    }

    private int helper(String str, int i, int n, int dp[]) {
        // BASE CASE: Reached the end of the string.
        // If index i reaches or exceeds n, no characters remain to partition,
        // which requires 0 additional palindromic substrings.
        if (i >= n) {
            return 0;
        }

        // MEMOIZATION CHECK:
        // Return previously computed minimum partitions for suffix starting at index i.
        if (dp[i] != -1) {
            return dp[i];
        }

        int minCuts = Integer.MAX_VALUE;

        // PREFIX PARTITION LOOP:
        // We try placing the NEXT cut after index k, where k ranges from i to n - 1.
        // The candidate prefix is str[i ... k].
        for (int k = i; k < n; k++) {

            // CONDITION:
            // We only make a partition if the prefix str[i ... k] is a valid palindrome.
            // If it is NOT a palindrome, partitioning here cannot form a valid solution.
            if (isPalindrome(i, k, str)) {

                // 1 piece formed by palindrome str[i ... k]
                // + optimal palindromic pieces needed for the remaining suffix str[k + 1 ... n - 1].
                int curr = 1 + helper(str, k + 1, n, dp);

                // Minimize total palindromic pieces needed for suffix starting at index i
                if (minCuts > curr) {
                    minCuts = curr;
                }
            }
        }

        return dp[i] = minCuts;
    }

    // TWO-POINTER PALINDROME VALIDATOR:
    // Checks if substring str[i ... j] reads the same forwards and backwards in O(j - i).
    private boolean isPalindrome(int i, int j, String str) {

        int l = i, r = j;

        while (l < r) {
            if (str.charAt(l) != str.charAt(r)) {
                return false;
            }

            l++;
            r--;
        }

        return true;
    }

    public static void main(String[] args) {
        PalindromePartitioningII obj = new PalindromePartitioningII();

        System.out.println(obj.minCut("abcbaedfde"));
    }
    
}
