package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Given a Directed Graph having V nodes numbered from 0 to V-1, and E directed edges. 
 * Given two nodes, source and destination, count the number of ways or paths between these two vertices in the directed graph. 
 * These paths should not contain any cycle.
    Note: Graph doesn't contain multiple edges, self-loop, and cycles.
 */

// In java int/Integer(Immutble) cannot be passed as reference. Always use a wrapper class.

public class allPossiblePaths {
    
    public static int countPaths(int V, ArrayList<ArrayList<Integer>> adj, int source,
            int destination) {
                
        IntegerByReference totalPaths = new IntegerByReference(0);

        boolean isVisited[] = new boolean[V];
        Arrays.fill(isVisited, false);

        dfs(adj, source, destination, isVisited, totalPaths);

        return totalPaths.getInt();
    }

    private static class IntegerByReference {
    
        private int num;
        
        public IntegerByReference(int num) {
            this.num = num;
        }

        public int getInt() {
            return this.num;
        }

        public void increaseNum() {
            this.num++;
        }
    }
    
    private static void dfs(ArrayList<ArrayList<Integer>> adj, int currNode, int destination, boolean[] isVisited,
            IntegerByReference totalPaths) {

        isVisited[currNode] = true;

        if (currNode == destination) {
            totalPaths.increaseNum();
            isVisited[currNode] = false;
            return;
        }

        for (Integer child : adj.get(currNode)) {
            if (!isVisited[child])
                dfs(adj, child, destination, isVisited, totalPaths);
        }

        isVisited[currNode] = false;
    }
    
    public static void main(String[] args) {
        int V, E;
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        E = sc.nextInt();

        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            graph.add(list);
        }

        for (int i = 0; i < E; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();

            graph.get(u).add(v);
        }
        sc.close();

        System.out.println(countPaths(V, graph, 0, 4));


    }
    
}
