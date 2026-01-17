package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Given an undirected connected graph with V vertices and adjacency list adj. You are required to find all the vertices
 *  removing which (and edges through it) disconnects the graph into 2 or more components and return it in sorted manner.
Note: Indexing is zero-based i.e nodes numbering from (0 to V-1). There might be loops present in the graph.
 */

// For root node, as there is no ansector, just count the number of children
// for others, find articulation point using in and low_ansec_in concept
// note every bridge is not articulation point
 
/**
 * e.g 1 - 2 - 3
 *         |
 *         4
 * all are bridge nodes but only 2 is articulation point, hence 
 * for a node to be articulation point, it's indegree has to be more than 1
 */

public class ArticulationPoint {

    private static Set<Integer> set = new HashSet<>();

    public static ArrayList<Integer> articulationPoints(int V, ArrayList<ArrayList<Integer>> adj) {

        ArrayList<Integer> listOfArticulationPoints = new ArrayList<>();
        

        int[] inTime = new int[V];
        int[] lowestAnsectorInTime = new int[V];
        boolean[] isVisited = new boolean[V];

        IntegerWrapper timer = new IntegerWrapper(0);

        Arrays.fill(lowestAnsectorInTime, Integer.MAX_VALUE);
        Arrays.fill(inTime, Integer.MAX_VALUE);

        dfs(adj, 0, -1, inTime, lowestAnsectorInTime, isVisited, timer);

        for (int el : set) {
            listOfArticulationPoints.add(el);
        }

        if (listOfArticulationPoints.size() == 0)
            listOfArticulationPoints.add(-1);
         
        Collections.sort(listOfArticulationPoints);

        return listOfArticulationPoints;

    }
    
    private static void dfs(ArrayList<ArrayList<Integer>> adj, int curr, int parent, int[] inTime, int[] lowestAnsectorInTime,
            boolean[] isVisited, IntegerWrapper timer) {

        isVisited[curr] = true;

        int children = 0;

        inTime[curr] = lowestAnsectorInTime[curr] = timer.getNum();
        timer.increaseNum();

        for (int child : adj.get(curr)) {
        
            if (child == parent)
                continue;

            if (!isVisited[child]) {
                children++;

                dfs(adj, child, curr, inTime, lowestAnsectorInTime, isVisited, timer);

                lowestAnsectorInTime[curr] = Math.min(lowestAnsectorInTime[curr], lowestAnsectorInTime[child]);

                if (parent != -1 && (lowestAnsectorInTime[child] >= inTime[curr])) {
                    set.add(curr);
            }
            } else {
                lowestAnsectorInTime[curr] = Math.min(lowestAnsectorInTime[curr], inTime[child]);
            }
        }
        
        if (children > 1 && parent == -1)
            set.add(curr);
        
    }

    public static class IntegerWrapper {
        private int x;

        public IntegerWrapper(int x) {
            this.x = x;
        }

        int getNum() {
            return this.x;
        }

        void increaseNum() {
            this.x++;
        }
    }

    public static void main(String[] args) {
        int V, E;
        Scanner scanner = new Scanner(System.in);

        V = scanner.nextInt();
        E = scanner.nextInt();

        ArrayList<ArrayList<Integer>> adj = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            int u, v;
            u = scanner.nextInt();
            v = scanner.nextInt();

            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        scanner.close();

        articulationPoints(V, adj).forEach(
                el -> {
                System.out.print(el + "  ");
            }
        );
    }
    
}
