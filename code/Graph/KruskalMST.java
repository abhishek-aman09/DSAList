package Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class KruskalMST {
    
    static long kruskalDSU(ArrayList<Edge> adj, int n, int m) {

        long mstWeight = 0;
        int numOfEdge = 0;

        DisjointSet dsuSet = new DisjointSet(n);

        Collections.sort(adj, new MyComparator());

        int i = 0;

        while (i < m) {
            Edge currEdge = adj.get(i);
            i++;

            int repSrc = dsuSet.findByPathCompression(currEdge.src);
            int repDes = dsuSet.findByPathCompression(currEdge.des);

            if (repDes == repSrc)
                continue;
            
            mstWeight += currEdge.wt;
            numOfEdge++;
            if (numOfEdge == n - 1)
                break;
            
            dsuSet.unionByRank(currEdge.src, currEdge.des);

        }

        return mstWeight;

    }

    static class MyComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge e1, Edge e2) {
            if (e1.wt < e2.wt)
                return -1;
            return 1;
        }
        
    }

    private static class Edge {
        int src;
        int des;
        int wt;

        Edge(int src, int des, int wt) {
            this.src = src;
            this.des = des;
            this.wt = wt;
        }
    }
    
    private static class DisjointSet {

        private int[] parent;
        private int[] rank;

        public DisjointSet(int size) {
            parent = new int[size];
            rank = new int[size];

            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int findByPathCompression(int node) {
            if (parent[node] == node)
                return node;

            return parent[node] = findByPathCompression(parent[node]);
        }

        public void unionByRank(int nodeA, int nodeB) {

            int repA = findByPathCompression(nodeA);
            int repB = findByPathCompression(nodeB);

            if (repA == repB)
                return;

            if (rank[repA] > rank[repB]) {
                parent[repB] = repA;
            } else if (rank[repB] > rank[repA]) {
                parent[repA] = repB;
            } else {
                parent[repA] = repB;
                rank[repB]++;
            }
        }
    }
    
    public static void main (String[] args)throws IOException {
        
        Scanner sc = new Scanner(System.in);
        ArrayList<Edge> adj = new ArrayList<>();
        
        int n = sc.nextInt();
        n++; // for 1 based nodes
        int m = sc.nextInt();
        
        int u, v;
        int w;
        
        for(int i = 0; i < m; i++)
        {
            u = sc.nextInt();
            v = sc.nextInt();
            w = sc.nextInt();

            Edge edg = new Edge(u, v, w);
            adj.add(edg);

        }
        sc.close();
        
        
        System.out.println(kruskalDSU(adj, n, m));
           
        
    }




}
