package Graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PrimsMST {
    
// Prims algorithm used a priority queue and a visited array
// Priority queue on basis of weight

    static int spanningTree(int V, int E, List<List<int[]>> adj) {

        int minWeightMST = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>(new MyComparator());

        boolean[] isVisited = new boolean[V];
        Arrays.fill(isVisited, false);

        pq.add(new Edge(0, -1, 0));

        while (pq.peek() != null) {

            Edge currEdge = pq.poll();

            if (!isVisited[currEdge.u]) {
                isVisited[currEdge.u] = true;
                if (currEdge.parent != -1) {
                    minWeightMST += currEdge.wt;
                }
                    
            }
            
            for (int[] childList : adj.get(currEdge.u)) {
                int child = childList[0];
                int childWeight = childList[1];

                if (!isVisited[child]) {
                    pq.add(new Edge(child, currEdge.u, childWeight));
                }
            }
            
        }



        return minWeightMST;

    }

    private static class MyComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            if (o1.wt < o2.wt)
                return -1;
            return 1;
        }    
    }

    private static class Edge {
        int u;
        int parent;
        int wt;

        Edge(int src, int des, int wt) {
            this.u = src;
            this.parent = des;
            this.wt = wt;
        }
    }


}
