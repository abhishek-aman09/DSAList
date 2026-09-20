package BinarySearch;

import java.util.*;

/*
 * ============================================================================
 * LEETCODE 1218: Longest Arithmetic Subsequence of Given Difference
 * ============================================================================
 * Link: https://leetcode.com/problems/longest-arithmetic-subsequence-of-given-difference/
 *
 * DESCRIPTION:
 * Given an integer array arr and an integer difference, return the length of the 
 * longest subsequence in arr which is an arithmetic sequence such that the difference 
 * between adjacent elements in the subsequence equals difference.
 *
 * A subsequence is a sequence that can be derived from arr by deleting some or no 
 * elements without changing the order of the remaining elements.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * Input: arr = [1,5,7,8,5,3,4,2,1], difference = -2
 * Output: 4
 * Explanation: The longest arithmetic subsequence is [7, 5, 3, 1].
 *
 * ----------------------------------------------------------------------------
 * APPROACH COMPARISON:
 *
 * Approach 1: Memoized DFS + Binary Search on Index Positions (Your Initial Solution)
 * - Map each distinct value to an ordered list of its indices in arr.
 * - For each index i, determine the next required arithmetic value: next = arr[i] + diff.
 * - Perform binary search (upper bound) over map[next] to find the smallest index > i.
 * - Recursively explore down the chain and memoize the longest chain starting at index i.
 * - Complexity:
 *     - Time:  O(N * log N) average; worst case O(N^2) if repeated lookups occur.
 *     - Space: O(N) to store index positions and memoization entries.
 *
 * Approach 2: Single-Pass Linear Dynamic Programming with HashMap (Optimal O(N))
 * - Realization: Because the common difference is fixed, an element x can ONLY be 
 *   preceded by a single unique value: (x - difference).
 * - There is no need to look ahead into future indices.
 * - By sweeping left-to-right, dp.get(x - difference) gives the length of the longest
 *   chain ending with (x - difference) seen prior to the current element.
 * - Transition:
 *     dp.put(x, dp.getOrDefault(x - difference, 0) + 1)
 * - Any later occurrence of x automatically inherits or builds upon the best prefix,
 *   making greedy overwrites optimal.
 * - Complexity:
 *     - Time:  O(N) single pass through the array.
 *     - Space: O(N) auxiliary space for the hash map.
 * ============================================================================
 */
public class LongestArithSubSeqWithDiff {

    /*
     * ------------------------------------------------------------------------
     * APPROACH 1: Memoized DFS + Inverted Index + Binary Search
     * ------------------------------------------------------------------------
     */
    public int longestSubsequenceBinarySearch(int[] arr, int difference) {
        int n = arr.length;

        // Inverted index: maps each number to a list of its positions in arr (sorted ascending)
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(arr[i], key -> new ArrayList<>()).add(i);
        }

        // dp[i] stores the length of the longest arithmetic subsequence starting at index i
        Map<Integer, Integer> dp = new HashMap<>();
        int maxLen = 0;

        // Evaluate the maximum arithmetic chain length starting from each index i
        for (int i = 0; i < n; i++) {
            int currLen = calcMaxLen(i, arr, difference, map, dp);
            maxLen = Math.max(currLen, maxLen);
        }

        return maxLen;
    }

    /**
     * Helper method to compute max chain length starting at index `ind`.
     */
    private int calcMaxLen(int ind, int[] arr, int diff, Map<Integer, List<Integer>> map, Map<Integer, Integer> dp) {
        int curr = arr[ind];
        int next = curr + diff;

        // If the next expected value does not exist anywhere in arr, chain ends here
        if (!map.containsKey(next)) {
            return 1;
        }

        // Return memoized result if already computed for this start index
        if (dp.containsKey(ind)) {
            return dp.get(ind);
        }

        // Find the earliest occurrence of `next` that appears strictly after `ind`
        int nextIndex = getIndexGreaterThanCurr(ind, next, map);
        if (nextIndex == -1) {
            return 1; // No occurrence of `next` found after `ind`
        }

        // Recurse on the found index and memoize
        int result = 1 + calcMaxLen(nextIndex, arr, diff, map, dp);
        dp.put(ind, result);

        return result;
    }

    /**
     * Binary search to find the smallest index in map.get(next) that is strictly > i.
     */
    private int getIndexGreaterThanCurr(int i, int next, Map<Integer, List<Integer>> map) {
        int ind = -1;
        List<Integer> list = map.get(next);

        int l = 0, r = list.size() - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (list.get(mid) > i) {
                ind = list.get(mid); // Valid forward candidate found; try to find an earlier one
                r = mid - 1;
            } else {
                l = mid + 1;         // Index is <= i; search right
            }
        }

        return ind;
    }

    /*
     * ------------------------------------------------------------------------
     * APPROACH 2: Single-Pass Linear Dynamic Programming with HashMap (Optimal O(N))
     * ------------------------------------------------------------------------
     */
    public int longestSubsequence(int[] arr, int difference) {
        // dp.get(x) stores the maximum length of an arithmetic sequence ending with value x
        Map<Integer, Integer> dp = new HashMap<>();
        int maxLen = 0;

        for (int x : arr) {
            // Check if the predecessor value (x - difference) was seen earlier
            int prevCount = dp.getOrDefault(x - difference, 0);

            // Extend the sequence ending at (x - difference) by 1
            int currentChainLen = prevCount + 1;

            // Update/overwrite dp[x] with the new max chain length ending at x
            dp.put(x, currentChainLen);

            // Track global longest chain length
            maxLen = Math.max(maxLen, currentChainLen);
        }

        return maxLen;
    }
}
