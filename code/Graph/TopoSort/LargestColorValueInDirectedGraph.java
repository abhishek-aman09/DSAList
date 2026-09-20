package Graph.TopoSort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import java.util.*;

/*
 * ============================================================================
 * LEETCODE 1857: Largest Color Value in a Directed Graph
 * ============================================================================
 * Link: https://leetcode.com/problems/largest-color-value-in-a-directed-graph/
 *
 * DESCRIPTION:
 * There is a directed graph of n colored nodes and m edges. The nodes are 
 * numbered from 0 to n - 1. You are given a string colors where colors[i] is a 
 * lowercase English letter representing the color of the i-th node.
 *
 * You are also given a 2D array edges where edges[j] = [uj, vj] indicates that 
 * there is a directed edge from node uj to node vj.
 *
 * A valid path that starts at some node and ends at another node is a sequence 
 * of directed edges. The color value of the path is the number of occurrences of 
 * the most frequently occurring color along the path.
 *
 * Return the largest color value of any valid path in the graph, or -1 if the 
 * graph contains a cycle.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * Input: colors = "abaca", edges = [[0,1],[0,2],[2,3],[3,4]]
 * Output: 3
 * Explanation: The path 0 -> 2 -> 3 -> 4 has node colors "a" -> "a" -> "c" -> "a".
 * The color 'a' occurs 3 times, which is the maximum.
 *
 * ----------------------------------------------------------------------------
 * APPROACH: Kahn's Algorithm (Topological Sort) + Dynamic Programming on DAG
 *
 * 1. Cycle Detection:
 *    - If the graph has a directed cycle, we can loop indefinitely and make 
 *      the frequency of colors infinite. The problem requires returning -1.
 *    - Kahn’s indegree-based BFS naturally tracks this: if the number of visited 
 *      nodes `count != n`, the graph contains a cycle.
 *
 * 2. DP State Definition:
 *    - Let `colorFreqInNode[u][c]` be the maximum frequency of color `c` ('a' - 'z') 
 *      along ANY valid directed path that ends at node `u`.
 *
 * 3. Avoiding Path Convergence Overcounting:
 *    - If two paths converge at `child` (e.g., parent1 -> child and parent2 -> child),
 *      we must NOT sum them (`+=`). We only want the strongest single path.
 *    - Hence, we take the maximum across incoming paths:
 *        colorFreqInNode[child][c] = max(colorFreqInNode[child][c], colorFreqInNode[parent][c])
 *
 * 4. DP Transition & Execution Flow:
 *    - Poll `node` from queue (which guarantees all paths leading into `node` are finalized).
 *    - Increment the count for this node's own color:
 *        colorFreqInNode[node][color]++;
 *    - Update the global maximum with `colorFreqInNode[node]` (every node can potentially 
 *      be the end of the optimal path).
 *    - Relax neighbors: for each child, push the finalized DP state via `Math.max`.
 *    - Decrement indegree; when indegree reaches 0, push `child` to queue.
 *
 * 5. Complexity:
 *    - Time Complexity:  O(26 * (V + E)) = O(V + E)
 *                        - Graph & indegree build: O(V + E).
 *                        - Kahn's queue processes each node once: O(V).
 *                        - For each edge (u -> v), we copy/max 26 color values: O(26 * E).
 *                        With V, E <= 10^5, operations ~ 2.6 * 10^6 (< 70ms in Java).
 *    - Space Complexity: O(26 * V + E) = O(V + E)
 *                        - DP table: n x 26 integers.
 *                        - Adjacency list and indegree: O(V + E).
 * ============================================================================
 */

public class LargestColorValueInDirectedGraph {

    public int largestPathValue(String colors, int[][] edges) {
        int n = colors.length();

        // colorFreqInNode[u][c] = max count of color c on any single path ending at u
        int[][] colorFreqInNode = new int[n][26];

        int[] indegree = new int[n];
        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Build directed adjacency list and compute indegrees
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            indegree[v]++;
            graph[u].add(v);
        }

        // Seed queue with all source nodes (indegree == 0)
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        int maxPathValue = 0;
        int visitedNodesCount = 0;

        // Process DAG in topological order
        while (!q.isEmpty()) {
            int node = q.poll();
            visitedNodesCount++;

            // Include current node's own color in paths ending at `node`
            int nodeColor = colors.charAt(node) - 'a';
            colorFreqInNode[node][nodeColor]++;

            // Update overall maximum with all colors along paths ending at `node`
            for (int freq : colorFreqInNode[node]) {
                maxPathValue = Math.max(maxPathValue, freq);
            }

            // Propagate path maxima downstream to children
            for (int child : graph[node]) {
                updateChildColors(node, child, colorFreqInNode);

                indegree[child]--;
                if (indegree[child] == 0) {
                    q.add(child);
                }
            }
        }

        // If not all nodes were visited, a directed cycle exists
        if (visitedNodesCount != n) {
            return -1;
        }

        return maxPathValue;
    }

    /**
     * Propagates max frequencies from parent to child across all 26 colors.
     * Math.max guarantees we track the optimal single path, avoiding double-counting
     * when paths diverge and recombine.
     */
    private void updateChildColors(int parent, int child, int[][] colorFreqInNode) {
        for (int i = 0; i < 26; i++) {
            colorFreqInNode[child][i] = Math.max(colorFreqInNode[child][i], colorFreqInNode[parent][i]);
        }
    }
}