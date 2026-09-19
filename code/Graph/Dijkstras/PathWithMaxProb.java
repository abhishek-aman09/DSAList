package Graph.Dijkstras;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class PathWithMaxProb {
    /*
     * ============================================================================
     * LEETCODE 1514: Path with Maximum Probability
     * ============================================================================
     * Link: https://leetcode.com/problems/path-with-maximum-probability/
     *
     * DESCRIPTION:
     * You are given an undirected weighted graph of n nodes (0-indexed), 
     * represented by an edge list where edges[i] = [a, b] is an undirected edge 
     * connecting the nodes a and b with a probability of success of traversing 
     * that edge succProb[i].
     *
     * Given two nodes start and end, find the path with the maximum probability 
     * of success to go from start to end.
     *
     * If there is no path from start to end, return 0. Your answer will be 
     * accepted if it differs from the correct answer by at most 1e-5.
     *
     * ----------------------------------------------------------------------------
     * EXAMPLE:
     * Input: n = 3, edges = [[0,1],[1,2],[0,2]], succProb = [0.5,0.5,0.2], 
     *        start = 0, end = 2
     * Output: 0.25000
     * Explanation: 
     * - Path 0 -> 2 has probability = 0.2
     * - Path 0 -> 1 -> 2 has probability = 0.5 * 0.5 = 0.25
     * The maximum probability path is 0.25.
     *
     * ----------------------------------------------------------------------------
     * APPROACH: Max-Heap Dijkstra (Multiplicative Greedy Traversal)
     * 
     * 1. Core Transformation:
     *    Standard Dijkstra finds the shortest path by minimizing additive 
     *    distances (dist[u] + weight). 
     *    Here, edge weights represent probabilities in the range [0.0, 1.0], 
     *    and path values are computed multiplicatively (p1 * p2 * ...).
     *    Multiplying by values <= 1.0 strictly decreases or maintains the probability, 
     *    meaning the greedy optimal substructure of Dijkstra still holds!
     *
     * 2. Key Differences from Standard Dijkstra:
     *    - Max-Heap PriorityQueue: Ordered descending by probability 
     *      (`Double.compare(b.prob, a.prob)`).
     *    - Neutral Element: Start node begins with probability 1.0 (multiplicative identity), 
     *      while all other nodes are initialized to 0.0.
     *    - Transition Condition: Relax an edge whenever `p * edgeProb > maxProb[v]`.
     *
     * 3. Pruning Stale States:
     *    `if (maxProb[u] > p) continue;` ensures that if we have already found a better 
     *    route to `u` since this state was pushed to the queue, we discard it immediately.
     *
     * 4. Complexity:
     *    - Time Complexity:  O(E + V * log V), where V = n and E = edges.length.
     *    - Space Complexity: O(V + E) for the adjacency list graph and probability table.
     * ============================================================================
     */
    public double maxProbability(int n, int[][] edges, double[] succProb, int start_node, int end_node) {

        // Track maximum probability of reaching each node from start_node
        double maxProb[] = new double[n];
        Arrays.fill(maxProb, 0.0d);

        // Multiplicative identity: probability of being at start_node initially is 1.0 (100%)
        maxProb[start_node] = 1.0d;

        // Adjacency list representation of the graph
        List<Pair> graph[] = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Build the undirected graph
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            double p = succProb[i];

            graph[u].add(new Pair(v, p));
            graph[v].add(new Pair(u, p));
        }

        // Max-Heap PriorityQueue: always process the node with the highest accumulated probability next
        Queue<Pair> pq = new PriorityQueue<>((a, b) -> Double.compare(b.prob, a.prob));

        // Push source node into the priority queue
        pq.add(new Pair(start_node, 1.0d));

        while (!pq.isEmpty()) {
            Pair curr = pq.poll();

            int u = curr.node;
            double p = curr.prob;

            // Early exit optimization: first time destination is popped from a max-heap,
            // its maximum probability is guaranteed to be finalized.
            if (u == end_node) {
                return p;
            }

            // Stale entry check: discard if a strictly better path to u has already been found
            if (maxProb[u] > p) {
                continue;
            }

            // Explore all adjacent neighbors
            for (Pair child : graph[u]) {
                int v = child.node;
                double cp = child.prob;

                // Path probability is the product of accumulated probability and edge probability
                double nextProb = p * cp;

                // Relaxation step: if this path yields a strictly higher probability, update and push to PQ
                if (nextProb > maxProb[v]) {
                    maxProb[v] = nextProb;
                    pq.add(new Pair(v, nextProb));
                }
            }
        }

        // Return the best probability reaching end_node (or 0.0 if unreachable)
        return maxProb[end_node];
    }

    /**
     * Helper pair class to hold a graph node and its corresponding probability.
     */
    private static class Pair {
        int node;
        double prob;

        Pair(int node, double prob) {
            this.node = node;
            this.prob = prob;
        }
    }
}
