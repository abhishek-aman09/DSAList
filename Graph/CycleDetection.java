package Graph;

import java.util.LinkedList;
import java.util.Queue;

public class CycleDetection {
    
    // Directed - BFS + indegree
    public static boolean isCyclic(int V, int[][] edges) {

        int totalNodes = 0;

        int visited[] = new int[V];
        int indegree[] = new int[V];

        for (int[] row : edges) {
            for (int el : row) {
                indegree[el]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < V; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int curr = queue.poll();

            visited[curr] = 1;
            totalNodes++;

            for (int el : edges[curr]) {
                if (visited[el] == 0) {
                    indegree[el]--;
                    if (indegree[el] == 0) {
                        queue.add(el);
                    }
                }
            }
        }

        return !(V == totalNodes);

    }
    

    public static void main(String[] args) {
        int arr[][] = {
            {},{2},{},{0},{2}
        };

        System.out.println(isCyclic(5, arr));
    }
}
