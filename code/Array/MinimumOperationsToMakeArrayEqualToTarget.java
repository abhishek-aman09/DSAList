package Array;

/*
 * ============================================================================
 * LEETCODE 3229: Minimum Operations to Make Array Equal to Target
 * ============================================================================
 * Link: https://leetcode.com/problems/minimum-operations-to-make-array-equal-to-target/
 *
 * DESCRIPTION:
 * You are given two positive integer arrays nums and target of the same length.
 * In a single operation, you can choose any subarray of nums and either increment
 * each value by 1 or decrement each value by 1.
 *
 * Return the minimum number of operations required to make nums equal to target.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * Input: nums = [3,5,1,2], target = [4,6,2,4]
 * Output: 2
 * Explanation:
 * Target differences: diff = target - nums = [1, 1, 1, 2].
 * Subarray [0..3] increment by 1 -> diff becomes [0, 0, 0, 1].
 * Subarray [3..3] increment by 1 -> diff becomes [0, 0, 0, 0].
 * Total operations = 2.
 *
 * ----------------------------------------------------------------------------
 * APPROACH: Signed Skyline Difference (Direct Extension of LC 1526)
 *
 * 1. Reduction to Target Difference Array:
 *    Define diff[i] = target[i] - nums[i].
 *    The problem is equivalent to transforming an array of all zeros into `diff`
 *    using subarray increments (+1) and decrements (-1).
 *
 * 2. Sign Continuity & Transition Cases:
 *    Let `prev` be the required difference at index i - 1 and `curr` at index i:
 *
 *    Case A: Same Sign (Both Positive: curr > 0, prev > 0)
 *      - If curr > prev: The current bar is taller than the previous positive peak.
 *        We extend `prev` operations from the left, but need (curr - prev) new
 *        positive operations starting here.
 *      - If curr <= prev: All `curr` positive increments are already covered by
 *        operations that continue through index i - 1. Extra cost = 0.
 *
 *    Case B: Same Sign (Both Negative: curr < 0, prev < 0)
 *      - In terms of absolute values, if |curr| > |prev| (i.e. curr < prev),
 *        we need (|curr| - |prev|) new decrement operations.
 *      - If |curr| <= |prev|, it is fully covered by operations extending
 *        from the left. Extra cost = 0.
 *
 *    Case C: Sign Change (from Positive to Negative or Negative to Positive)
 *      - An increment operation cannot cross over into a decrement operation.
 *      - The previous chain cannot help the current sign at all.
 *      - We must start completely fresh with |curr| operations.
 *
 * 3. Compact Mathematical Unification:
 *    Notice that for all cases, if we track the signed differences:
 *      - If curr and prev have the same sign:
 *          if |curr| > |prev|, cost increases by |curr| - |prev|.
 *      - If curr and prev have different signs:
 *          cost increases by |curr|.
 *    By initializing `prev = 0`, the first element diff[0] naturally executes
 *    the sign-change branch and adds |diff[0]| operations.
 *
 * 4. Complexity:
 *    - Time Complexity:  O(N) — single pass over the array.
 *    - Space Complexity: O(1) — in-place difference tracking with no extra allocation.
 * ============================================================================
 */
public class MinimumOperationsToMakeArrayEqualToTarget {

    public long minimumOperations(int[] nums, int[] target) {
        int n = target.length;
        long totalOperations = 0;
        int prev = 0;

        for (int i = 0; i < n; i++) {
            int curr = target[i] - nums[i];

            if (curr > 0) {
                if (prev > 0) {
                    // Both positive: add only the height exceeding the previous bar
                    if (curr > prev) {
                        totalOperations += (curr - prev);
                    }
                } else {
                    // Sign flipped or started from 0: start fresh positive chain
                    totalOperations += curr;
                }
            } else if (curr < 0) {
                if (prev < 0) {
                    // Both negative: add only the depth exceeding the previous valley
                    if (curr < prev) {
                        totalOperations += (prev - curr); // (Math.abs(curr) - Math.abs(prev))
                    }
                } else {
                    // Sign flipped or started from 0: start fresh negative chain
                    totalOperations += Math.abs(curr);
                }
            }

            prev = curr;
        }

        return totalOperations;
    }

    /*
     * ------------------------------------------------------------------------
     * Alternate Ultra-Compact Math Formulation:
     * Sum of positive parts of (curr - prev):
     * totalOperations += Math.max(0, curr - prev) for all i (with wrap around).
     * ------------------------------------------------------------------------
     */
    public long minimumOperationsCompact(int[] nums, int[] target) {
        long totalOperations = 0;
        int prev = 0;

        for (int i = 0; i < nums.length; i++) {
            int curr = target[i] - nums[i];

            if ((curr > 0 && prev > 0) || (curr < 0 && prev < 0)) {
                int add = Math.abs(curr) - Math.abs(prev);
                if (add > 0) {
                    totalOperations += add;
                }
            } else {
                totalOperations += Math.abs(curr);
            }

            prev = curr;
        }

        return totalOperations;
    }
}
