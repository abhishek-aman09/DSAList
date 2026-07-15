package String;

public class LongestCommonPrefix {

    public String longestCommonPrefix(String[] strs) {

        int n = strs.length;

        StringBuilder lcp = new StringBuilder(strs[0]);

        for (int i = 1; i < n; i++) {
            String word = strs[i];

            int j = 0;
            int k = 0;
            for (; j < word.length() && k < lcp.length(); j++, k++) {
                char ch = word.charAt(j);
                char lch = lcp.charAt(k);

                if (ch != lch) {
                    lcp.setLength(k + 1);
                    break;
                }
            }

            if (k < lcp.length()) {
                lcp.setLength(j);
            }
        }

        return lcp.toString();

    }
}
