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
    
    Approach : DSU Based
    There can be three scenarios, either we have (refer image with same name for reference)
    1. cycle in a graph (all node with one parent)
    Sol : we perform DSU, if parent of any two nodes are already same, we return that edge as it forms a loop
    
    2. one node having two parents without cycle
    Sol : in the case, as a node has two parents, it is connected in two ways, we can remove the edge that comes later
    finding that two edges are essentialy taken care in starting of the method.
    
    3. one node having two parents with cycle
    Sol. Kind of a tricky one, we simply cannot remove the later edge as the former edge could be part of cycle.
    To deal with that, we get two candidates from scenario 2, then we check which of these edges is part of loop like
    in scenario 1.
    */
    
    public int[] findRedundantDirectedConnection(int[][] edges) {

        // this part essentialy finds candidates for nodes with two parent
        int n = edges.length;

        int[] parent = new int[n + 1]; // parent array

        int[] candidateA = null;
        int[] candidateB = null;

        for (int[] edge : edges) {
            int par = edge[0];
            int child = edge[1];

            if (parent[child] != 0) { // if parent of any node is already initialized
                candidateA = new int[] { parent[child], child }; // we take the first pair as first candidate
                candidateB = edge; // we take the current as second candidate
            } else {
                parent[child] = par;
            }
        }

        for (int i = 0; i <= n; i++) {
            parent[i] = i; // reset parent to its node value
        }

        // This blocks essentially checks for cyclic nodes

        for (int[] edge : edges) { 
            if (edge == candidateB) { // why? for scenarion 2 and 3, we will assume candidate B is not the redundant node
                continue;
            }

            int par = edge[0];
            int child = edge[1];

            // get parent of each node
            int parP = findByPathCompression(par, parent);
            int parC = findByPathCompression(child, parent);

            if (parP == parC) { // if parents are same, we have a cycle
                if (candidateA != null) { // (Scenario 3) if we have a node with two parent and contributing to cycle, return it
                    return candidateA;
                } else { // (Scenario 1) if candidate A is null, then we only have a cycle and no two parents, return the curr edge
                    return edge;
                }
            } else {
                parent[parC] = parP;
            }
        }

        return candidateB; // return candidate B as per Scenario 2 and 3

    }
    
    private int findByPathCompression(int node, int parent[]) {
        if (node == parent[node]) {
            return node;
        }

        return parent[node] = findByPathCompression(parent[node], parent);
    }
}
