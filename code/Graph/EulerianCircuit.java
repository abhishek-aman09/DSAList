package Graph;

import java.util.List;

public class EulerianCircuit {
    
    public int isEulerCircuit(int v, List<Integer>[] adj) {

        int connectedComponents = 0;
        int vis[] = new int[v];
        int degree[] = new int[v];

        for (int i = 0; i < v; i++) {
            if (vis[i] == 0) {
                if (connectedComponents == 0) {
                    connectedComponents = 1;
                    dfs(i, adj, vis, degree);
                } else {
                    // assuming if a graph has more than one connected component
                    // with more than one edges, it connot have eulerian path or circuit.
                    // we should start only from one node and must traverse
                    // all the edges to have a path.
                    return 0;
                }
            }
        }

        int countOfOdds = 0;

        for (int i = 0; i < v; i++) {
            if (degree[i] % 2 == 1) {
                countOfOdds++;
            }
        }
        // condition for eulerian circuit
        if(countOfOdds == 0) {
            return 2;
        }
        // conditon for eulerian path
        if(countOfOdds == 2) {
            return 1;
        }

        return 0;

    }
    
    void dfs(int node, List<Integer>[] adj, int vis[], int degree[]) {

        if (vis[node] == 1) {
            return;
        }

        vis[node] = 1;

        for (int child : adj[node]) {
            if (vis[child] == 0) {
                dfs(child, adj, vis, degree);
            }

            if (child != node) {
                degree[node]++; 
            }
            
        }

    }

}


/*
 * 
 * 
 * Conditions for Eulerian Path and circuit to exist
 * 
 * _________________________________________________________________________________
 * | Graph Type |         Eulerian Circuit         |       Eulerian Path            |
 * ---------------------------------------------------------------------------------
 * | Undirected    |  Every vertex should have     | Either every vertex has        |
 * |               |  even degree. (except self    | even degree or exactly         |
 * |               |  loop)                        | two vertices have odd          |
 * |               |                               | degree.                        |
 * ----------------------------------------------------------------------------------
 * | Directed      |  Every vertex should have     | At most one vertex should      |
 * |               |  equal indegree and outdegree | have outdegree - indegree = 1  |
 * |               |                               | and indegree - outdegree = 1   |
 * |               |                               | and all other node should have |
 * |               |                               | equal indegree and outdegree.  |
 * ----------------------------------------------------------------------------------
 * 
 * 
 * 
 * 
 */