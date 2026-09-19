package Google.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class NumOfWaysToArriveAtDestination {

    /*
    https://leetcode.com/problems/number-of-ways-to-arrive-at-destination/description/
    
    You are in a city that consists of n intersections numbered from 0 to n - 1 with bi-directional roads between some intersections. 
    The inputs are generated such that you can reach any intersection from any other intersection and that there is at most one road between any two intersections.
    
    You are given an integer n and a 2D integer array roads where roads[i] = [ui, vi, timei] means that there is a road between intersections ui and vi that takes timei minutes to travel. 
    You want to know in how many ways you can travel from intersection 0 to intersection n - 1 in the shortest amount of time.
    
    Return the number of ways you can arrive at your destination in the shortest amount of time. Since the answer may be large, return it modulo 10e9 + 7.
    
    
    Input: n = 7, roads = [[0,6,7],[0,1,2],[1,2,3],[1,3,3],[6,3,3],[3,5,1],[6,5,1],[2,5,1],[0,4,5],[4,6,2]]
    Output: 4
    Explanation: The shortest amount of time it takes to go from intersection 0 to intersection 6 is 7 minutes.
    The four ways to get there in 7 minutes are:
    - 0 ➝ 6
    - 0 ➝ 4 ➝ 6
    - 0 ➝ 1 ➝ 2 ➝ 5 ➝ 6
    - 0 ➝ 1 ➝ 3 ➝ 5 ➝ 6
    
    
    
    * ============================================================================
    * APPROACH: Dijkstra's Algorithm with In-Flight Dynamic Programming
    * ============================================================================
    * 
    * 1. Problem Essence:
    *    We need to find the number of paths from node 0 to node (n - 1) that have
    *    the strictly minimum possible travel time, modulo 10^9 + 7.
    *
    * 2. Why Dijkstra + DP?
    *    - Standard Dijkstra finds the shortest distance from a source to all nodes.
    *    - Because edge weights are strictly positive, the graph of shortest paths
    *      forms a Directed Acyclic Graph (DAG).
    *    - Instead of running a separate DP or DFS pass after Dijkstra, we can count
    *      the number of ways to reach each node directly while Dijkstra runs:
    *        a) If we discover a strictly shorter path to node `v`:
    *           Reset ways[v] = ways[u], update minDist[v], and push `v` to the PQ.
    *        b) If we discover an alternative path to node `v` with the exact same
    *           minimal distance:
    *           Accumulate ways[v] = (ways[v] + ways[u]) % MOD. Do not push to PQ.
    *
    * 3. Avoiding Stale States:
    *    Using `if (currTime > minDistFrmSrc[u]) continue;` ensures stale, outdated
    *    PQ entries are dropped immediately, keeping Dijkstra's runtime optimal.
    *
    * 4. Complexity:
    *    - Time Complexity:  O((V + E) * log V), where V = n and E = roads.length.
    *    - Space Complexity: O(V + E) for adjacency list, minDist, and ways arrays.
    * ============================================================================
    */




    private static final long MOD = 1_000_000_007L;

    public int countPaths(int n, int[][] roads) {

        // Array to track the minimum time taken from source (node 0) to every node
        long minDistFrmSrc[] = new long[n];
        Arrays.fill(minDistFrmSrc, Long.MAX_VALUE / n);

        // Min-heap priority queue to process nodes in ascending order of arrival time
        Queue<Pair> q = new PriorityQueue<>((a, b) -> Long.compare(a.time, b.time));

        // Base case: starting at node 0 at time 0
        q.offer(new Pair(0, 0L));
        minDistFrmSrc[0] = 0L;

        // Build the bidirectional graph as an adjacency list
        List<int[]> graph[] = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int edge[] : roads) {
            int u = edge[0];
            int v = edge[1];
            int time = edge[2];

            graph[u].add(new int[]{v, time});
            graph[v].add(new int[]{u, time});
        }

        // DP array: ways[i] stores the number of shortest paths from node 0 to node i
        long ways[] = new long[n];
        ways[0] = 1; // Exactly 1 way to be at the starting node at time 0

        while (!q.isEmpty()) {
            Pair curr = q.poll();

            int u = curr.v;
            long currTime = curr.time;

            // Optimization: Skip processing if we already found a shorter path to `u`
            if (currTime > minDistFrmSrc[u]) {
                continue;
            }

            // Traverse all adjacent neighbors
            for (int[] child : graph[u]) {
                int v = child[0];
                long childTime = child[1];
                long newTime = currTime + childTime;

                // Case 1: Found a strictly shorter path to neighbor `v`
                if (newTime < minDistFrmSrc[v]) {
                    minDistFrmSrc[v] = newTime;
                    ways[v] = ways[u]; // Inherit path count directly from parent `u`
                    q.offer(new Pair(v, newTime));
                } 
                // Case 2: Found an alternative path to `v` with the exact same shortest time
                else if (newTime == minDistFrmSrc[v]) {
                    ways[v] = (ways[v] + ways[u]) % MOD; // Accumulate additional paths
                    // Note: Do not push to PQ again since `v` is already scheduled
                }
            }
        }

        // Return total shortest paths reaching the destination node (n - 1)
        return (int) (ways[n - 1] % MOD);
    }

    // Helper container class representing (node, accumulated time)
    private class Pair {
        int v;
        long time;

        Pair(int v, long time) {
            this.v = v;
            this.time = time;
        }
    }

    
}
