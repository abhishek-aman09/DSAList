package DynamicProgramming.PartitionDP;

import java.util.Arrays;

public class MinimumCostToCutStick {
    
    /** https://leetcode.com/problems/minimum-cost-to-cut-a-stick/description/
     * Given a wooden stick of length n units. The stick is labelled from 0 to n.
     * Given an integer array cuts where cuts[i] denotes a position you should perform a cut at.
    
        You should perform the cuts in order, you can change the order of the cuts as you wish.
    
        The cost of one cut is the length of the stick to be cut,
        the total cost is the sum of costs of all cuts. When you cut a stick, 
        it will be split into two smaller sticks (i.e. the sum of their lengths is the length of the stick before the cut). 
    
        Approach Similar to MCM.
        We need to define a new array with boundaries i.e. 0 and n at both ends
        this will help us determine the length of the rod without passing the length


    */


    public int minCost(int n, int[] cuts) {

        int len = cuts.length;

        if (len < 1) {
            return 0;
        }

        // 1. BOUNDARY PADDING:
        // A stick segment requires two endpoints to determine its length.
        // We append virtual cut points at position 0 (left end of stick)
        // and position n (right end of stick).
        int cutsWithBoundary[] = new int[len + 2];
        cutsWithBoundary[0] = 0;
        cutsWithBoundary[len + 1] = n;

        for (int i = 1; i < len + 1; i++) {
            cutsWithBoundary[i] = cuts[i - 1];
        }

        // 2. SORTING IS MANDATORY:
        // Sorting ensures that any cut position k strictly falls between
        // cut points i and j if and only if i < k < j.
        // Without sorting, a cut at index k might not physically divide the 
        // segment defined by boundaries cuts[i] and cuts[j].
        Arrays.sort(cutsWithBoundary);

        int dp[][] = new int[len + 2][len + 2];
        for (int row[] : dp) {
            Arrays.fill(row, -1);
        }

        // Initial problem: stick bounded by cut 0 (position 0) and cut len+1 (position n)
        return helper(cutsWithBoundary, 0, len + 1, dp);
    }

    private int helper(int cuts[], int i, int j, int dp[][]) {

        // BASE CASE: No cuts remaining inside the current segment.
        // If j - i <= 1, indices i and j are adjacent in the sorted array.
        // There are no internal cut points between them (e.g., boundaries 0 and 1).
        // Making 0 cuts costs 0.
        if (j - i <= 1) {
            return 0;
        }

        // MEMOIZATION CHECK:
        // dp[i][j] stores the minimum cost to perform all remaining cuts
        // strictly strictly strictly between boundary cuts[i] and cuts[j].
        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int minCost = Integer.MAX_VALUE;

        // TRY EVERY CANDIDATE CUT 'k':
        // Here, 'k' is chosen as the FIRST cut made on the stick segment [cuts[i], cuts[j]].
        for (int k = i + 1; k < j; k++) {

            // 1. COST OF THE CURRENT CUT:
            // By problem definition, cutting a stick costs its current length:
            // length = cuts[j] - cuts[i].
            //
            // 2. SUBPROBLEM DECOMPOSITION:
            // Making cut 'k' splits the current stick into two independent sub-sticks:
            //   - Left stick:  bounded by cuts[i] and cuts[k] -> helper(cuts, i, k, dp)
            //   - Right stick: bounded by cuts[k] and cuts[j] -> helper(cuts, k, j, dp)
            // Notice that 'k' acts as the right boundary for the left segment
            // and the left boundary for the right segment.
            int cost = (cuts[j] - cuts[i]) 
                    + helper(cuts, i, k, dp) 
                    + helper(cuts, k, j, dp);

            // Track the minimum cost across all choices of first cut 'k'
            if (cost < minCost) {
                minCost = cost;
            }
        }

        return dp[i][j] = minCost;
    }

    public static void main(String[] args) {
        int arr[] = { 5 };

        MinimumCostToCutStick obj = new MinimumCostToCutStick();

        System.out.println(obj.minCost(7, arr));
    }
}
