package Array;

import java.util.Arrays;

/*
 * ============================================================================
 * LEETCODE 135: Candy
 * ============================================================================
 * Link: https://leetcode.com/problems/candy/
 *
 * DESCRIPTION:
 * There are n children standing in a line. Each child is assigned a rating 
 * value given in the integer array ratings.
 *
 * You are giving candies to these children subjected to the following requirements:
 * 1. Each child must have at least one candy.
 * 2. Children with a higher rating get more candies than their neighbors.
 *
 * Return the minimum number of candies you need to have to distribute the candies 
 * to the children.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * Input: ratings = [1,0,2]
 * Output: 5
 * Explanation: You can allocate to the first, second and third child with 
 * 2, 1, 2 candies respectively.
 *
 * ----------------------------------------------------------------------------
 * APPROACH: Two-Pass Greedy (Left-to-Right and Right-to-Left)
 *
 * 1. Problem Decomposition:
 *    The condition "higher rating than both neighbors" can be separated into 
 *    two independent directional constraints:
 *      - Left Neighbor Constraint:  If ratings[i] > ratings[i - 1], candies[i] > candies[i - 1]
 *      - Right Neighbor Constraint: If ratings[i] > ratings[i + 1], candies[i] > candies[i + 1]
 *
 * 2. Pass 1 (Left to Right):
 *    - Initialize every child with 1 candy.
 *    - Scan from i = 1 to n - 1:
 *        If ratings[i] > ratings[i - 1], set candies[i] = candies[i - 1] + 1.
 *    - This pass satisfies the left-neighbor condition completely.
 *
 * 3. Pass 2 (Right to Left):
 *    - Scan from i = n - 2 down to 0:
 *        If ratings[i] > ratings[i + 1], child i must have more candies than child i + 1.
 *        To satisfy both left and right conditions simultaneously, assign:
 *          candies[i] = Math.max(candies[i], candies[i + 1] + 1).
 *
 * 4. Complexity:
 *    - Time Complexity:  O(N) — two sequential passes through the array of length N.
 *    - Space Complexity: O(N) — auxiliary array to store the candy count per child.
 * ============================================================================
 */
public class Candy {

    public int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];

        // Requirement 1: Each child must get at least 1 candy
        Arrays.fill(candies, 1);

        // Pass 1: Resolve left-to-right dependencies
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1;
            }
        }

        // Pass 2: Resolve right-to-left dependencies while preserving Pass 1 constraints
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = Math.max(candies[i], candies[i + 1] + 1);
            }
        }

        // Sum total candies needed
        int totalCandies = 0;
        for (int count : candies) {
            totalCandies += count;
        }

        return totalCandies;
    }

    /*
     * ------------------------------------------------------------------------
     * OPTIONAL: O(1) Auxiliary Space Peak-Valley Greedy Approach
     * Tracks slopes directly (up, down, peak) in a single pass without an array.
     * Time: O(N), Space: O(1)
     * ------------------------------------------------------------------------
     */
    public int candyConstantSpace(int[] ratings) {
        int n = ratings.length;
        if (n <= 1) return n;

        int candies = 1;
        int up = 0;
        int down = 0;
        int peak = 0;

        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                up++;
                down = 0;
                peak = up;
                candies += 1 + up;
            } else if (ratings[i] == ratings[i - 1]) {
                up = 0;
                down = 0;
                peak = 0;
                candies += 1;
            } else {
                down++;
                up = 0;
                // If the downward slope exceeds the peak height, the peak must be bumped up
                candies += 1 + down + (down > peak ? 0 : -1);
            }
        }

        return candies;
    }
}
