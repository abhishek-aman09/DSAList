package Array;

import java.util.*;

/*
 * ============================================================================
 * LEETCODE 3201: Find the Maximum Length of Valid Subsequence I
 * ============================================================================
 * Link: https://leetcode.com/problems/find-the-maximum-length-of-valid-subsequence-i/
 *
 * DESCRIPTION:
 * You are given an integer array nums.
 * A subsequence sub of nums with length x is called valid if it satisfies:
 * (sub[0] + sub[1]) % 2 == (sub[1] + sub[2]) % 2 == ... == (sub[x - 2] + sub[x - 1]) % 2.
 *
 * Return the maximum length of a valid subsequence of nums.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * Input: nums = [1, 2, 3, 4]
 * Output: 4
 * Explanation: The valid subsequence is [1, 2, 3, 4] where:
 * (1 + 2) % 2 == 1, (2 + 3) % 2 == 1, (3 + 4) % 2 == 1.
 *
 * ----------------------------------------------------------------------------
 * APPROACH: Parity Pattern Classification / DP
 *
 * 1. Mathematical Insight:
 *    Let the consecutive pair sum parity be c in {0, 1}:
 *        (sub[i] + sub[i + 1]) % 2 == c for all i
 *
 *    Since x % 2 has only two values (0 or 1), any valid subsequence must follow
 *    one of exactly four parity patterns:
 *      1. All Even (sum parity = 0):
 *         even + even = even -> pattern: [0, 0, 0, 0, ...]
 *      2. All Odd (sum parity = 0):
 *         odd + odd = even   -> pattern: [1, 1, 1, 1, ...]
 *      3. Alternating starting with Even (sum parity = 1):
 *         even + odd = odd   -> pattern: [0, 1, 0, 1, ...]
 *      4. Alternating starting with Odd (sum parity = 1):
 *         odd + even = odd   -> pattern: [1, 0, 1, 0, ...]
 *
 * 2. Consolidation:
 *    - All elements with parity 0: simply the count of even numbers.
 *    - All elements with parity 1: simply the count of odd numbers.
 *    - Alternating patterns:
 *      Notice that the maximum alternating subsequence does not depend on
 *      whether you start at the first even or first odd. If an alternating
 *      subsequence of length L exists, whichever element comes first just
 *      toggles the expected next parity (0 -> 1 -> 0 -> 1...).
 *      Thus, the best alternating subsequence is simply found by flipping
 *      the expected parity on every mismatch!
 *
 * 3. Generalized DP Formulation (leads directly into LC 3202):
 *    - Maintain `dp[last_parity][remainder]`: length of longest valid subsequence
 *      ending with `last_parity` where (prev + curr) % 2 == remainder.
 *    - Or simply count the 4 patterns directly in a single pass.
 *
 * 4. Complexity:
 *    - Time Complexity:  O(N) - single pass over the array.
 *    - Space Complexity: O(1) - primitive state tracking with no allocations.
 * ============================================================================
 */
public class MaxLenOfValidSubSeq {

    public int maximumLength(int[] nums) {
        int countEven = 0;
        int countOdd = 0;

        // Tracks length of alternating patterns
        // altEven: expects 0 first -> [0, 1, 0, 1, ...]
        // altOdd:  expects 1 first -> [1, 0, 1, 0, ...]
        int altEven = 0;
        int altOdd = 0;

        int expectForEvenStart = 0;
        int expectForOddStart = 1;

        for (int x : nums) {
            int rem = (x % 2 + 2) % 2; // Safe modulo for non-negative parities (0 or 1)

            // Pattern 1 & 2: Homogeneous parities (sum % 2 == 0)
            if (rem == 0) {
                countEven++;
            } else {
                countOdd++;
            }

            // Pattern 3: Alternating starting with Even [0, 1, 0, 1, ...]
            if (rem == expectForEvenStart) {
                altEven++;
                expectForEvenStart ^= 1; // Toggle between 0 and 1
            }

            // Pattern 4: Alternating starting with Odd [1, 0, 1, 0, ...]
            if (rem == expectForOddStart) {
                altOdd++;
                expectForOddStart ^= 1; // Toggle between 1 and 0
            }
        }

        // Return the maximum across all 4 valid patterns
        return Math.max(Math.max(countEven, countOdd), Math.max(altEven, altOdd));
    }
}
