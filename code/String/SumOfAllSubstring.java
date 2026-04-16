package String;

public class SumOfAllSubstring {

    /*
    https://leetcode.com/problems/sum-of-beauty-of-all-substrings/description/
    The beauty of a string is the difference in frequencies between the most frequent and least frequent characters.
    
    For example, the beauty of "abaacc" is 3 - 1 = 2.
    Given a string s, return the sum of beauty of all of its substrings.
    
    Approach : For all the subarrays, count freq and beauty
    */
    
    public int beautySum(String s) {

        
        int n = s.length();

        int ans= 0;

        for(int i = 0; i < n; i++) {
            for(int j = i ; j < n; j++) {
                ans += countFreqAndGetBeauty(i, j, s);
            }
        }

        return ans;
        
    }

    private int countFreqAndGetBeauty(int i, int j, String s) {
        int freq[] = new int[26];

        while(i <= j) {
            freq[(int)s.charAt(i) - 97]++;
            i++;
        }

        return getBeauty(freq);
    }

    private int getBeauty(int freq[]) {

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                min = Math.min(min, freq[i]);
                max = Math.max(max, freq[i]);
            }

        }
        
        if (max == Integer.MAX_VALUE || min == Integer.MIN_VALUE) {
            return 0;
        }

        return max - min;
    }
    
    public static void main(String[] args) {
        SumOfAllSubstring obj = new SumOfAllSubstring();

        String s = "aabcbaa";

        System.out.println(obj.beautySum(s));
    }
}
