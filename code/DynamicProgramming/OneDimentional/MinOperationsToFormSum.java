package DynamicProgramming.OneDimentional;

import java.util.*;

/*
 * ============================================================================
 * LEETCODE: Minimum Operations to Form Target Sum via Halving and Doubling
 * ============================================================================
 *
 * DESCRIPTION:
 * You are given an array of positive integers nums and an integer sum (target).
 * In one operation, you can either:
 *   1. Divide any element by 2 (integer division / floor).
 *   2. Multiply any element by 2.
 *
 * Each element can be transformed into any value reachable via repeated 
 * halving or doubling. You select a subset of transformed elements (at most one 
 * transformation per original element) such that their total equals `sum`.
 *
 * Return the minimum total operations needed, or -1 if impossible.
 *
 * ----------------------------------------------------------------------------
 * APPROACH: 0/1 Knapsack DP with Grouped Transformations
 *
 * 1. State Definition:
 *    Let dp[s] be the minimum operations required to achieve exact sum s.
 *    Initialize dp[0] = 0 and dp[s] = INF for all s > 0.
 *
 * 2. Per-Element Candidate Generation:
 *    For each original number in nums, we can:
 *      - Take nothing (cost 0, weight 0; implicit in 0/1 knapsack by keeping dp[s]).
 *      - Transform to num / (2^k) with cost k.
 *      - Transform to num * (2^k) with cost k (bounded by val <= sum).
 *    Store the best cost for each distinct candidate value in a map or flat array.
 *
 * 3. 0/1 Knapsack Transition (Grouped / Mutually Exclusive):
 *    Since each element in nums can be picked at most once (under one of its 
 *    transformed values), this is a Grouped 0/1 Knapsack problem.
 *    - To prevent picking multiple transformations of the same element, we must 
 *      derive nextDp strictly from the prior item's dp array:
 *        nextDp[s + val] = min(nextDp[s + val], dp[s] + opCost)
 *    - After iterating through all options of num, update dp = nextDp.
 *
 * 4. Optimization Details:
 *    - Replace HashMap<Integer, Integer> with two small parallel arrays (or an int[][])
 *      because each number has at most around log2(num) + log2(sum) <= 60 candidates.
 *    - Avoid clone() overhead by alternating between two pre-allocated arrays dp and nextDp.
 *
 * 5. Complexity:
 *    - Let K <= 60 be the maximum number of powers-of-two variants per number.
 *    - Time Complexity:  O(N * K * sum)
 *    - Space Complexity: O(sum) using two flat 1D arrays.
 * ============================================================================
 */
public class MinOperationsToFormSum {

    public int minOperations(int[] nums, int sum) {
        final int INF = 1_000_000_000;

        int[] dp = new int[sum + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;

        // Buffers for candidates of the current number to eliminate HashMap allocation
        int[] candVal = new int[70];
        int[] candCost = new int[70];

        for (int num : nums) {
            // Map distinct target values to their minimum operation cost
            Map<Integer, Integer> options = new HashMap<>();

            // 1. Generate halving candidates: num / 2^k
            int curr = num;
            int cost = 0;
            while (curr > 0) {
                if (curr <= sum) {
                    options.put(curr, Math.min(options.getOrDefault(curr, INF), cost));
                }
                curr /= 2;
                cost++;
            }

            // 2. Generate doubling candidates: num * 2^k
            long multiple = (long) num * 2; // num itself already added in halving loop (k=0)
            cost = 1;
            while (multiple <= sum) {
                int val = (int) multiple;
                options.put(val, Math.min(options.getOrDefault(val, INF), cost));
                multiple *= 2;
                cost++;
            }

            // Flatten candidates to fast arrays
            int candCount = 0;
            for (Map.Entry<Integer, Integer> entry : options.entrySet()) {
                candVal[candCount] = entry.getKey();
                candCost[candCount] = entry.getValue();
                candCount++;
            }

            // Grouped 0/1 Knapsack step: clone previous DP state (option to not pick this number)
            int[] nextDp = dp.clone();

            for (int s = 0; s <= sum; s++) {
                if (dp[s] == INF) continue;

                for (int i = 0; i < candCount; i++) {
                    int val = candVal[i];
                    int opCost = candCost[i];

                    if (s + val <= sum) {
                        nextDp[s + val] = Math.min(nextDp[s + val], dp[s] + opCost);
                    }
                }
            }

            dp = nextDp;
        }

        return dp[sum] >= INF ? -1 : dp[sum];
    }
}
