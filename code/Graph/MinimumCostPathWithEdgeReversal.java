package Graph;

import java.util.ArrayList;
import java.util.List;

public class MinimumCostPathWithEdgeReversal {
    
    public int minCost(int n, int[][] edges) {

        int m = edges.length;

        // Array of List of 2-index array to store v and w as pair
        List<int[]>[] adj = (List<int[]>[]) new ArrayList[n];

        //  Move all v and w as pair for u.
        // also add a back edge from v to u with weight 2 * w.
        for (int i = 0; i < m; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int w = edges[i][2];

            if (adj[u] == null) {
                adj[u] = new ArrayList<>();
            }

            if (adj[v] == null) {
                adj[v] = new ArrayList<>();
            }

            adj[u].add(new int[] { v, w });
            adj[v].add(new int[] { u, 2 * w });

        }

        

    }

}
