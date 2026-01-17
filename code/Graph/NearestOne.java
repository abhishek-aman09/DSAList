package Graph;

import java.util.Arrays;
import java.util.Scanner;

public class NearestOne {


    public static int[][] nearest(int[][] grid)
    {
        int rows = grid.length;
        int columns = grid[0].length;

        int[][] result = new int[rows][columns];

        for (int[] row : result) {
            Arrays.fill(row, -1);
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = getDistance(grid, rows, columns, i, j, result);
            }
        }

        return result;

    }
    
    private static int getDistance(int[][] grid, int rows, int column, int x, int y, int[][] result) {

        if (grid[x][y] == 1)
            return 0;

        int distance = 0, minDistance = Integer.MAX_VALUE;
        boolean hasFound = false;

        while(!hasFound) {
            if (x - 1 >= 0) {
                // if 1 is found in adjacent top grid
                if (grid[x - 1][y] == 1) {
                    return distance + 1;
                }

                // if adjacent top grid has precomputed value of nearest one
                if (result[x - 1][y] != -1) {
                    minDistance = Math.min(minDistance, distance + result[x - 1][y] + 1);
                    hasFound = true;
                }
            }

            if (x + 1 < rows) {
                // if 1 is found in adjacent down grid
                if (grid[x + 1][y] == 1) {
                    return distance + 1;
                }

                // if adjacent down grid has precomputed value of nearest one
                if (result[x + 1][y] != -1) {
                    minDistance = Math.min(minDistance, distance + result[x + 1][y] + 1);
                    hasFound = true;
                }
            }

            if (y - 1 >= 0) {
                // if 1 is found in adjacent left grid
                if (grid[x][y - 1] == 1) {
                    return distance + 1;
                }

                // if adjacent left grid has precomputed value of nearest one
                if (result[x][y - 1] != -1) {
                    minDistance = Math.min(minDistance, distance + result[x][y - 1] + 1);
                    hasFound = true;
                }
            }

            if (y + 1 < column) {
                // if 1 is found in adjacent left grid
                if (grid[x][y + 1] == 1) {
                    return distance + 1;
                }

                // if adjacent left grid has precomputed value of nearest one
                if (result[x][y + 1] != -1) {
                    minDistance = Math.min(minDistance, distance + result[x][y + 1] + 1);
                    hasFound = true;
                }
            }

            distance++;
        }

        return minDistance;

    }

    public static void main(String[] args) {

        int m, n;

        Scanner scanner = new Scanner(System.in);
        m = scanner.nextInt();
        n = scanner.nextInt();

        int[][] arr = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = scanner.nextInt();
            }
        }

        scanner.close();

        int[][] result = nearest(arr);

        for (int[] rows : result) {
            for (int el : rows) {
                System.out.print(el + " ");
            }
            System.out.println();
        }

    }
    
}