package Google.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class CheapestFlightsWithinKStops {

    /*
    https://leetcode.com/problems/cheapest-flights-within-k-stops/
    
    There are n cities connected by some number of flights. 
    You are given an array flights where flights[i] = [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with cost pricei.
    
    You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops. If there is no such route, return -1.
    
    Input: n = 4, flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]], src = 0, dst = 3, k = 1
    Output: 700
    Explanation:
    The graph is shown above.
    The optimal path with at most 1 stop from city 0 to 3 is marked in red and has cost 100 + 600 = 700.
    Note that the path through cities [0,1,2,3] is cheaper but is invalid because it uses 2 stops.
    
    Approach : Perform Dijkstra's on basis of number of stops and then on weights
    
    
    */
    
    static final int INF = Integer.MAX_VALUE / 4;
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {

        List<int[]> adj[] = new ArrayList[n];
        // construct the graph
        for (int row[] : flights) {
            int u = row[0];
            int v = row[1];
            int w = row[2];

            if (adj[u] == null) {
                adj[u] = new ArrayList<>();
            }

            adj[u].add(new int[]{v, w});
        }

        int distFromSrc[] = dijkstras(src, adj, k + 1);

        return distFromSrc[dst] == INF ? -1 : distFromSrc[dst];

    }
    
    private int[] dijkstras(int src, List<int[]> adj[], int k) {
        int n = adj.length;

        // fill min dist with infinity
        int distFromSrc[] = new int[n];
        Arrays.fill(distFromSrc, INF);

        // create priority queue ans keep order on basis of stops and then weights
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> {
                if(a[2] != b[2]) {
                    return Integer.compare(a[2], b[2]);
                } else {
                    return Integer.compare(a[1], b[1]);
                }
            }
        );

        distFromSrc[src] = 0;
        pq.add(new int[]{src, 0, 0});


        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            int w = curr[1];
            int stops = curr[2];

            // if there are no outgoing nodes from curr, ignore
            if (adj[u] == null) {
                continue;
            }

            List<int[]> children = adj[u];
            for (int[] child : children) {

                int childId = child[0];
                int childWeight = child[1];

                // check weight of child + w is less than min dist of child
                // and total stops to reach the child is within limit
                // if both are ok, relax the edge
                if (childWeight + w < distFromSrc[childId] && stops + 1 <= k) {
                    distFromSrc[childId] = childWeight + w;
                    pq.add(new int[]{childId, childWeight + w, stops + 1});
                }
            }
        }
        
        return distFromSrc;

    }
}
