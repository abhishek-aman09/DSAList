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

        int dp[] = new int[n];
        Arrays.fill(dp, -1);

        return helper(s, 0, n, dp) - 1;
    }
    
    private int helper(String str, int i, int n, int dp[]) {
        if (i >= n) {
            return 0;
        }

        if (dp[i] != -1) {
            return dp[i];
        }

        int minCuts = Integer.MAX_VALUE;
        for (int k = i; k < n; k++) {

            if (isPalindrome(i, k, str)) {
                int curr = 1 + helper(str, k + 1, n, dp);
                if (minCuts > curr) {
                    minCuts = curr;
                }
            }
        }

        return dp[i] = minCuts;
    }

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
