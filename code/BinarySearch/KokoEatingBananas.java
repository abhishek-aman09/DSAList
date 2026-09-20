package BinarySearch;

import java.util.Arrays;

/*
 * ============================================================================
 * LEETCODE 875: Koko Eating Bananas
 * ============================================================================
 * Link: https://leetcode.com/problems/koko-eating-bananas/
 *
 * DESCRIPTION:
 * Koko loves to eat bananas. There are n piles of bananas, the i-th pile has 
 * piles[i] bananas. The guards have gone and will come back in h hours.
 *
 * Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses 
 * some pile of bananas and eats k bananas from that pile. If the pile has less 
 * than k bananas, she eats all of them instead and will not eat any more bananas 
 * during this hour.
 *
 * Return the minimum integer k such that she can eat all the bananas within h hours.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * Input: piles = [3, 6, 7, 11], h = 8
 * Output: 4
 * Explanation:
 * At speed 4:
 * - Pile 3 takes ceil(3 / 4)  = 1 hour
 * - Pile 6 takes ceil(6 / 4)  = 2 hours
 * - Pile 7 takes ceil(7 / 4)  = 2 hours
 * - Pile 11 takes ceil(11 / 4) = 3 hours
 * Total = 1 + 2 + 2 + 3 = 8 hours <= 8. Speed 4 is optimal.
 *
 * ----------------------------------------------------------------------------
 * APPROACH: Binary Search on Answer Space [1, max(piles)]
 *
 * 1. Monotonicity:
 *    - If Koko can finish all piles at eating speed `k` within `h` hours, 
 *      she can also finish at any speed > `k`.
 *    - If she CANNOT finish at speed `k`, she cannot finish at any speed < `k`.
 *    - This monotonic property: [F, F, ..., F, T, T, ..., T] enables binary search.
 *
 * 2. Search Space:
 *    - Lower bound `left = 1` (minimum possible eating speed).
 *    - Upper bound `right = max(piles)` (eating faster than the largest pile 
 *      gives no benefit, since Koko only eats from one pile per hour).
 *    - Note on Sorting: Finding the maximum takes O(N) with a simple loop. 
 *      Sorting the array is unnecessary and avoids the O(N log N) overhead.
 *
 * 3. Ceiling Division Without Floating-Point Math:
 *    - `Math.ceil((double) el / currSpeed)` introduces floating-point division 
 *      and precision overhead.
 *    - Standard integer formula: `ceil(A / B) = (A + B - 1) / B`.
 *      For example: (7 + 4 - 1) / 4 = 10 / 4 = 2.
 *
 * 4. Integer Overflow Protection:
 *    - Summing hours across 10^4 piles with pile size up to 10^9 can exceed 
 *      Integer.MAX_VALUE if speed is 1. `totalHours` must be tracked as `long`.
 *
 * 5. Complexity:
 *    - Time Complexity:  O(N * log(max(piles)))
 *                        Binary search does ~30 iterations for max pile = 10^9.
 *                        Each iteration does an O(N) check.
 *    - Space Complexity: O(1) auxiliary space.
 * ============================================================================
 */
public class KokoEatingBananas {

    public int minEatingSpeed(int[] piles, int h) {
        // Step 1: Find the maximum pile in O(N) without sorting
        int left = 1;
        int right = 0;

        for (int pile : piles) {
            if (pile > right) {
                right = pile;
            }
        }

        int minSpeed = right;

        // Step 2: Binary search on the answer domain [1, right]
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (canFinish(piles, mid, h)) {
                minSpeed = mid;       // Found a valid speed, try to find a smaller one
                right = mid - 1;
            } else {
                left = mid + 1;        // Too slow, increase speed
            }
        }

        return minSpeed;
    }

    /**
     * Checks if Koko can finish all piles within `h` hours at `speed` bananas/hr.
     */
    private boolean canFinish(int[] piles, int speed, int h) {
        long totalHours = 0;

        for (int pile : piles) {
            // Integer ceiling division: ceil(pile / speed) == (pile + speed - 1) / speed
            totalHours += (pile + speed - 1) / speed;

            // Early exit to save iterations
            if (totalHours > h) {
                return false;
            }
        }

        return true;
    }
}
