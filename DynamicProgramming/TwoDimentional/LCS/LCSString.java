package DynamicProgramming.TwoDimentional.LCS;

import java.util.Stack;
import java.util.Arrays;

public class LCSString {

    // Code to print LCS 
    static Stack<Pair> lcs(String s1, String s2) {

        int n = s1.length();
        int m = s2.length();

        int dp[][] = new int[n + 1][m + 1];

        Stack<Pair> posLCS = new Stack<>();

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

        int indS1 = n, indS2 = m;
        StringBuilder lcsString = new StringBuilder();

        while (indS1 > 0 && indS2 > 0) {
            if (s1.charAt(indS1 - 1) == s2.charAt(indS2 - 1)) {
                posLCS.add(new Pair(indS1 - 1, indS2 - 1));
                lcsString.append(s1.charAt(indS1 - 1));
                indS1--;
                indS2--;
            } else if (dp[indS1 - 1][indS2] > dp[indS1][indS2 - 1]) {
                indS1--;
            } else {
                indS2--;
            }
        }

        System.out.println("Longest Common Subsequence is " + lcsString.reverse());

        return posLCS;

    }

    /*
     * Given two strings, ‘A’ and ‘B’.
     *  Return the shortest supersequence string ‘S’, containing both ‘A’ and ‘B’ as its subsequences.
     *  If there are multiple answers, return any of them.
     * 
     * Sample Input 2 :
        2
        coding
        ninjas
        
        blinding
        lights
    
        Sample Output 2 :
        codningjas
        blindinghts
    
        https://www.naukri.com/code360/problems/shortest-supersequence_4244493?leftPanelTabValue=PROBLEM
        
    
     */

    public static String shortestSupersequence(String s1, String s2) {
        Stack<Pair> posList = lcs(s1, s2);
        int n = s1.length();
        int m = s2.length();

        StringBuilder superSequence = new StringBuilder();

        int itA = 0, itB = 0;

        while (!posList.empty()) {
            int aLim = posList.peek().first;
            int bLim = posList.peek().second;
            posList.pop();

            while (itA < aLim) {
                superSequence.append(s1.charAt(itA));
                itA++;
            }

            while (itB < bLim) {
                superSequence.append(s2.charAt(itB));
                itB++;
            }
            superSequence.append(s1.charAt(aLim));

            itA = aLim + 1;
            itB = bLim + 1;
        }

        while (itA < n) {
            superSequence.append(s1.charAt(itA));
            itA++;
        }
        
        while (itB < m) {
            superSequence.append(s2.charAt(itB));
            itB++;
        }
            
        return superSequence.toString();

    }

    public static class Pair {
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) {
        System.out.println(LCSString.shortestSupersequence("coding", "ninjas"));
    }

}
