package DynamicProgramming.TwoDimentional.LIS;

import java.util.Arrays;

/*
 * ============================================================================
 * LEETCODE 354: Russian Doll Envelopes
 * ============================================================================
 * Link: https://leetcode.com/problems/russian-doll-envelopes/
 *
 * DESCRIPTION:
 * You are given a 2D array of integers envelopes where envelopes[i] = [wi, hi] 
 * represents the width and the height of an envelope.
 *
 * One envelope can fit into another if and only if both the width and height of 
 * one envelope are strictly greater than the other envelope's width and height.
 *
 * Return the maximum number of envelopes you can Russian doll (i.e., put one 
 * inside the other).
 *
 * Note: You cannot rotate an envelope.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * Input: envelopes = [[5,4],[6,4],[6,7],[2,3]]
 * Output: 3
 * Explanation: The maximum number of envelopes you can Russian doll is 3:
 * ([2,3] => [5,4] => [6,7]).
 *
 * ----------------------------------------------------------------------------
 * APPROACH: 2D Sorting Trick + 1D Longest Increasing Subsequence (Patience Sort)
 *
 * 1. The Core 2D Challenge:
 *    - An envelope (w1, h1) fits inside (w2, h2) iff:
 *        w1 < w2 AND h1 < h2  (strictly greater in both dimensions).
 *
 * 2. The Sorting Trick:
 *    - Sort width ASCENDING:
 *        a[0] vs b[0] -> Ascending order guarantees w1 <= w2 <= w3 ...
 *    - For identical widths, sort height DESCENDING:
 *        When a[0] == b[0] -> Descending order (b[1] vs a[1]).
 *    - Why descending on height?
 *        Because width must be STRICTLY greater. If we have [3, 3] and [3, 4], 
 *        [3, 3] cannot fit into [3, 4]. By sorting heights descending, we see 
 *        height 4 before height 3. In the 1D LIS on heights, 4 cannot be followed 
 *        by 3 to form an increasing subsequence. This prevents picking two envelopes 
 *        with the exact same width.
 *
 * 3. Reduction to 1D LIS:
 *    - Once sorted, the width constraint is automatically handled!
 *    - The problem reduces to finding the standard 1D Longest Increasing 
 *      Subsequence (LIS) on the HEIGHTS array alone.
 *
 * 4. Patience Sorting / Binary Search (O(N log N)):
 *    - Maintain `tails[]` where `tails[i]` stores the smallest tail of an 
 *      increasing subsequence of length `i + 1`.
 *    - For each envelope height `h`:
 *        - Binary search for the first index `idx` such that `tails[idx] >= h` 
 *          (lower_bound).
 *        - Replace `tails[idx] = h`.
 *        - If `idx == length`, append `h` and increment `length`.
 *    - Final answer is `length`.
 *
 * 5. Complexity:
 *    - Time Complexity:  O(N log N)
 *                        - Sorting: O(N log N).
 *                        - Processing N envelopes with binary search: O(N log N).
 *                        Runs well within limits for N = 10^5 (takes ~30-40 ms).
 *    - Space Complexity: O(N) auxiliary space for the `tails` array.
 * ============================================================================
 */
public class RussianDollEnvelope {

    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0) {
            return 0;
        }

        int n = envelopes.length;

        // Step 1: Sort width ASC, and height DESC when widths tie
        Arrays.sort(envelopes, (a, b) -> {
            if (a[0] != b[0]) {
                return Integer.compare(a[0], b[0]);
            }
            return Integer.compare(b[1], a[1]);
        });

        // Step 2: 1D LIS on heights using patience sorting array
        // tails[i] stores the smallest ending height among all increasing subsequences of length (i + 1)
        int[] tails = new int[n];
        int len = 0;

        for (int[] env : envelopes) {
            int height = env[1];

            // Binary search to find the insertion point (lower_bound) of `height` in tails[0 ... len - 1]
            int l = 0, r = len - 1;
            int insertIndex = len;

            while (l <= r) {
                int mid = l + (r - l) / 2;

                if (tails[mid] >= height) {
                    insertIndex = mid;
                    r = mid - 1; // Try to find an even earlier index
                } else {
                    l = mid + 1;
                }
            }

            // Replace existing tail or extend subsequence
            tails[insertIndex] = height;
            if (insertIndex == len) {
                len++;
            }
        }

        return len;
    }
}
