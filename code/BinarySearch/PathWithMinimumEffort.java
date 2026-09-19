package BinarySearch;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class PathWithMinimumEffort {

    /*
    https://leetcode.com/problems/path-with-minimum-effort/submissions/2119404680/
    
    You are a hiker preparing for an upcoming hike. You are given heights, a 2D array of size rows x columns, where heights[row][col] represents the height of cell (row, col). 
    You are situated in the top-left cell, (0, 0), and you hope to travel to the bottom-right cell, (rows-1, columns-1) (i.e., 0-indexed). You can move up, down, left, or right, 
    and you wish to find a route that requires the minimum effort.
    
    A route's effort is the maximum absolute difference in heights between two consecutive cells of the route.
    
    Return the minimum effort required to travel from the top-left cell to the bottom-right cell.
    
    Input: heights = [[1,2,2],[3,8,2],[5,3,5]]
    Output: 2
    Explanation: The route of [1,3,5,3,5] has a maximum absolute difference of 2 in consecutive cells.
    This is better than the route of [1,2,2,2,5], where the maximum absolute difference is 3.
    
    Approach : Do binary search on total effort space, for each effort,check if we can reach till the bottom end or not, if yes, search for less effort
    
    
    */
    public int minimumEffortPath(int[][] heights) {
        int n = heights.length;
        int m = heights[0].length;

        // Binary search space: the minimum possible effort is 0, 
        // and the maximum possible effort is 1,000,000 (based on LeetCode constraints).
        int l = 0, r = 1000000;

        int ans = 0;

        // Binary Search on the answer space
        while (l <= r) {
            int mid = l + (r - l) / 2;

            // Check if there is a valid path from top-left to bottom-right
            // where the maximum absolute difference between any two adjacent cells is <= 'mid'.
            boolean isPossibleWithGivenDiff = bfs(mid, heights, n, m);

            if (isPossibleWithGivenDiff) {
                // If a path is possible, 'mid' could be our answer.
                // However, we want the MINIMUM effort, so we search the left half 
                // to see if an even smaller effort is also possible.
                ans = mid;
                r = mid - 1;
            } else {
                // If no path is possible, 'mid' is too restrictive. 
                // We must increase our allowed effort by searching the right half.
                l = mid + 1;
            }
        }

        return ans;
    }

    private boolean bfs(int diff, int[][] grid, int n, int m) {

        Queue<int[]> queue = new LinkedList<>();
        // Start BFS from the top-left corner (0, 0)
        queue.add(new int[] { 0, 0 });

        // Keep track of visited cells to prevent infinite loops and redundant processing
        boolean[][] isVisited = new boolean[n][m];
        isVisited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();

            int x = curr[0];
            int y = curr[1];

            // If we have successfully reached the bottom-right corner, a valid path exists!
            if (x == n - 1 && y == m - 1) {
                return true;
            }

            // Check all 4 directions (Down, Up, Right, Left).
            // For each direction, we transition ONLY IF the neighbor is within bounds, unvisited, 
            // AND the height difference between the current cell and the neighbor is <= our allowed 'diff'.

            // Down
            if (isSafe(x + 1, y, n, m, isVisited) && Math.abs(grid[x + 1][y] - grid[x][y]) <= diff) {
                isVisited[x + 1][y] = true;
                queue.offer(new int[] { x + 1, y });
            }

            // Up
            if (isSafe(x - 1, y, n, m, isVisited) && Math.abs(grid[x - 1][y] - grid[x][y]) <= diff) {
                isVisited[x - 1][y] = true;
                queue.offer(new int[] { x - 1, y });
            }

            // Right
            if (isSafe(x, y + 1, n, m, isVisited) && Math.abs(grid[x][y + 1] - grid[x][y]) <= diff) {
                isVisited[x][y + 1] = true;
                queue.offer(new int[] { x, y + 1 });
            }

            // Left
            if (isSafe(x, y - 1, n, m, isVisited) && Math.abs(grid[x][y - 1] - grid[x][y]) <= diff) {
                isVisited[x][y - 1] = true;
                queue.offer(new int[] { x, y - 1 });
            }
        }

        // If the queue empties and we haven't reached the destination, 
        // no valid path is possible with this specific 'diff'.
        return false;
    }

    // Helper method to ensure the coordinates are within the grid boundaries and haven't been visited yet.
    private boolean isSafe(int x, int y, int n, int m, boolean[][] isVisited) {
        return (x >= 0 && x < n && y >= 0 && y < m && !isVisited[x][y]);
    }
    
    //---------------------------- Alternate approach usint dijkstras ---------------------------------------------------
    
    // Cardinal directions for adjacent cell transitions: Down, Up, Right, Left
    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int minimumEffortPathWithDijkstras(int[][] heights) {
        int totalRows = heights.length;
        int totalCols = heights[0].length;

        // minEffortToReach[r][c] stores the minimum effort required to reach cell (r, c)
        int[][] minEffortToReach = new int[totalRows][totalCols];
        for (int[] row : minEffortToReach) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // Min-Heap priority queue ordered by effort: {row, col, accumulatedEffort}
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(
            (cellA, cellB) -> Integer.compare(cellA[2], cellB[2])
        );

        // Base case: starting cell requires 0 effort
        minEffortToReach[0][0] = 0;
        priorityQueue.offer(new int[]{0, 0, 0});

        while (!priorityQueue.isEmpty()) {
            int[] current = priorityQueue.poll();
            int currentRow = current[0];
            int currentCol = current[1];
            int currentEffort = current[2];

            // Early exit: First time bottom-right corner is extracted from the min-heap,
            // its minimum effort is guaranteed to be optimal.
            if (currentRow == totalRows - 1 && currentCol == totalCols - 1) {
                return currentEffort;
            }

            // Stale entry check: discard if a path with strictly lower effort to this cell already exists
            if (currentEffort > minEffortToReach[currentRow][currentCol]) {
                continue;
            }

            // Explore all 4 orthogonal neighbors
            for (int[] direction : DIRECTIONS) {
                int nextRow = currentRow + direction[0];
                int nextCol = currentCol + direction[1];

                // Check grid boundaries
                if (nextRow >= 0 && nextRow < totalRows && nextCol >= 0 && nextCol < totalCols) {
                    // Effort required for this single step between adjacent cells
                    int stepEffort = Math.abs(heights[currentRow][currentCol] - heights[nextRow][nextCol]);

                    // Total effort along this path is the bottleneck (maximum single-step effort)
                    int maxEffortAlongPath = Math.max(currentEffort, stepEffort);

                    // Relaxation step: found a path to (nextRow, nextCol) requiring strictly less effort
                    if (maxEffortAlongPath < minEffortToReach[nextRow][nextCol]) {
                        minEffortToReach[nextRow][nextCol] = maxEffortAlongPath;
                        priorityQueue.offer(new int[]{nextRow, nextCol, maxEffortAlongPath});
                    }
                }
            }
        }

        return 0;
    }
}
