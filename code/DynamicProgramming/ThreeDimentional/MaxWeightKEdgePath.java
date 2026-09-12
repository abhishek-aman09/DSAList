package DynamicProgramming.ThreeDimentional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MaxWeightKEdgePath {

    /*
    https://leetcode.com/problems/maximum-weighted-k-edge-path
    
    You are given an integer n and a Directed Acyclic Graph (DAG) with n nodes labeled from 0 to n - 1. This is represented by a 2D array edges, where edges[i] = [ui, vi, wi] indicates a directed edge from node ui to vi with weight wi.
    
    You are also given two integers, k and t.
    
    Your task is to determine the maximum possible sum of edge weights for any path in the graph such that:
    
    The path contains exactly k edges.
    The total sum of edge weights in the path is strictly less than t.
    Return the maximum possible sum of weights for such a path. If no such path exists, return -1.
    
    Input: n = 3, edges = [[0,1,1],[1,2,2]], k = 2, t = 4
    
    Output: 3
    
    Explanation:
    The only path with k = 2 edges is 0 -> 1 -> 2 with weight 1 + 2 = 3 < t.
    Thus, the maximum possible sum of weights less than t is 3.
    
    approach : construct a graph, do a dfs for k steps, return the max value from
    any of the recursive call
    */

    // Sentinel value used to represent an impossible/invalid state.
    // Divided by 4 to safely allow edge additions (w + nextSteps) without 32-bit integer underflow.
    private static final int MIN_VAL = Integer.MIN_VALUE / 4;

    public int maxWeight(int n, int[][] edges, int k, int t) {

        // BASE EDGE CASE: A path of length 0 gathers 0 weight.
        if (k == 0) {
            return 0;
        }

        // 1. ADJACENCY LIST GRAPH CONSTRUCTION:
        // Map u -> List of [neighbor v, edge weight w]
        Map<Integer, List<int[]>> graph = new HashMap<>();

        for (int el[] : edges) {
            int u = el[0];
            int v = el[1];
            int w = el[2];
            graph.computeIfAbsent(u, key -> new ArrayList<>()).add(new int[] { v, w });
        }

        // 2. MEMOIZATION TABLE:
        // dp[u][steps][currSum] caches:
        // Max additional weight collected from node u with 'steps' edges remaining,
        // given that the path up to node u has already accumulated 'currSum' weight.
        int[][][] dp = new int[n][k + 1][t];
        Arrays.stream(dp).forEach(grid -> Arrays.stream(grid).forEach(row -> Arrays.fill(row, -1)));

        int ans = -1;

        // 3. EXPLORE EVERY NODE AS A POTENTIAL STARTING POINT:
        // The path of length k can originate from any valid source node u.
        for (int u : graph.keySet()) {
            int result = getMaxWeight(u, graph, k, 0, t, dp);
            ans = Math.max(result, ans);
        }

        // Returns -1 if no path of length k with total sum < t could be formed.
        return ans;
    }

    private int getMaxWeight(int u, Map<Integer, List<int[]>> graph, int steps, int currSum, int maxSum, int[][][] dp) {

        // BASE CASE 1: Successfully used all k edges.
        // If no remaining steps are needed, 0 additional weight is gathered.
        if (steps == 0) {
            return 0;
        }

        // MEMOIZATION CHECK:
        // Return precomputed max path weight extension for node u with identical remaining steps and current sum.
        if (dp[u][steps][currSum] != -1) {
            return dp[u][steps][currSum];
        }

        List<int[]> children = graph.getOrDefault(u, null);

        // BASE CASE 2: Dead end.
        // Node u has no outgoing edges (out-degree = 0) but we still need steps > 0 edges.
        // This path is invalid -> return impossible sentinel.
        if (children == null) {
            return dp[u][steps][currSum] = MIN_VAL;
        }

        int ans = MIN_VAL;

        // 4. TRANSITION: TRY ALL DIRECT OUTGOING EDGES (u -> v):
        for (int[] child : children) {
            int v = child[0];
            int w = child[1];

            // CONSTRAINT PRUNING:
            // Only explore this branch if taking edge weight 'w' keeps the total weight strictly below maxSum (t).
            if (currSum + w < maxSum) {
                
                // Recurse to node v:
                // - Decrement required remaining edges (steps - 1)
                // - Accumulate new weight into running sum (currSum + w)
                int nextSteps = getMaxWeight(v, graph, steps - 1, currSum + w, maxSum, dp);
                
                // Only update ans if the subproblem returned a valid path (not MIN_VAL)
                ans = Math.max(w + nextSteps, ans);   
            }
        }

        // Memoize and return the optimal additional path weight from this state
        return dp[u][steps][currSum] = ans;
    }
    
    public static void main(String[] args) {
        MaxWeightKEdgePath obj = new MaxWeightKEdgePath();

        int edges[][] = { { 0, 1, 2 }, { 0, 2, 3 } };

        System.out.println(obj.maxWeight(3, edges, 1, 3));
    }

}
