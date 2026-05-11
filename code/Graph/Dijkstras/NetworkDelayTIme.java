package Graph.Dijkstras;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class NetworkDelayTIme {

    // https://leetcode.com/problems/network-delay-time/description/
    /*
    You are given a network of n nodes, labeled from 1 to n. 
    You are also given times, a list of travel times as directed edges 
    times[i] = (ui, vi, wi), where ui is the source node, 
    vi is the target node, and wi is the time it takes for a signal to travel from source to target.
    
    We will send a signal from a given node k. 
    Return the minimum time it takes for all the n nodes to receive the signal. 
    If it is impossible for all the n nodes to receive the signal, return -1.
    
    Approach : Run Dijkstra with k as source and return the highest number.
    */

    static final int INF = Integer.MAX_VALUE / 4;
    public int networkDelayTime(int[][] times, int n, int k) {

        List<Node> adj[] = new ArrayList[n + 1];

        for (int row[] : times) {
            int u = row[0];
            int v = row[1];
            int w = row[2];

            if (adj[u] == null) {
                adj[u] = new ArrayList<>();
            }

            adj[u].add(new Node(v, w));
        }

        int dis[] = dijkstras(k, adj);
        int ans = 0;

        for (int i = 1; i <= n; i++) {
            if (dis[i] > ans) {
                ans = dis[i];
            }

            if (dis[i] == INF) {
                return -1;
            }
        }

        return ans;
    }
    
    private int[] dijkstras(int src, List<Node> adj[]) {
        int n = adj.length;

        int distFromSrc[] = new int[n];
        Arrays.fill(distFromSrc, INF);

        PriorityQueue<Node> pq = new PriorityQueue<>(
            Comparator.comparingInt(node -> node.weight)
        );

        distFromSrc[src] = 0;
        pq.add(new Node(src, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            int u = curr.id;
            int w = curr.weight;

            if (adj[u] == null) {
                continue;
            }

            List<Node> children = adj[u];
            for (Node child : children) {

                int childId = child.id;
                int childWeight = child.weight;

                if (childWeight + w < distFromSrc[childId]) {
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
