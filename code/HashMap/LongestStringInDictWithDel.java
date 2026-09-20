package HashMap;

import java.util.ArrayList;
import java.util.List;

/*
 * ============================================================================
 * LEETCODE 524: Longest Word in Dictionary through Deleting
 * ============================================================================
 * Link: https://leetcode.com/problems/longest-word-in-dictionary-through-deleting/
 *
 * DESCRIPTION:
 * Given a string s and a string array dictionary, return the longest string in 
 * the dictionary that can be formed by deleting some of the given string characters. 
 * If there is more than one possible result, return the longest word with the 
 * smallest lexicographical order. If there is no possible result, return the 
 * empty string.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * Input: s = "abpcplea", dictionary = ["ale","apple","monkey","plea"]
 * Output: "apple"
 * Explanation: Both "apple" and "plea" can be formed by deleting characters of s.
 * "apple" is longer than "plea", so return "apple".
 *
 * ----------------------------------------------------------------------------
 * APPROACH: Inverted Index (Position List) + Binary Search Subsequence Matching
 *
 * 1. Problem Characterization:
 *    A word `w` can be formed by deleting characters from `s` if and only if `w`
 *    is a subsequence of `s`.
 *
 * 2. Inverted Index / Position Map:
 *    - Preprocess `s` by recording the 0-based indices for each character:
 *      `pos[ch]` stores all indices where character `ch` appears in `s` in strictly 
 *      ascending order.
 *    - Replacing `Map<Character, List<Integer>>` with a fixed-size `List<Integer>[26]` 
 *      removes auto-boxing and hash collisions.
 *
 * 3. Subsequence Check via Binary Search (Upper Bound):
 *    - Track the last matched position in `s`: `minIndex` (initially -1).
 *    - For each character `ch` in the target word:
 *        Find the smallest index in `pos[ch]` that is STRICTLY greater than `minIndex`.
 *    - Because indices in `pos[ch]` are inserted sequentially, they are sorted by default.
 *    - Binary search finds this next index in O(log(occurrences_of_ch)) time.
 *    - If no such index exists, the word is not a subsequence.
 *
 * 4. Tie-Breaking:
 *    - Maintain `ans` initialized to `""`.
 *    - If a valid subsequence `word` is found:
 *        a) If word.length() > ans.length() -> pick word.
 *        b) If word.length() == ans.length() && word.compareTo(ans) < 0 -> pick word.
 *
 * 5. Complexity Analysis:
 *    - Preprocessing: O(|s|) time to build the index list.
 *    - Checking a word: O(|word| * log |s|) time.
 *    - Total Time Complexity: O(|s| + Sum(|word_i| * log |s|))
 *      Given |s| <= 1000 and dictionary size <= 1000 with word lengths <= 1000, 
 *      this runs well within 15-20 ms.
 *    - Space Complexity: O(|s|) space to store character positions.
 * ============================================================================
 */
public class LongestStringInDictWithDel {

    public String findLongestWord(String s, List<String> dictionary) {
        int n = s.length();

        // Step 1: Preprocess character positions into an array of lists (a-z)
        List<Integer>[] charToPosList = new ArrayList[26];
        for (int i = 0; i < 26; i++) {
            charToPosList[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            charToPosList[s.charAt(i) - 'a'].add(i);
        }

        String ans = "";

        // Step 2: Validate each dictionary word
        for (String word : dictionary) {
            // Pruning: skip words that are shorter than current best ans
            if (word.length() < ans.length()) {
                continue;
            }

            if (checkIfPresent(word, charToPosList)) {
                // Update best match using length and lexicographical order
                if (word.length() > ans.length() || word.compareTo(ans) < 0) {
                    ans = word;
                }
            }
        }

        return ans;
    }

    /**
     * Checks whether `word` is a subsequence of `s` using binary search over character positions.
     */
    private boolean checkIfPresent(String word, List<Integer>[] charToPosList) {
        int minIndex = -1;

        for (int i = 0; i < word.length(); i++) {
            int charIdx = word.charAt(i) - 'a';
            List<Integer> positions = charToPosList[charIdx];

            // If the character does not exist in s, word cannot be formed
            if (positions.isEmpty()) {
                return false;
            }

            int smallestPosGreaterThanMin = getSmallestPosGreaterThanMin(minIndex, positions);

            // No valid forward index found
            if (smallestPosGreaterThanMin == -1) {
                return false;
            }

            minIndex = smallestPosGreaterThanMin;
        }

        return true;
    }

    /**
     * Binary search to find the smallest index in `list` that is strictly greater than `minPos`.
     */
    private int getSmallestPosGreaterThanMin(int minPos, List<Integer> list) {
        int ans = -1;
        int l = 0, r = list.size() - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (list.get(mid) > minPos) {
                ans = list.get(mid);
                r = mid - 1; // Search left for an earlier occurrence
            } else {
                l = mid + 1;
            }
        }

        return ans;
    }
}