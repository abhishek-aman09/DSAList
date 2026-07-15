package Google.Graph;

public class RedundantConnectionII {

    /*
    https://leetcode.com/problems/redundant-connection-ii/
    
    In this problem, a rooted tree is a directed graph such that, there is exactly one node (the root) 
    for which all other nodes are descendants of this node, plus every node has exactly one parent, 
    except for the root node which has no parents.
    
    The given input is a directed graph that started as a rooted tree with n nodes (with distinct values from 1 to n),
    with one additional directed edge added. The added edge has two different vertices chosen from 1 to n, 
    and was not an edge that already existed.
    
    The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [ui, vi] 
    that represents a directed edge connecting nodes ui and vi, where ui is a parent of child vi.
    
    Return an edge that can be removed so that the resulting graph is a rooted tree of n nodes. 
    If there are multiple answers, return the answer that occurs last in the given 2D-array.
    
    
    Input: edges = [[1,2],[1,3],[2,3]]
    Output: [2,3]
    
    
    */
    
    public int[] findRedundantDirectedConnection(int[][] edges) {

        int n = edges.length;

        int[] parent = new int[n + 1];

        int[] candidateA = null;
        int[] candidateB = null;

        for (int[] edge : edges) {
            int par = edge[0];
            int child = edge[1];

            if (parent[child] != 0) {
                candidateA = new int[] { parent[child], child };
                candidateB = edge;
            } else {
                parent[child] = par;
            }
        }

        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }

        for (int[] edge : edges) {
            if (edge == candidateB) {
                continue;
            }

            int par = edge[0];
            int child = edge[1];

            int parP = findByPathCompression(par, parent);
            int parC = findByPathCompression(child, parent);

            if (parP == parC) {
                if (candidateA != null) {
                    return candidateA;
                } else {
                    return edge;
                }
            } else {
                parent[parC] = parP;
            }
        }

        return candidateB;

    }
    
    private int findByPathCompression(int node, int parent[]) {
        if (node == parent[node]) {
            return node;
        }

        return parent[node] = findByPathCompression(parent[node], parent);
    }
}
