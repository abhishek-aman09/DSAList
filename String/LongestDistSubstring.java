package String;

import java.util.HashMap;
import java.util.Map;

public class LongestDistSubstring {
    
    public int lengthOfLongestSubstring(String s) {

        int n = s.length();

        Map<Character, Integer> freq = new HashMap<>();

        int i = 0;
        int ans = 0;

        int lastPos[] = new int[26];

        while (i < n) {

            char ch = s.charAt(i);

            if (!freq.containsKey(ch)) {
                freq.put(s.charAt(i), 0);
            }

            if (freq.get(ch) == 0) {
                ans = Integer.max(ans, freq.size());
                freq.put(ch, 1);
                lastPos[ch - 'a'] = i;
                i++;
                continue;
            }

            int newLenOfDistinct = i - lastPos[ch - 'a'];

            ans = Integer.max(ans, newLenOfDistinct);

            for (int j = 0; j <= lastPos[ch - 'a']; j++) {
                if (freq.get(s.charAt(j)) == 1) {
                    freq.remove(s.charAt(i));
                } else {
                    freq.put(s.charAt(j), freq.get(s.charAt(j)) - 1);
                }
            }

            lastPos[ch - 'a'] = i;
            freq.put(ch, 1);

            i++;

        }

        return ans;

    }
    
    public static void main(String[] args) {
        LongestDistSubstring obj = new LongestDistSubstring();

        String s = "abcabcbb";

        System.out.println(obj.lengthOfLongestSubstring(s));
    }
    
}
