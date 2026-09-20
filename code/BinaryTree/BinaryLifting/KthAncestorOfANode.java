package BinaryTree.BinaryLifting;
    import java.util.Arrays;

public class KthAncestorOfANode {

    /*
    * ============================================================================
    * LEETCODE 1483: Kth Ancestor of a Tree Node
    * ============================================================================
    * Link: https://leetcode.com/problems/kth-ancestor-of-a-tree-node/
    *
    * DESCRIPTION:
    * You are given a tree with n nodes numbered from 0 to n - 1 in the form of a 
    * parent array parent where parent[i] is the parent of the i-th node. The root 
    * of the tree is node 0. Find the k-th ancestor of a given node.
    *
    * The k-th ancestor of a tree node is the k-th node in the path that leads from 
    * that node to the root of the tree.
    *
    * Implement the TreeAncestor class:
    * - TreeAncestor(int n, int[] parent): Initializes the object with the number 
    *   of nodes and the parent array.
    * - int getKthAncestor(int node, int k): Returns the k-th ancestor of the given 
    *   node. If no such ancestor exists, return -1.
    *
    * ----------------------------------------------------------------------------
    * EXAMPLE:
    * Input:
    * ["TreeAncestor", "getKthAncestor", "getKthAncestor", "getKthAncestor"]
    * [[7, [-1, 0, 0, 1, 1, 2, 2]], [3, 1], [5, 2], [6, 3]]
    * Output: [null, 1, 0, -1]
    * Explanation:
    * TreeAncestor treeAncestor = new TreeAncestor(7, [-1, 0, 0, 1, 1, 2, 2]);
    * treeAncestor.getKthAncestor(3, 1); // returns 1 (1st ancestor of 3 is 1)
    * treeAncestor.getKthAncestor(5, 2); // returns 0 (2nd ancestor of 5 is 0)
    * treeAncestor.getKthAncestor(6, 3); // returns -1 (no 3rd ancestor exists)
    *
    * ----------------------------------------------------------------------------
    * APPROACH: Binary Lifting (Powers of Two Precomputation)
    *
    * 1. Intuition:
    *    - A naive approach of stepping node = parent[node] runs in O(K) per query.
    *      With K <= 5 * 10^4 and 5 * 10^4 queries, O(Q * K) TLEs.
    *    - Any integer k can be decomposed into binary components:
    *        e.g., k = 13 = 8 + 4 + 1 = 2^3 + 2^2 + 2^0.
    *    - Instead of jumping 1 step 13 times, we can jump 2^0 step, then 2^2 steps, 
    *      then 2^3 steps (only 3 jumps).
    *
    * 2. Precomputation (DP Table):
    *    - ancestor[node][log] represents the (2^log)-th ancestor of node.
    *    - Base case: ancestor[node][0] = parent[node] (the 2^0 = 1st ancestor).
    *    - Recurrence relation:
    *        ancestor[node][log] = ancestor[ ancestor[node][log - 1] ][log - 1]
    *      (To jump 2^log steps, jump 2^(log-1) steps, then jump another 2^(log-1) steps).
    *    - If intermediate node is -1 (out of bounds), the ancestor is -1.
    *
    * 3. Querying:
    *    - Inspect each bit `log` of integer `k`:
    *      If the log-th bit is set ((k & (1 << log)) != 0), lift node by:
    *        node = ancestor[node][log]
    *    - If at any point node becomes -1, return -1 immediately.
    *
    * 4. Complexity:
    *    - Preprocessing Time:  O(N * log N) to construct the ancestor matrix.
    *    - Query Time:          O(log K) per getKthAncestor call (~16 bit checks).
    *    - Space Complexity:    O(N * log N) auxiliary space for the DP table.
    * ============================================================================
    */
    private static class TreeAncestor {

        // ancestor[node][log] stores the (2^log)-th ancestor of `node`
        private final int[][] ancestor;
        private int maxAncestors;

        public TreeAncestor(int n, int[] parent) {

            // Determine the number of powers of two needed such that 2^maxAncestors > n
            while ((1 << maxAncestors) <= n) {
                maxAncestors++;
            }

            ancestor = new int[n][maxAncestors];

            // Fill table with -1 to indicate nonexistent ancestors (e.g., above the root)
            for (int[] row : ancestor) {
                Arrays.fill(row, -1);
            }

            // Base case: 2^0-th ancestor is the direct parent
            for (int i = 0; i < n; i++) {
                ancestor[i][0] = parent[i];
            }

            // Build binary lifting table: outer loop over bit powers, inner loop over nodes
            for (int log = 1; log < maxAncestors; log++) {
                for (int i = 0; i < n; i++) {
                    int intermediateAncestor = ancestor[i][log - 1];

                    // If intermediate ancestor exists, jump another 2^(log - 1) steps from it
                    if (intermediateAncestor != -1) {
                        ancestor[i][log] = ancestor[intermediateAncestor][log - 1];
                    }
                }
            }
        }

        public int getKthAncestor(int node, int k) {

            // Inspect each bit of k and jump corresponding powers of 2
            for (int log = 0; log < maxAncestors; log++) {
                if ((k & (1 << log)) != 0) {
                    node = ancestor[node][log];

                    // If we stepped above the tree root, no ancestor exists
                    if (node == -1) {
                        return -1;
                    }
                }
            }

            return node;
        }
    }

    /**
     * Your TreeAncestor object will be instantiated and called as such:
     * TreeAncestor obj = new TreeAncestor(n, parent);
     * int param_1 = obj.getKthAncestor(node, k);
     */

}
