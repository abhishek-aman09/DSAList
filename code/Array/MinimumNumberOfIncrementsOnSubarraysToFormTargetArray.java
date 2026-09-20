package Array;

/*
 * ============================================================================
 * LEETCODE 1526: Minimum Number of Increments on Subarrays to Form a Target Array
 * ============================================================================
 * Link: https://leetcode.com/problems/minimum-number-of-increments-on-subarrays-to-form-a-target-array/
 *
 * DESCRIPTION:
 * You are given an integer array target. You have an integer array initial of 
 * the same size with all zeros initially.
 *
 * In one operation you can choose any subarray from initial and increment each 
 * value by one.
 *
 * Return the minimum number of operations to form a target array from initial.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * Input: target = [1,2,3,2,1]
 * Output: 3
 * Explanation: We need at least 3 operations to form the target array:
 * 1. Increment subarray [0...4]: [1,1,1,1,1]
 * 2. Increment subarray [1...3]: [1,2,2,2,1]
 * 3. Increment subarray [2...2]: [1,2,3,2,1]
 *
 * ----------------------------------------------------------------------------
 * APPROACH: One-Pass Greedy / Differential Peak Analysis
 *
 * 1. Physical / Visual Intuition:
 *    - Imagine the array values as building heights or a skyline.
 *    - Each operation places a horizontal block of height 1 spanning any 
 *      contiguous range of columns.
 *    - To build the first bar target[0], we need exactly target[0] operations 
 *      starting at index 0.
 *    - Moving from left to right (from target[i - 1] to target[i]):
 *        a) If target[i] <= target[i - 1]:
 *           All target[i] horizontal layers can simply be extended from the 
 *           operations already initiated for target[i - 1]. No new operations 
 *           need to begin at index i.
 *        b) If target[i] > target[i - 1]:
 *           The first target[i - 1] layers are carried over from the left, but 
 *           the remaining height difference (target[i] - target[i - 1]) represents 
 *           new, higher layers that MUST begin at or after index i. 
 *           Thus, exactly (target[i] - target[i - 1]) new operations are required.
 *
 * 2. Equivalence to LeetCode 3229 / Rain Water Style Logic:
 *    - Total operations = target[0] + Sum(max(0, target[i] - target[i - 1]))
 *    - By initializing `prev = 0`, the first element target[0] naturally pays 
 *      (target[0] - 0) operations under the exact same condition `el > prev`.
 *
 * 3. Complexity Analysis:
 *    - Time Complexity:  O(N) — single pass through the array.
 *    - Space Complexity: O(1) — two primitive integer variables, zero memory overhead.
 * ============================================================================
 */
public class MinimumNumberOfIncrementsOnSubarraysToFormTargetArray {

    public int minNumberOperations(int[] target) {
        int prev = 0;
        int ans = 0;

        for (int el : target) {
            // Whenever the height increases, new subarray increments must begin
            if (el > prev) {
                ans += (el - prev);
            }
            prev = el;
        }

        return ans;
    }
}
