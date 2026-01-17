package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DijkstrasMinDistFromSource {
    // Function to find the shortest path from a source node to all other nodes


    public static int[] shortestPath(ArrayList<ArrayList<Integer>> adj, int src) {
        int V = adj.size();
        int maxInt = Integer.MAX_VALUE - 1;
        int[] minDistFromSource = new int[V];
        boolean[] isVisited = new boolean[V];

        Arrays.fill(isVisited, false);
        Arrays.fill(minDistFromSource, maxInt);

        PriorityQueue<ChildWithWeight> pq = new PriorityQueue<>(new MyComparator());

        pq.add(new ChildWithWeight(src, 0));
        minDistFromSource[src] = 0;

        while (pq.peek() != null) {

            ChildWithWeight currNode = pq.poll();

            isVisited[currNode.node] = true;
            minDistFromSource[currNode.node] = Math.min(minDistFromSource[currNode.node], currNode.weight);

            for (int child : adj.get(currNode.node)) {
                if (!isVisited[child] && minDistFromSource[child] > currNode.weight + 1) {
                    ChildWithWeight childObj = new ChildWithWeight(child, currNode.weight + 1);
                    pq.add(childObj);
                }
            }

        }

        for (int i = 0; i < V; i++)
            if (minDistFromSource[i] == maxInt)
                minDistFromSource[i] = -1;
        
        return minDistFromSource;
    }

    public static class ChildWithWeight {
        private int node;
        private int weight;

        public ChildWithWeight(final int node, final int weight) {
            this.node = node;
            this.weight = weight;
        }
    }

    public static class MyComparator implements Comparator<ChildWithWeight> {

        @Override
        public int compare(ChildWithWeight o1, ChildWithWeight o2) {

            if (o1.weight < o2.weight)
                return -1;

            return 1;
   
        }

    }
    
    public static void main(String[] args) {
    }

}
