package RecursionAndBacktracking;

public class Sudoku {

    private char toChar(int i) {
        return (char) (i + 48);

    }

    private static boolean isSafe(int[][] mat, int n, int x, int y, int num) {

        for (int i = 0; i < n; i++) {
            if (mat[x][i] == num || mat[i][y] == num) {
                return false;
            }
        }

        // Check if num exists in the 3x3 sub-matrix
        int startRow = x - (x % 3), startCol = y - (y % 3);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mat[i + startRow][j + startCol] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean solve(int[][] mat, int n, int x, int y) {

        if (x == n - 1 && y == n - 1) {
            for (int i = 1; i <= 9; i++) {
                if (isSafe(mat, n, x, y, i)) {
                    mat[x][y] = i;
                }
            }
            return true;
        }

        if (y >= 9) {
            x++;
            y = 0;
        }

        // if grid el is 0
        if (mat[x][y] == 0) {

            // try all permutation from 1 to 9
            for (int i = 1; i <= 9; i++) {
                // if i is safe to put at grid i,j
                if (isSafe(mat, n, x, y, i)) {
                    mat[x][y] = i;
                    boolean result;
                    result = solve(mat, n, x, y + 1);

                    if (result == true) {
                        return true;
                    }
                }
            }

            mat[x][y] = 0;
            return false;
        } else {
            return solve(mat, n, x, y + 1);
        }

    }

    static void solveSudoku(int[][] mat) {
        int n = 9;

        solve(mat, n, 0, 0);
    }

}
