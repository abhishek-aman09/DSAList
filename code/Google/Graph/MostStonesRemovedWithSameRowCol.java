package Google.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MostStonesRemovedWithSameRowCol {
    
    /*
    https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/
    
    
    On a 2D plane, we place n stones at some integer coordinate points. Each coordinate point may have at most one stone.
    
    A stone can be removed if it shares either the same row or the same column as another stone that has not been removed.
    
    Given an array stones of length n where stones[i] = [xi, yi] represents the location of the ith stone, 
    return the largest possible number of stones that can be removed.
    
    
    Example 1:
    
    Input: stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
    Output: 5
    Explanation: One way to remove 5 stones is as follows:
    1. Remove stone [2,2] because it shares the same row as [2,1].
    2. Remove stone [2,1] because it shares the same column as [0,1].
    3. Remove stone [1,2] because it shares the same row as [1,0].
    4. Remove stone [1,0] because it shares the same column as [0,0].
    5. Remove stone [0,1] because it shares the same row as [0,0].
    Stone [0,0] cannot be removed since it does not share a row/column with another stone still on the plane.
    
    Approach : We use the indices of coordinates as nodes in our graph
    suppose we havee [[0,0],[1,0],[0,1]]
    
    for each index we have index 0's x cordinte match with 2's x cordinate so we push both into each other child
    i.push(j) and j.push(i)
    formed graph 0 -> 1, 2 | 1 -> 0 | 2 -> 0

    now we perform dfs to count the number of nodes we can remove. we can only remove a node if it has at least one unvisited child
    */


    public int removeStones(int[][] stones) {

        int n = stones.length;

        List<Integer> graph[] = new ArrayList[n]; // size of grpah is n as it is index based
        boolean isVisited[] = new boolean[n];

        Arrays.fill(isVisited, false);

        for(int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int i = 0; i < n; i++) {
            int x = stones[i][0];
            int y = stones[i][1];

            for(int j = i + 1; j < n; j++) {

                int jx = stones[j][0];
                int jy = stones[j][1];

                if(x == jx || y == jy) { // if either x or y coordiantes of curr pair matches, we join them
                   graph[i].add(j);
                   graph[j].add(i);
                }    
            }
        }

        int ans = 0;
        int[] count = new int[]{0}; // var to store count of nodes removed

        for(int u = 0; u < n; u++) {
            if(!isVisited[u]) {
                dfs(graph, isVisited, u, count);
                ans += count[0];
                count[0] = 0;
            }
        }

        return ans;
        
    }

    private void dfs(List<Integer> graph[], boolean[] isVisited, int u, int[] count) {

        isVisited[u] = true;

        for (int child : graph[u]) {
            if (!isVisited[child]) {
                count[0]++; // for each unvisited children, we increase the count
                dfs(graph, isVisited, child, count);
            }
        }
    }
    
    
}
