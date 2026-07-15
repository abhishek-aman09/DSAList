package Google.Graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathInGridWithObstacles {

    /*
    https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/description/
    
    You are given an m x n integer matrix grid where each cell is either 0 (empty) or 1 (obstacle). 
    You can move up, down, left, or right from and to an empty cell in one step.
    
    Return the minimum number of steps to walk from the upper left corner (0, 0) to the lower right corner (m - 1, n - 1) 
    given that you can eliminate at most k obstacles. If it is not possible to find such walk return -1.
    
    
    Input: grid = [[0,0,0],[1,1,0],[0,0,0],[0,1,1],[0,0,0]], k = 1
    Output: 6
    Explanation: 
    The shortest path without eliminating any obstacle is 10.
    The shortest path with one obstacle elimination at position (3,2) is 6. Such path is (0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> (3,2) -> (4,2).
    
    
    Approach : Always use BFS for shortest path in a graph/grid
    
    with coordinates, we also need to keep a track of K remaining at each cell. As, reaching a particular cell with k1,
    It could be impossible to reach the end, but with another k2, it could be possible
    
    Also the visited of each cell should also be depend on the k with which we are reaching it.
    
    */
    

    public int shortestPath(int[][] grid, int k) {

        int n = grid.length;
        int m = grid[0].length;

        // create a visited for each cell which is also dependent on K.
        boolean isVisited[][][] = new boolean[n][m][k + 1];

        Arrays.stream(isVisited).forEach(matrix -> Arrays.stream(matrix).forEach(row -> Arrays.fill(row, false)));

        // the bfs queue will store the coordinates and the K value with which we are reaching there
        Queue<Integer[]> queue = new LinkedList<>();

        // push source into the queue and mark it visited, we use null to mark end of each level iteration
        queue.offer(new Integer[] { 0, 0, k });
        queue.offer(null);
        isVisited[0][0][k] = true;

        int currentLevel = 0;

        while (!queue.isEmpty()) {
            Integer[] curr = queue.poll(); // poll the current cell

            if (curr == null) { // standard level increment based on null
                if (!queue.isEmpty()) {
                    queue.offer(null);
                }

                currentLevel++;
                continue;
            }

            int x = curr[0];
            int y = curr[1];
            int remK = curr[2];

            if (x == n - 1 && y == m - 1) { // if we have reached the end, return the current level
                return currentLevel;
            }

           // we push the neighbours based on the criteria, wheter they have a wall or note (Applicable to all four directions)
            if (isSafe(x + 1, y, n, m, remK, isVisited) && grid[x + 1][y] == 0) { // if they don't have the wall, K remains the same
                isVisited[x + 1][y][remK] = true;
                queue.offer(new Integer[] { x + 1, y, remK });
            } else if (isSafe(x + 1, y, n, m, remK - 1, isVisited) && grid[x + 1][y] == 1) { // if they have a wall, they will
                // get one K less as they will use them.
                isVisited[x + 1][y][remK - 1] = true;
                queue.offer(new Integer[] { x + 1, y, remK - 1 });
            }
            
            if (isSafe(x - 1, y, n, m, remK, isVisited) && grid[x - 1][y] == 0) {
                isVisited[x - 1][y][remK] = true;
                queue.offer(new Integer[] { x - 1, y, remK });
            } else if (isSafe(x - 1, y, n, m, remK - 1, isVisited) && grid[x - 1][y] == 1) {
                isVisited[x - 1][y][remK - 1] = true;
                queue.offer(new Integer[] { x - 1, y, remK - 1 });
            }
            
            if (isSafe(x, y + 1, n, m, remK, isVisited) && grid[x][y + 1] == 0) {
                isVisited[x][y + 1][remK] = true;
                queue.offer(new Integer[] { x, y + 1, remK });
            } else if (isSafe(x, y + 1, n, m, remK - 1, isVisited) && grid[x][y + 1] == 1) {
                isVisited[x][y + 1][remK - 1] = true;
                queue.offer(new Integer[] { x, y + 1, remK - 1 });

            }
            
            if (isSafe(x, y - 1, n, m, remK, isVisited) && grid[x][y - 1] == 0) {
                isVisited[x][y - 1][remK] = true;
                queue.offer(new Integer[] { x, y - 1, remK });
            } else if (isSafe(x, y - 1, n, m, remK - 1, isVisited) && grid[x][y - 1] == 1) {
                isVisited[x][y - 1][remK - 1] = true;
                queue.offer(new Integer[] { x, y - 1, remK - 1});
                
            }
            
        }

        return -1; // return -1 if we could not reach the end

    }
    
    private boolean isSafe(int x, int y, int n, int m, int k, boolean[][][] isVisited) {
        return (x < n && x >= 0 && y < m && y >= 0 && k >= 0 && !isVisited[x][y][k]);
    }

    public static void main(String[] args) {
        ShortestPathInGridWithObstacles obj = new ShortestPathInGridWithObstacles();

        int grid[][] = new int[][] { { 0, 0 }, { 1, 0 }, { 1, 0 }, { 1, 0 }, { 1, 0 }, { 1, 0 }, { 0, 0 }, { 0, 1 },
                { 0, 1 }, { 0, 1 }, { 0, 0 }, { 1, 0 }, { 1, 0 }, { 0, 0 } };

        System.out.println(obj.shortestPath(grid, 4));
    }
    
}
