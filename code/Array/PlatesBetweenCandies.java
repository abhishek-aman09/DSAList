package Array;

import java.util.ArrayList;

public class PlatesBetweenCandies {

    /*
    https://leetcode.com/problems/plates-between-candles/description/
    
    There is a long table with a line of plates and candles arranged on top of it. You are given a 0-indexed string s consisting of characters '*' and '|' only, where a '*' represents a plate and a '|' represents a candle.
    
    You are also given a 0-indexed 2D integer array queries where queries[i] = [lefti, righti] denotes the substring s[lefti...righti] (inclusive). For each query, you need to find the number of plates between candles that are in the substring. A plate is considered between candles if there is at least one candle to its left and at least one candle to its right in the substring.
    
    For example, s = "||**||**|*", and a query [3, 8] denotes the substring "*||**|". The number of plates between candles in this substring is 2, as each of the two plates has at least one candle in the substring to its left and right.
    Return an integer array answer where answer[i] is the answer to the ith query.
    
    
    Approach : 
    maintain two array one to store nearest plate to left and other to store
    nearest plate to the right. also make third array to store the prefix sum of the plates
    
    for each query, find neares plate to left and right and subtract the numbers of plates in between
    */
   
    public int[] platesBetweenCandles(String s, int[][] queries) {

        int n = s.length();

        int leftCandies[] = new int[n];
        int rightCandies[] = new int[n];
        int prefixCandies[] = new int[n];

        int right = -1;
        int candies = 0;

        // store nearest plate to right
        for(int i = n - 1; i >= 0; i--) {
            if(s.charAt(i) == '|') {
                right = i;
            }

            rightCandies[i] = right;
        }

        int left = -1;
        // store nearest plate to left and preix sum of candies
        for(int i = 0; i < n; i++) {
            if(s.charAt(i) == '|') {
                candies++;
                left = i;
            }

            leftCandies[i] = left;
            prefixCandies[i] = candies;
        }

        n = queries.length;

        int ans[] = new int[n];

        for(int i = 0; i < n; i++) {
            left = queries[i][0];
            right = queries[i][1];

            int lC = rightCandies[left];
            int rC = leftCandies[right];

            // if there are at least two plates bw the given indices
            if(rC > lC && lC != -1 && rC != -1) {
                int totalItems = rC - lC;
                int candiesInBetween = prefixCandies[rC] - prefixCandies[lC];
                ans[i] = (totalItems - candiesInBetween);
            } else {
                ans[i] = 0;
            }
            
        }

        return ans;
    }
    
}
