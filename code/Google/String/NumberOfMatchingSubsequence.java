package Google.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberOfMatchingSubsequence {

    /*
    https://leetcode.com/problems/number-of-matching-subsequences/description/
    
    Given a string s and an array of strings words, return the number of words[i] that is a subsequence of s.
    
    A subsequence of a string is a new string generated from the original string with some characters (can be none) 
    deleted without changing the relative order of the remaining characters.
    
    For example, "ace" is a subsequence of "abcde".
    
    Input: s = "abcde", words = ["a","bb","acd","ace"]
    Output: 3
    Explanation: There are three strings in words that are a subsequence of s: "a", "acd", "ace".
    
    Approach : create a map of char : list of pos of original string (go linearly, list will be already sorted). 
    Iterate through all the subsequence and maintain a minCurrPos variable for each. For each character, perfrom binary
    search on list of array to find the smallest position grater than minCurrPos, if it does not exit, return false
    */

    public int numMatchingSubseq(String s, String[] words) {

        int len = s.length();

        Map<Character, List<Integer>> charToPosMap = new HashMap<>();

        for (int i = 0; i < len; i++) {

            char ch = s.charAt(i);

            if (!charToPosMap.containsKey(ch)) {
                charToPosMap.put(ch, new ArrayList<>());
            }

            charToPosMap.get(ch).add(i); // map creation, char to list of position
        }

        int result = 0;

        for (String word : words) {

            if (isSubsequence(word, charToPosMap)) { // method to check if it is a subsequence
                result++;
            }
        }

        return result;

    }
    
    private boolean isSubsequence(String str, Map<Character, List<Integer>> map) {

        int minCurrInd = -1;

        for (char ch : str.toCharArray()) {
            if (!map.containsKey(ch)) { // if character does not exist in string, return false
                return false;
            }

            // get smallest pos greater than current min
            int smallestPosGreaterThanMin = getSmallestPosGreaterThanMin(minCurrInd, map.get(ch));

            if (smallestPosGreaterThanMin == -1) {
                return false;
            }

            minCurrInd = smallestPosGreaterThanMin; // keep updating minCurrPos for each character
        }

        return true;
    }

    // binary search block
    private int getSmallestPosGreaterThanMin(int minInd, List<Integer> listOfPos) {

        int pos = -1;

        int l = 0, r = listOfPos.size() - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;
            // if pos at mid is greater than minPos, store and check for left subarray
            if (listOfPos.get(mid) > minInd) {
                pos = listOfPos.get(mid);
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return pos; // return pos

    }
    
    public static void main(String[] args) {
        NumberOfMatchingSubsequence obj = new NumberOfMatchingSubsequence();

        System.out.println(
                obj.numMatchingSubseq("dsahjpjauf", new String[] { "ahjpjau", "ja", "ahbwzgqnuk", "ps" }));
    }
    
}
