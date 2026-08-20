package Google.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class MinWeightedSubgraphWithReqPath {

    /*
    https://leetcode.com/problems/minimum-weighted-subgraph-with-the-required-paths/description/
    
    You are given an integer n denoting the number of nodes of a weighted directed graph. The nodes are numbered from 0 to n - 1.
    You are also given a 2D integer array edges where edges[i] = [fromi, toi, weighti] denotes that there exists a directed edge from fromi to toi with weight weighti.
    Lastly, you are given three distinct integers src1, src2, and dest denoting three distinct nodes of the graph.
    Return the minimum weight of a subgraph of the graph such that it is possible to reach dest from both src1 and src2 via a set of edges of this subgraph. In case such a subgraph does not exist, return -1.
    A subgraph is a graph whose vertices and edges are subsets of the original graph. The weight of a subgraph is the sum of weights of its constituent edges.
    
    Approach : Create two graph, one normal, other reversed on edges.
    perform diajkstra's on graph A with src1 and src2, perform diajkstra's on reverse graph with source as dest.
    these three ararys will give you min dist of every node from src1, src2 and dest. for each i check the min(1 + 2 + 3)
    */
    
    public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest) {

        final long MAX = Long.MAX_VALUE / 3;
        List<Pair> graph[] = new ArrayList[n]; // normal graph
        List<Pair> revGraph[] = new ArrayList[n];// reverse graph

        for(int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            revGraph[i] = new ArrayList<>();
        }

        for(int edge[] : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];

            graph[u].add(new Pair(v, w));
            revGraph[v].add(new Pair(u, w));
        }

        long[] distFromSrc1 = new long[n];
        long[] distFromSrc2 = new long[n];
        long[] distFromDest = new long[n];

        Arrays.fill(distFromSrc1, MAX);

        shortestPathFromSrc(graph, src1, distFromSrc1); // diajkstra's with src1

        if(distFromSrc1[dest] == MAX) {
            return -1;
        }


        Arrays.fill(distFromSrc2, MAX);

        shortestPathFromSrc(graph, src2, distFromSrc2); // diajkstra's with src2

        if(distFromSrc2[dest] == MAX) {
            return -1;
        }

        Arrays.fill(distFromDest, MAX);
        shortestPathFromSrc(revGraph, dest, distFromDest); // diajkstra's with dest on reverse graph
       
        long ans = Long.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            ans = Long.min(ans, (distFromDest[i] + distFromSrc1[i] + distFromSrc2[i])); // take the min
        }

        return ans;
        
    }

    // diajkstra's block
    private void shortestPathFromSrc(List<Pair> graph[], int src, long distFromSrc[]) {

        Queue<Pair> bfsQueue = new PriorityQueue<>((a, b) -> Long.compare(a.weight, b.weight));

        bfsQueue.add(new Pair(src, 0));
        distFromSrc[src] = 0;

        while(!bfsQueue.isEmpty()) {
            Pair curr = bfsQueue.poll();

            if(graph[curr.node].isEmpty() || curr.weight > distFromSrc[curr.node]) {
                continue;
            }

            for(Pair child : graph[curr.node]) {

                if(child.weight + curr.weight < distFromSrc[child.node]) {
                    distFromSrc[child.node] = child.weight + curr.weight;
                    bfsQueue.add(new Pair(child.node, child.weight + curr.weight));
                }
            }
        }
    }


    private static class Pair {
        int node;
        long weight;

        Pair(int node, long weight) {
            this.node = node;
            this.weight = weight;
        }
    }
    
}
