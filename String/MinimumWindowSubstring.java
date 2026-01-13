package String;

import java.util.HashMap;
import java.util.Map;

public class MinimumWindowSubstring {
    // https://leetcode.com/problems/minimum-window-substring/description/

    /*
        Given two strings s and t of lengths m and n respectively,
        return the minimum window substring of s such that every character in t
        (including duplicates) is included in the window. If there is no such substring,
        return the empty string "".
    
        Input: s = "ADOBECODEBANC", t = "ABC"
        Output: "BANC"
        Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
    */
   

    public String minWindow(String s, String t) {

        int n = s.length();
        int m = t.length();

        if (m > n) {
            return "";
        }

        int l = 0;
        int r = 0;
        int minLen = Integer.MAX_VALUE;
        int minLenLeft = 0;
        Map<Character, Integer> freqOfCharsInWindow = new HashMap<>();
        Map<Character, Integer> freqOfCharsInT = new HashMap<>();
        int totalCountOfUniqueTCharsInWindowWithSameFreq = 0;

        for (int i = 0; i < m; i++) {
            char ch = t.charAt(i);
            freqOfCharsInT.put(ch, freqOfCharsInT.getOrDefault(ch, 0) + 1);
        }

        while (r < n) {
            char ch = s.charAt(r);

            if (freqOfCharsInT.containsKey(ch)) {
                freqOfCharsInWindow.put(ch, freqOfCharsInWindow.getOrDefault(ch, 0) + 1);
                if (freqOfCharsInT.get(ch).equals(freqOfCharsInWindow.get(ch))) {
                    totalCountOfUniqueTCharsInWindowWithSameFreq++;
                }
            }

            while (totalCountOfUniqueTCharsInWindowWithSameFreq == freqOfCharsInT.size()) {

                if (r - l + 1 < minLen) {
                    minLen = r - l + 1;
                    minLenLeft = l;
                }
                char leftChar = s.charAt(l);
                if (freqOfCharsInWindow.containsKey(leftChar)) {

                    if (freqOfCharsInT.get(leftChar).equals(freqOfCharsInWindow.get(leftChar))) {
                        totalCountOfUniqueTCharsInWindowWithSameFreq--;
                    }
                    
                    freqOfCharsInWindow.put(leftChar, freqOfCharsInWindow.get(leftChar) - 1);
                }

                l++;
            }
            r++;
        }

        if (minLen == Integer.MAX_VALUE) {
            return "";
        }

        return s.substring(minLenLeft, minLenLeft + minLen);

    }
    
    public static void main(String[] args) {
        MinimumWindowSubstring obj = new MinimumWindowSubstring();

        System.out.println(obj.minWindow("bbaa", "aba"));
    }
}
