package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MinimumCostPathWithEdgeReversal {

    // https://leetcode.com/problems/minimum-cost-path-with-edge-reversals/description/
    
    public int minCost(int n, int[][] edges) {

        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            
            adj[u].add(new int[]{v, w});
            adj[v].add(new int[]{u, 2 * w});
        }

        long[] minCosts = new long[n];
        Arrays.fill(minCosts, Long.MAX_VALUE);
        minCosts[0] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.offer(new long[]{0, 0});

        while (!pq.isEmpty()) {
            long[] current = pq.poll();
            long d = current[0];
            int u = (int) current[1];

            if (d > minCosts[u]) continue;
            if (u == n - 1) return (int) d;

            for (int[] neighbor : adj[u]) {
                int v = neighbor[0];
                int weight = neighbor[1];
                
                if (minCosts[u] + weight < minCosts[v]) {
                    minCosts[v] = minCosts[u] + weight;
                    pq.offer(new long[]{minCosts[v], v});
                }
            }
        }

        return minCosts[n - 1] == Long.MAX_VALUE ? -1 : (int) minCosts[n - 1];
    }
}

