package Google.Graph;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class HighScore {

    /*
    APPROACH: Bellman-Ford with Two-Way Reachability Filtering
    * ============================================================================
    * 
    * 1. Problem Overview:
    *    We want to find the maximum possible score walking from room 1 to room n.
    *    Since tunnels can be revisited and weights can be positive or negative:
    *    - If an arbitrarily large score is possible, print -1.
    *    - Otherwise, print the maximum score.
    *
    * 2. Why Bellman-Ford?
    *    - Finding the longest simple path in a general graph is NP-hard, but 
    *      in Directed Graphs with cycles, a cycle that increases the score 
    *      (positive cycle) allows score augmentation to infinity.
    *    - Bellman-Ford naturally handles cycle detection and edge relaxation.
    *      Instead of minimizing distances, we maximize accumulated scores:
    *      Relax when: dist[u] + weight > dist[v].
    *
    * 3. The Core Trap (Irrelevant Cycles):
    *    - A positive cycle allows an infinite score if and only if:
    *        a) It is reachable from starting room 1.
    *        b) It can eventually reach destination room n.
    *    - If a positive cycle is trapped in a dead end (cannot reach n) or is
    *      disconnected from 1, it has zero impact on valid walks from 1 to n.
    *
    * 4. Two-Way Reachability via BFS:
    *    - Forward BFS from 1: Marks all nodes reachable from room 1.
    *    - Backward BFS from n: By reversing edge directions (u <- v), a single BFS
    *      from room n identifies all nodes that can reach room n in O(V + E) time.
    *    - During the cycle detection pass (step n), we only flag an infinite loop 
    *      if the node being relaxed satisfies:
    *      reachableFrom1[v] && canReachN[v].
    *
    * 5. Data Types:
    *    - Scores can reach up to 2500 * 10^9 = 2.5 * 10^12, which exceeds 32-bit int.
    *    - We must use 64-bit signed integers (`long`) for all distances and weights.
    *
    * 6. Complexity:
    *    - Time Complexity:  O(V * E) for Bellman-Ford + O(V + E) for BFS traversals.
    *                        With V <= 2500, E <= 5000, operations ~ 1.25 * 10^7 (well under 1 sec).
    *    - Space Complexity: O(V + E) for adjacency lists and reachability arrays.
    * ============================================================================
    * 
    
    */
   
    /**
     * Coordinates reachability pruning, Bellman-Ford score maximization, 
     * and valid positive-cycle verification.
     */

    // Safe negative infinity to prevent underflow when adding negative weights
    private static final long INF = (long) 1e17;

    public static long solve(int n, int[][] edges, List<Integer>[] forwardGraph, List<Integer>[] reverseGraph) {
        // Step 1: Find all nodes reachable from room 1
        boolean[] reachableFrom1 = getReachability(1, forwardGraph, n);

        // Step 2: Find all nodes that can reach room n (by running BFS on the reversed graph)
        boolean[] canReachN = getReachability(n, reverseGraph, n);

        // Step 3: Run (n - 1) rounds of Bellman-Ford relaxations to compute maximum scores
        long[] dist = computeMaxDistances(1, n, edges);

        // Step 4: Run the n-th check to detect reachable positive cycles
        if (hasInfinitelyAugmentingCycle(edges, dist, reachableFrom1, canReachN)) {
            return -1; // Arbitrarily large score possible
        }

        return dist[n];
    }

    /**
     * Initializes an empty adjacency list array of size (n + 1).
     */
    @SuppressWarnings("unchecked")
    private static List<Integer>[] createGraph(int n) {
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        return graph;
    }

    /**
     * Standard BFS to determine all nodes reachable from the specified source node.
     */
    private static boolean[] getReachability(int start, List<Integer>[] adj, int n) {
        boolean[] visited = new boolean[n + 1];
        Queue<Integer> queue = new ArrayDeque<>();

        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int next : adj[curr]) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }

        return visited;
    }

    /**
     * Executes (n - 1) rounds of Bellman-Ford edge relaxations to find the maximum 
     * path score from room 1 to all reachable rooms.
     */
    private static long[] computeMaxDistances(int src, int n, int[][] edges) {
        long[] dist = new long[n + 1];
        Arrays.fill(dist, -INF);
        dist[src] = 0; // Starting score at room 1 is 0

        // In a graph without positive cycles, the longest simple path has at most (n - 1) edges
        for (int i = 1; i < n; i++) {
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int w = edge[2];

                // Relax edge if node u has been reached and traversing u -> v increases score at v
                if (dist[u] != -INF && dist[u] + w > dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }

        return dist;
    }

    /**
     * Performs an n-th iteration over all edges to test for cycle expansion.
     * A cycle allows an infinite score only if it is reachable from room 1 AND can reach room n.
     */
    private static boolean hasInfinitelyAugmentingCycle(int[][] edges, long[] dist, 
            boolean[] reachableFrom1, boolean[] canReachN) {
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];

            // If an edge can still be relaxed after (n - 1) iterations, a positive cycle exists
            if (dist[u] != -INF && dist[u] + w > dist[v]) {
                // Confirm whether this cycle sits on a valid journey from room 1 to room n
                if (reachableFrom1[v] && canReachN[v]) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        // Flat edge list for Bellman-Ford relaxations
        int[][] edges = new int[m][3];

        // Adjacency lists for forward and backward reachability checks
        List<Integer>[] forwardGraph = createGraph(n);
        List<Integer>[] reverseGraph = createGraph(n);

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();

            edges[i] = new int[]{u, v, w};
            forwardGraph[u].add(v);  // Forward direction: u -> v
            reverseGraph[v].add(u);  // Reversed direction: v -> u
        }

        // Solve and output the result
        long result = solve(n, edges, forwardGraph, reverseGraph);
        System.out.println(result);
    }
    
}
