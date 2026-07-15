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

    private static final int MIN_VAL = Integer.MIN_VALUE / 4;
    
    public int maxWeight(int n, int[][] edges, int k, int t) {

        if (k == 0) {
            return 0;
        }

        // construct the graph
        Map<Integer, List<int[]>> graph = new HashMap<>();

        // dp to store max value for given node for given steps remaining and current sum til the node
        int[][][] dp = new int[n][k + 1][t];
        Arrays.stream(dp).forEach(grid-> Arrays.stream(grid).forEach(row -> Arrays.fill(row, -1)));

        for (int el[] : edges) {
            int u = el[0];
            int v = el[1];
            int w = el[2];

            graph.computeIfAbsent(u, key -> new ArrayList<>()).add(new int[] { v, w });
        }

        int ans = -1;

        // calculate max value for each node as source
        for (int u : graph.keySet()) {
            int result = getMaxWeight(u, graph, k, 0, t, dp);
            
            ans = Math.max(result, ans);
        }

        return ans;

    }

    private int getMaxWeight(int u, Map<Integer, List<int[]>> graph, int steps, int currSum, int maxSum, int[][][] dp) {

        // if no steps are remaining, return 0
        if (steps == 0) {
            return 0;
        }

        // check for precomputed value
        if (dp[u][steps][currSum] != -1) {
            return dp[u][steps][currSum];
        }

        List<int[]> children = graph.getOrDefault(u, null);

        // if given node has no children and steps are left, we return min as we cannot reach further
        if (children == null) {
            return dp[u][steps][currSum] = MIN_VAL;
        }

        int ans = MIN_VAL;

        for (int[] child : children) {
            int v = child[0];
            int w = child[1];

            // for each node, check if the net sum will be in limit.
            if (currSum + w < maxSum) {
                 int nextSteps =  getMaxWeight(v, graph, steps - 1, currSum + w, maxSum, dp);
                ans = Math.max(w + nextSteps, ans);   
            }
        }

        // store it value before returning
        return dp[u][steps][currSum] = ans;

    }
    
    public static void main(String[] args) {
        MaxWeightKEdgePath obj = new MaxWeightKEdgePath();

        int edges[][] = { { 0, 1, 2 }, { 0, 2, 3 } };

        System.out.println(obj.maxWeight(3, edges, 1, 3));
    }

}
