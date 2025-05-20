package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;


public class KosarajuSCC {

    public static int kosaraju(ArrayList<ArrayList<Integer>> adj) {
        int numScc = 0;

        int V = adj.size();

        // Stack to store the reverese order of dfs stack
        Stack<Integer> nodeWithEndTime = new Stack<Integer>();

        boolean[] isVisited = new boolean[V];
        Arrays.fill(isVisited, false);

        // first call to dfs is to calculate the out time of each node
        for (int i = 0; i < V; i++) {
            if (!isVisited[i])
                dfsWithOutTime(adj, i, isVisited, nodeWithEndTime);
        }
        
        // second create transpose of the graph
        ArrayList<ArrayList<Integer>> transposeGraph = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            transposeGraph.add(new ArrayList<>());
        }

        for (int i = 0; i < V; i++) {
            for (int child : adj.get(i)) {
                transposeGraph.get(child).add(i);
            }
        }

        // Step 3 : We need to traverse the graph in decreasing order of finish times
        //          and count number of components.

        Arrays.fill(isVisited, false);

        while (!nodeWithEndTime.empty()) {
            int i = nodeWithEndTime.pop();
          if (!isVisited[i]) {
                dfs(transposeGraph, i, isVisited);
                numScc++;
            }  
        } 


        return numScc;
    }

    
    private static void dfsWithOutTime(ArrayList<ArrayList<Integer>> adj, int curr, boolean[] isVisited, 
            Stack<Integer> nodeWithEndTime) {

        isVisited[curr] = true;

        for (Integer child : adj.get(curr)) {
            if (!isVisited[child]) {
                dfsWithOutTime(adj, child, isVisited, nodeWithEndTime);
            }
        }

        nodeWithEndTime.add(curr);
    }

    private static void dfs(ArrayList<ArrayList<Integer>> adj, int curr, boolean[] isVisited) {

        isVisited[curr] = true;

        for (Integer child : adj.get(curr)) {
            if (!isVisited[child]) {
                dfs(adj, child, isVisited);
            }
        }

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

        System.out.println(kosaraju(graph));
    }
}
