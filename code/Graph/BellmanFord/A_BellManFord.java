package Graph.BellmanFord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class A_BellManFord {
    
    /*
    * ============================================================================
    * GEEKSFORGEEKS: Distance from the Source (Bellman-Ford Algorithm)
    * ============================================================================
    * Link: https://www.geeksforgeeks.org/problems/distance-from-the-source-bellman-ford-algorithm/1
    *
    * DESCRIPTION:
    * Given a weighted and directed graph of V vertices and E edges in the form 
    * of an adjacency list, find the shortest distance of all the vertices from 
    * the source vertex src. If a negative weight cycle exists in the graph that 
    * is reachable from the source, return an array consisting of only -1.
    *
    * ----------------------------------------------------------------------------
    * EXAMPLE:
    * Input: V = 3, edges = [[0, 1, 5], [1, 0, 3], [1, 2, -1], [2, 0, 1]], src = 2
    * Output: [1, 6, 0]
    * Explanation: 
    * - Shortest distance from 2 to 0 is 2 -> 0 with distance 1.
    * - Shortest distance from 2 to 1 is 2 -> 0 -> 1 with distance 1 + 5 = 6.
    * - Shortest distance from 2 to 2 is 0.
    *
    * ----------------------------------------------------------------------------
    * APPROACH: Classic Bellman-Ford (Single-Source Shortest Path with Negative Cycle Detection)
    *
    * 1. Why Bellman-Ford?
    *    - Dijkstra fails when edges have negative weights because its greedy 
    *      assumption (once a node is extracted, its distance is finalized) breaks down.
    *    - Bellman-Ford relaxes all E edges systematically over (V - 1) iterations.
    *
    * 2. Why (V - 1) Rounds of Relaxation?
    *    - In any graph without negative cycles, the shortest simple path between 
    *      any two vertices contains at most (V - 1) edges.
    *    - Each full relaxation round guarantees that shortest paths with up to 
    *      k edges are correctly resolved. Hence, (V - 1) rounds ensure all 
    *      reachable vertices have their optimal shortest distance finalized.
    *
    * 3. Detecting Reachable Negative Cycles (The V-th Pass):
    *    - If we perform an extra pass over all edges and ANY edge (u -> v with weight w) 
    *      can still be relaxed (minDist[u] + w < minDist[v]), it means the path length 
    *      is continuing to decrease past (V - 1) edges.
    *    - This confirms the presence of a reachable negative-weight cycle, so we return [-1].
    *
    * 4. Value of INF:
    *    - GFG problem constraints specify unreachable nodes should maintain 10^8 (100000000).
    *    - Keeping INF around 10^8 also prevents 32-bit signed integer overflow when 
    *      performing addition: `minDist[u] + w`.
    *
    * 5. Complexity:
    *    - Time Complexity:  O(V * E)
    *                        - (V - 1) passes of E edge relaxations: (V - 1) * E
    *                        - 1 cycle-check pass: E
    *                        - Total operations: V * E.
    *    - Space Complexity: O(V) for storing the minDist array and final output list.
    * ============================================================================
    */

    public ArrayList<Integer> bellmanFord(int V, int[][] edges, int src) {
        
        // minDist[i] tracks the shortest distance from src to node i
        int[] minDist = new int[V];
        
        // GFG convention: unreachable nodes should have value 10^8 (1e8)
        final int INF = 100_000_000;
        
        // Initialize all distances to infinity
        Arrays.fill(minDist, INF);
        
        // Source distance to itself is always 0
        minDist[src] = 0;
        
        // Step 1: Relax all edges (V - 1) times
        for (int rotation = 1; rotation < V; rotation++) {
            
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int weight = edge[2];
                
                // Relax edge if u is reachable and path through u is strictly shorter
                if (minDist[u] != INF && minDist[u] + weight < minDist[v]) {
                    minDist[v] = minDist[u] + weight;
                }
            }
        }
        
        // Step 2: Perform the V-th pass to detect reachable negative-weight cycles
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];
            
            // If any edge can still be relaxed, an augmenting negative cycle exists
            if (minDist[u] != INF && minDist[u] + weight < minDist[v]) {
                return new ArrayList<>(List.of(-1));
            }
        }
        
        // Step 3: Convert primitive array to ArrayList<Integer>
        ArrayList<Integer> result = new ArrayList<>(V);
        for (int distance : minDist) {
            result.add(distance);
        }
        
        return result;
    }
}
