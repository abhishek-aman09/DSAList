package String;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LongestDistSubstring {
    /*
     * https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
     * Time complexity = O(n * 256)
     * keep a track of last position of each char.
     * if repeat found, update the current pos of char and all previous char to -1
     * as they are irrelevent.
     */
    
    public int lengthOfLongestSubstring(String s) {

        int n = s.length();

        int ans = 0;

        int lastPos[] = new int[256]; // for all know ASCII chars.

        // fill the initial position of each el to -1
        Arrays.fill(lastPos, -1);

        int countUnique = 0;

        for (int i = 0; i < n; i++) {

            char ch = s.charAt(i);

            if (lastPos[ch] == -1) {
                // if current char is unique, add it
                countUnique++;
            } else {
                // if it is not unique, update the prev chars to -1 as they can no longer
                // contribute to unique chars
                for (int j = 0; j < 256; j++) {
                    if (lastPos[j] < lastPos[ch] && j != (ch)) {
                        lastPos[j] = -1;
                    }
                }
                // update the count uniques to currentPos of char - last pos of char
                countUnique = i - lastPos[ch];
            }

            // update the last pos of char to current pos
            lastPos[ch] = i;
            ans = Integer.max(ans, countUnique);
        }

        return ans;

    }
    
    public static void main(String[] args) {
        LongestDistSubstring obj = new LongestDistSubstring();

        String s = " A% f&";

        System.out.println(obj.lengthOfLongestSubstring(s));
    }
    
}
