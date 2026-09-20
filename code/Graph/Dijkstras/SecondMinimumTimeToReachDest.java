package Graph.Dijkstras;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * ============================================================================
 * LEETCODE 2045: Second Minimum Time to Reach Destination
 * ============================================================================
 * Link: https://leetcode.com/problems/second-minimum-time-to-reach-destination/
 *
 * DESCRIPTION:
 * A city is represented as a bi-directional connected graph with n vertices 
 * labeled from 1 to n, and an array edges where edges[i] = [ui, vi].
 *
 * Each edge requires `time` minutes to traverse. Signals at all vertices change 
 * simultaneously every `change` minutes:
 * - Starts GREEN at minute 0.
 * - Alternates: [0, change) GREEN, [change, 2*change) RED, etc.
 * - You may only leave a vertex when the signal is GREEN. If you arrive during 
 *   a RED signal, you must wait until the signal turns GREEN before departing.
 *
 * Return the strictly second minimum time it takes to travel from vertex 1 to vertex n.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * Input: n = 5, edges = [[1,2],[1,3],[1,4],[3,4],[4,5]], time = 3, change = 5
 * Output: 13
 * Explanation:
 * The shortest path is 1 -> 4 -> 5, taking 2 edges.
 * The strictly second shortest path takes 3 edges: 1 -> 3 -> 4 -> 5.
 * Total travel time factoring traffic lights evaluates to 13.
 *
 * ----------------------------------------------------------------------------
 * APPROACH: Multi-State BFS (Shortest & Strictly Second Shortest Paths)
 *
 * 1. Edge Weight Uniformity:
 *    - All edges take the exact same duration `time`, and traffic signals are 
 *      identical across all nodes.
 *    - Because edge cost is uniform, the strictly second shortest time must 
 *      correspond to a path that traverses strictly more edges than the shortest path 
 *      (either d_min + 1 edges via an alternate path or d_min + 2 edges by stepping back and forth).
 *
 * 2. Two-Distance Tracking:
 *    - Maintain two distance arrays:
 *        - `dist1[v]`: strictly shortest arrival time at node v.
 *        - `dist2[v]`: strictly second shortest arrival time at node v.
 *    - Each node is enqueued at most twice (once when updating dist1, and once when updating dist2).
 *
 * 3. Traffic Light Signal Simulation (With Concrete Timeline Example):
 *    - Lights start GREEN at minute 0 and switch every `change` minutes.
 *      Suppose `change = 5`, `time = 3`:
 *
 *        Timeline:   0        5        10       15       20
 *                    |--------|--------|--------|--------|
 *        Color:        GREEN     RED     GREEN     RED    GREEN
 *        Cycle (k):      0        1        2        3       4
 *
 *    - Step A: Find which cycle bucket `departureTime` falls into:
 *        `signalStatus = departureTime / change`  (integer division)
 *
 *    - Step B: Check signal color:
 *        * If `signalStatus % 2 == 0` (EVEN cycle: 0, 2, 4...):
 *          The light is GREEN. No waiting needed.
 *          Example: Arrive at minute 12 -> 12 / 5 = 2 (Even -> GREEN).
 *                   Departure remains 12.
 *                   Arrival at neighbor = 12 + 3 = 15.
 *
 *        * If `signalStatus % 2 == 1` (ODD cycle: 1, 3, 5...):
 *          The light is RED. You are blocked and must wait until the NEXT cycle starts.
 *          The next cycle starts at index `(signalStatus + 1) * change`.
 *          Example: Arrive at minute 7 -> 7 / 5 = 1 (Odd -> RED).
 *                   Wait until next cycle: (1 + 1) * 5 = 10.
 *                   Departure is delayed to 10 (waited 3 minutes).
 *                   Arrival at neighbor = 10 + 3 = 13.
 *
 * 4. Relaxation Rules:
 *    - If arrivalTime < dist1[child]:
 *        dist2[child] = dist1[child]
 *        dist1[child] = arrivalTime
 *        enqueue (child, arrivalTime)
 *    - Else if arrivalTime > dist1[child] && arrivalTime < dist2[child]:
 *        dist2[child] = arrivalTime
 *        enqueue (child, arrivalTime)
 *    - Else: ignore (either duplicate dist1, or >= dist2).
 *
 * 5. Complexity:
 *    - Time Complexity:  O(V + E)
 *                        Each vertex is enqueued at most twice, traversing its incident 
 *                        edges at most twice across the entire BFS.
 *    - Space Complexity: O(V + E) for the adjacency list, queue, and distance tables.
 * ============================================================================
 */

public class SecondMinimumTimeToReachDest {

    public int secondMinimum(int n, int[][] edges, int time, int change) {

        // Step 1: Build undirected adjacency list (1-indexed)
        List<Integer>[] graph = new ArrayList[n + 1];
        final int INF = Integer.MAX_VALUE;

        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            graph[u].add(v);
            graph[v].add(u);
        }

        // Step 2: Initialize 1st and strictly 2nd shortest distance tables
        int[] dist1 = new int[n + 1];
        int[] dist2 = new int[n + 1];

        Arrays.fill(dist1, INF);
        Arrays.fill(dist2, INF);

        // Base case: starting at node 1 at time 0
        dist1[1] = 0;

        // Queue stores states as: {node, currentTime}
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{1, 0});

        // Step 3: Run BFS to find strictly second shortest path to destination n
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int node = curr[0];
            int currentTime = curr[1];

            // Early exit: once dist2[n] is finalized and popped, strictly second minimum time is found
            if (node == n && dist2[node] != INF) {
                return dist2[node];
            }

            // Step 4: Handle traffic signal delays before departing current node
            int departureTime = currentTime;
            
            // Determine cycle index: e.g., if change = 5, [0..4] -> 0 (green), [5..9] -> 1 (red)
            int signalStatus = departureTime / change;

            // Odd quotient indicates RED signal; must wait until the start of the next cycle (signalStatus + 1)
            // Example: arrive at min 7, change = 5 -> 7/5 = 1 (RED) -> wait until (1 + 1) * 5 = min 10
            if (signalStatus % 2 == 1) {
                departureTime = (signalStatus + 1) * change;
            }

            // Time when we reach the adjacent neighbor after traversing edge of length `time`
            int arrivalTime = departureTime + time;

            // Step 5: Relax incident edges
            for (int child : graph[node]) {

                // Case 1: Found a path strictly faster than the current best
                if (arrivalTime < dist1[child]) {
                    dist2[child] = dist1[child];
                    dist1[child] = arrivalTime;
                    queue.offer(new int[]{child, arrivalTime});
                } 
                // Case 2: Found a strictly second shortest path (greater than dist1, smaller than dist2)
                else if (arrivalTime > dist1[child] && arrivalTime < dist2[child]) {
                    dist2[child] = arrivalTime;
                    queue.offer(new int[]{child, arrivalTime});
                }
            }
        }

        return dist2[n];
    }
    
}
