import java.util.*;

/*
 * ============================================================================
 * LEETCODE 778: Swim in Rising Water
 * ============================================================================
 * Link: https://leetcode.com/problems/swim-in-rising-water/
 *
 * DESCRIPTION:
 * You are given an n x n integer matrix grid where each value grid[i][j] represents 
 * the elevation at that point (i, j).
 *
 * The rain starts to fall. At time t, the depth of the water everywhere is t. 
 * You can swim from a square to another 4-directionally adjacent square if and 
 * only if the elevation of both squares individually are at most t. You can swim 
 * infinite distances in zero time. Of course, you must stay within the boundaries 
 * of the grid during your swim.
 *
 * Return the least time until you can reach the bottom right square (n - 1, n - 1) 
 * if you start at the top left square (0, 0).
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * Input: grid = [[0,2],[1,3]]
 * Output: 3
 * Explanation:
 * At time 0, you are in grid[0][0].
 * You cannot go anywhere else because 4-directionally adjacent neighbors have a higher elevation.
 * You cannot reach grid[1][1] until time 3, when you can swim along the path:
 * 0 -> 1 -> 3.
 *
 * ----------------------------------------------------------------------------
 * APPROACH: Binary Search on Answer + BFS Feasibility Check
 *
 * 1. Monotonicity Property:
 *    - If it is possible to reach (n - 1, n - 1) at time T, it is definitely 
 *      possible at any time T' > T (since more water allows access to more cells).
 *    - If it is impossible at time T, it is also impossible at any time T' < T.
 *    - This monotonic feasibility predicate allows us to binary search the answer.
 *
 * 2. Search Space:
 *    - Minimum possible time: low = 0 (or grid[0][0]).
 *    - Maximum possible time: high = n * n - 1 (since elevations are a permutation of 0 to n^2 - 1).
 *
 * 3. Feasibility Predicate (canReachDestination / BFS):
 *    - Given an allowed elevation ceiling `time`:
 *        a) Check if start grid[0][0] <= time.
 *        b) Traverse using standard 4-directional BFS, stepping ONLY onto cells 
 *           where neighborElevation <= time.
 *        c) If we reach (n - 1, n - 1), return true; otherwise false.
 *
 * 4. Complexity:
 *    - Time Complexity:  O(N^2 * log(N^2)) = O(N^2 * log N)
 *                        - Search range is [0, N^2 - 1], requiring ~ 2 * log2(N) steps.
 *                        - Each BFS visits at most N^2 cells and 4 * N^2 edges -> O(N^2).
 *                        - With N <= 50, N^2 = 2500, log2(2500) ~ 12 iterations.
 *                        - Total operations ~ 12 * 2500 = 3 * 10^4 (blazing fast, < 15ms).
 *    - Space Complexity: O(N^2) for the boolean visited table and BFS queue.
 * ============================================================================
 */
class SwimInRisingWater {

    // 4 cardinal directions: Down, Up, Right, Left
    private static final int[][] DIRECTIONS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int swimInWater(int[][] grid) {
        int n = grid.length;

        // Binary search range for time: from 0 up to the maximum elevation (n * n - 1)
        int low = 0;
        int high = (n * n) - 1;
        int minRequiredTime = high;

        while (low <= high) {
            int midTime = low + (high - low) / 2;

            // Check if bottom-right corner is reachable when water level is midTime
            if (canReachDestination(midTime, grid, n)) {
                minRequiredTime = midTime; // Feasible; try to find a smaller valid time
                high = midTime - 1;
            } else {
                low = midTime + 1;         // Infeasible; water level must be higher
            }
        }

        return minRequiredTime;
    }

    /**
     * Runs BFS to verify whether a path exists from (0, 0) to (n - 1, n - 1)
     * using only cells with elevation <= allowedTime.
     */
    private boolean canReachDestination(int allowedTime, int[][] grid, int n) {
        // Cannot even step onto the starting cell
        if (grid[0][0] > allowedTime) {
            return false;
        }

        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[n][n];

        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];

            // Reached destination cell
            if (row == n - 1 && col == n - 1) {
                return true;
            }

            // Explore all 4 adjacent neighbors
            for (int[] dir : DIRECTIONS) {
                int nextRow = row + dir[0];
                int nextCol = col + dir[1];

                // Check bounds, unvisited state, and elevation constraint
                if (isSafe(nextRow, nextCol, n, visited) && grid[nextRow][nextCol] <= allowedTime) {
                    visited[nextRow][nextCol] = true;
                    queue.offer(new int[]{nextRow, nextCol});
                }
            }
        }

        return false;
    }

    /**
     * Checks whether the coordinate (row, col) is within grid boundaries and not yet visited.
     */
    private boolean isSafe(int row, int col, int n, boolean[][] visited) {
        return row >= 0 && row < n && col >= 0 && col < n && !visited[row][col];
    }
}
