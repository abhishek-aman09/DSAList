package Graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Input: grid = {{X,O,X},{O,X,O},{X,X,X}}
Output: 3
Explanation: 
The grid is-
X O X
O X O
X X X
So, X with same colour are adjacent to each 
other vertically for horizontally (diagonals 
not included). So, there are 3 different groups 
in the given grid.
 */

public class NumberOfIslands {

    public int xShape(char[][] grid) {

        int totalIslands = 0;

        int rows = grid.length;
        int columns = grid[0].length;

        boolean[][] isVisited = new boolean[rows][columns];

        for (boolean[] row : isVisited)
            Arrays.fill(row, false);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!isVisited[i][j] && grid[i][j] == 'X') {
                    totalIslands++;
                    bfs(grid, i, j, rows, columns, isVisited);
                }
            }
        }

        return totalIslands;
    }

    public static class Pair {
        
        private final int first;
        private final int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
        
        public int getFirst() {
            return this.first;
        }

        public int getSecond() {
            return this.second;
        }

    }
    
    private void bfs(char[][] grid, int i, int j, int rows, int columns, boolean[][] isVisited) {

        Queue<Pair> queue = new LinkedList<>();

        queue.add(new Pair(i, j));

        while (queue.peek() != null) {
            Pair curr = queue.poll();

            int n = curr.getFirst();
            int m = curr.getSecond();

            isVisited[n][m] = true;

            if (isSafe(n - 1, m, rows, columns, isVisited, grid)) {
                isVisited[n - 1][m] = true;
                queue.add(new Pair(n - 1, m));
            }
            if (isSafe(n + 1, m, rows, columns, isVisited, grid)) {
                isVisited[n + 1][m] = true;
                queue.add(new Pair(n + 1, m));
            }
            if (isSafe(n, m - 1, rows, columns, isVisited, grid)) {
                isVisited[n][m - 1] = true;
                queue.add(new Pair(n, m - 1));
            }
            if (isSafe(n, m + 1, rows, columns, isVisited, grid)) {
                isVisited[n][m + 1] = true;
                queue.add(new Pair(n, m + 1));
            }
        }

    }
    
    private boolean isSafe(int i, int j, int rows, int columns, boolean[][] isVisited, char[][] grid) {
        if (i < 0 || i >= rows || j < 0 || j >= columns)
            return false;
        
        if (isVisited[i][j] || grid[i][j] != 'X')
            return false;
        
        return true;
    }
    
}
