package BinarySearch;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestDuplicateSubstring {

    public String longestDupSubstring(String s) {

        int l = 1, r = s.length() - 1;

        String ans = "";

        while (l <= r) {
            int mid = l + (r - l) / 2;

            String str = checkWithSize(mid, s);

            if (str.length() > ans.length()) {
                ans = str;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return ans;

    }
    

    // this single modulo method won't work for s length 10^5
    // suggest double modulo method to interviewer for large lengths ( double modulo is basically two hash comuted as OR)
    private String checkWithSize(int size, String s) {

        Set<Long> set = new HashSet<>();

        final long MOD = 1000000007;
        final long prime = 53;

        long hash = 0;

        long removeFactor = 1l;

        for (int i = 0; i < size; i++) {
            hash = (hash * prime + s.charAt(i)) % MOD;
            if (i < size - 1) {
                removeFactor = (removeFactor * prime) % MOD;
            }
        }

        set.add(hash);

        for (int i = size; i < s.length(); i++) {

            long outgoingVal = (s.charAt(i) * removeFactor) % MOD;

            hash = (hash - outgoingVal + MOD) % MOD;

            hash = (hash * prime + s.charAt(i)) % MOD;

            if (set.contains(hash)) {
                return s.substring(i - size + 1, i + 1);
            }

            set.add(hash);
        }

        return "";

    }
    

    // double hash method
    
    private String checkWithSizeDoubleHash(int size, String s) {
        if (size <= 0 || size > s.length()) return "";

        Set<Long> set = new HashSet<>();
        final long MOD1 = 1_000_000_007L;
        final long MOD2 = 1_000_000_009L;
        final long P1 = 257L;
        final long P2 = 313L;

        long h1 = 0, h2 = 0;
        long pow1 = 1, pow2 = 1;

        for (int i = 0; i < size; i++) {
            h1 = (h1 * P1 + s.charAt(i)) % MOD1;
            h2 = (h2 * P2 + s.charAt(i)) % MOD2;
            if (i < size - 1) {
                pow1 = (pow1 * P1) % MOD1;
                pow2 = (pow2 * P2) % MOD2;
            }
        }

        // Combine two 32-bit values into one 64-bit long
        long combined = (h1 << 32) | h2;
        set.add(combined);

        for (int i = size; i < s.length(); i++) {
            long out1 = (s.charAt(i - size) * pow1) % MOD1;
            long out2 = (s.charAt(i - size) * pow2) % MOD2;

            h1 = (h1 - out1 + MOD1) % MOD1;
            h2 = (h2 - out2 + MOD2) % MOD2;

            h1 = (h1 * P1 + s.charAt(i)) % MOD1;
            h2 = (h2 * P2 + s.charAt(i)) % MOD2;

            combined = (h1 << 32) | h2;
            if (set.contains(combined)) {
                return s.substring(i - size + 1, i + 1);
            }
            set.add(combined);
        }

        return "";
    }

    public static void main(String[] args) {
        LongestDuplicateSubstring obj = new LongestDuplicateSubstring();

        System.out.println(obj.longestDupSubstring("banana"));
    }
    
}
