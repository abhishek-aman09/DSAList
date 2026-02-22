package Graph.Dijkstras;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class CheapestFlightsWithinKStops {
    
    static final int INF = Integer.MAX_VALUE / 4;
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {

        List<Node> adj[] = new ArrayList[n];

        for (int row[] : flights) {
            int u = row[0];
            int v = row[1];
            int w = row[2];

            if (adj[u] == null) {
                adj[u] = new ArrayList<>();
            }

            adj[u].add(new Node(v, w));
        }

        int distFromSrc[] = dijkstras(src, adj, k);

        return distFromSrc[dst] == INF ? -1 : distFromSrc[dst];

    }
    
    private int[] dijkstras(int src, List<Node> adj[], int k) {
        int n = adj.length;

        int distFromSrc[] = new int[n];
        Arrays.fill(distFromSrc, INF);

        PriorityQueue<Node> pq = new PriorityQueue<>(
            Comparator.comparingInt(node -> node.weight)
        );

        distFromSrc[src] = 0;
        pq.add(new Node(src, 0));
        pq.add(new Node(-1, -1));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();

            if (k == 0) {
                break;
            }

            int u = curr.id;
            int w = curr.weight;

            if (u == -1) {
                k--;
                if (!pq.isEmpty()) {
                    pq.add(new Node(-1, -1));
                }
                continue;
            }

            if (adj[u] == null) {
                continue;
            }

            List<Node> children = adj[u];
            for (Node child : children) {

                int childId = child.id;
                int childWeight = child.weight;

                if (childWeight + w < distFromSrc[childId] && k > 0) {
                    distFromSrc[childId] = childWeight + w;
                    pq.add(new Node(childId, childWeight + w));
                }
            }
        }
        
        return distFromSrc;

    }
    
    private static class Node {
        int id;
        int weight;

        Node(int id, int weight) {
            this.id = id;
            this.weight = weight;
        }
    }
}
