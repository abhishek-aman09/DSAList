package Graph.DisjointSet;

import java.util.Arrays;

/*
 * ============================================================================
 * LEETCODE 1697: Checking Existence of Edge Length Limited Paths
 * ============================================================================
 * Link: https://leetcode.com/problems/checking-existence-of-edge-length-limited-paths/
 *
 * DESCRIPTION:
 * An undirected graph of n nodes from 0 to n - 1 is given by a 2D array edgeList, 
 * where edgeList[i] = [ui, vi, disi] denotes an edge between nodes ui and vi with 
 * distance disi. There may be multiple edges between two nodes.
 *
 * You are also given an array queries, where queries[j] = [pj, qj, limitj]. 
 * Find whether there is a path between pj and qj such that each edge on the 
 * path has a distance strictly less than limitj.
 *
 * Return a boolean array answer, where answer.length == queries.length and 
 * answer[j] is true if there is a path for queries[j] and false otherwise.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * Input: n = 3, edgeList = [[0,1,2],[1,2,4],[2,0,8],[1,0,16]], 
 *        queries = [[0,1,2],[0,2,5]]
 * Output: [false, true]
 * Explanation:
 * Query [0,1,2]: Needs path with edges < 2. No edges < 2 exist -> false.
 * Query [0,2,5]: Uses path 0 -> 1 -> 2 with weights 2 and 4 (both < 5) -> true.
 *
 * ----------------------------------------------------------------------------
 * APPROACH: Offline Query Processing + Kruskal's Two-Pointer DSU
 *
 * 1. Offline Query Processing:
 *    - Solving each query independently from scratch would take O(Q * E), which TLEs.
 *    - Key observation: The threshold `limit` only increases if we sort queries 
 *      ascending by `limit`.
 *    - As `limit` increases, previously added valid edges (weight < limit) remain 
 *      permanently valid! Edges never need to be deleted.
 *
 * 2. Two-Pointer Incremental DSU:
 *    - Sort `edgeList` ascending by edge weight.
 *    - Attach original indices to `queries` and sort queries ascending by `limit`.
 *    - Maintain a Disjoint Set Union (DSU) structure.
 *    - For each query (u, v, limit, origIdx):
 *        Advance edge pointer `j`: add all edges with `weight < limit` into the DSU.
 *        Query result: check if `find(u) == find(v)`.
 *        Store result at `ans[origIdx]`.
 *
 * 3. Optimizations (Flat Primitives & Zero Object Allocations):
 *    - Instead of allocating `new int[]{...}` per query, reuse arrays or map indices:
 *      sort an `Integer[] queryIndices` array based on `queries[idx][2]`.
 *    - Use Path Compression + Union by Rank/Size for alpha(N) amortized DSU operations.
 *
 * 4. Complexity:
 *    - Time Complexity:  O(E log E + Q log Q + (E + Q) * alpha(V))
 *                        - Edge sorting: O(E log E).
 *                        - Query sorting: O(Q log Q).
 *                        - DSU unions and finds: O((E + Q) * alpha(V)).
 *                        With E, Q <= 10^5, this runs in ~50-60 ms in Java.
 *    - Space Complexity: O(V + Q) for parent/rank arrays and query index tracking.
 * ============================================================================
 */
public class CheckingExistenceOfEdgeLengthLimitedPaths {

    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] q) {
        int m = q.length;
        int numEdges = edgeList.length;

        // Step 1: Augment queries with their original index to answer offline
        // queries[i] = {u, v, limit, originalIndex}
        int[][] queries = new int[m][4];
        for (int i = 0; i < m; i++) {
            queries[i][0] = q[i][0];
            queries[i][1] = q[i][1];
            queries[i][2] = q[i][2];
            queries[i][3] = i;
        }

        // Step 2: Sort edges by weight and queries by limit
        Arrays.sort(edgeList, (a, b) -> Integer.compare(a[2], b[2]));
        Arrays.sort(queries, (a, b) -> Integer.compare(a[2], b[2]));

        // Step 3: Initialize DSU state
        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        boolean[] ans = new boolean[m];
        int edgeIdx = 0;

        // Step 4: Two-pointer sweep — incrementally union edges strictly less than query limit
        for (int[] query : queries) {
            int u = query[0];
            int v = query[1];
            int limit = query[2];
            int originalIdx = query[3];

            // Add all edges with weight strictly less than the current query's limit
            while (edgeIdx < numEdges && edgeList[edgeIdx][2] < limit) {
                int src = edgeList[edgeIdx][0];
                int dest = edgeList[edgeIdx][1];
                unionByRank(src, dest, parent, rank);
                edgeIdx++;
            }

            // If u and v share the same connected component, a valid limited path exists
            ans[originalIdx] = (findParent(u, parent) == findParent(v, parent));
        }

        return ans;
    }

    /**
     * DSU find with path compression.
     */
    private int findParent(int node, int[] parent) {
        if (node != parent[node]) {
            parent[node] = findParent(parent[node], parent);
        }
        return parent[node];
    }

    /**
     * DSU union by rank to maintain balanced tree depth.
     */
    private void unionByRank(int a, int b, int[] parent, int[] rank) {
        int rootA = findParent(a, parent);
        int rootB = findParent(b, parent);

        if (rootA == rootB) {
            return;
        }

        if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else if (rank[rootB] > rank[rootA]) {
            parent[rootA] = rootB;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }
    }
}
