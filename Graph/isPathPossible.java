package Graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Given a grid of size n*n filled with 0, 1, 2, 3.
 *  Check whether there is a path possible from the source to destination.
 *  You can traverse up, down, right and left.
    The description of cells is as follows:

    A value of cell 1 means Source.
    A value of cell 2 means Destination.
    A value of cell 3 means Blank cell.
    A value of cell 0 means Wall (blocked cell which we cannot traverse).
Note: There are only a single source and a single destination.
 */

public class isPathPossible {

    private static class Pair {
        
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

    public static boolean is_Possible(int[][] grid)
    {

        Queue<Pair> queue = new LinkedList<>();

        int rows = grid.length;
        int columns = grid[0].length;

        boolean[][] isVisited = new boolean[rows][columns];

        for (boolean[] row : isVisited) {
            Arrays.fill(row, false);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] == 1) {
                    queue.add(new Pair(i, j));
                    break;
                }
            }
        }

        while (queue.peek() != null) {
            Pair curr = queue.poll();

            int x = curr.getFirst();
            int y = curr.getSecond();
            isVisited[x][y] = true;

            if (x - 1 >= 0) {
                if (grid[x - 1][y] == 2)
                    return true;

                if (!isVisited[x - 1][y] && grid[x - 1][y] == 3) {
                    queue.add(new Pair(x - 1, y));
                }
            }

            if (x + 1 < rows) {
                if (grid[x + 1][y] == 2)
                    return true;

                if (!isVisited[x + 1][y] && grid[x + 1][y] == 3) {
                    queue.add(new Pair(x + 1, y));
                }
            }

            if (y - 1 >= 0) {
                if (grid[x][y - 1] == 2)
                    return true;

                if (!isVisited[x][y - 1] && grid[x][y - 1] == 3) {
                    queue.add(new Pair(x, y - 1));
                }
            }

            if (y + 1 < columns) {
                if (grid[x][y + 1] == 2)
                    return true;

                if (!isVisited[x][y + 1] && grid[x][y + 1] == 3) {
                    queue.add(new Pair(x, y + 1));
                }
            }

        }

        return false;
    }
    
    
}
