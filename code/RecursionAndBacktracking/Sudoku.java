package RecursionAndBacktracking;

public class Sudoku {

    // https://leetcode.com/problems/sudoku-solver/submissions/1566927857/

    /*
    Write a program to solve a Sudoku puzzle by filling the empty cells.
    
    A sudoku solution must satisfy all of the following rules:
    
    Each of the digits 1-9 must occur exactly once in each row.
    Each of the digits 1-9 must occur exactly once in each column.
    Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
    The '.' character indicates empty cells.
    
    Approach : Try all combination from 1 to 9 for each cell and recursively call
    the next
    Time complexity (9^(N^2));
    
    */

    private char toChar(int i) {
        return (char) (i + 48);

    }

    private boolean isSafe(char[][] mat, int n, int x, int y, int num) {

        // if the number present in present row or column
        for (int i = 0; i < n; i++) {
            if (mat[x][i] == toChar(num) || mat[i][y] == toChar(num)) {
                return false;
            }
        }

        // Check if num exists in the 3x3 sub-matrix
        int startRow = x - (x % 3), startCol = y - (y % 3);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mat[i + startRow][j + startCol] == toChar(num)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean solve(char[][] mat, int n, int x, int y) {

        if (x == n) {
            return true;
        }

        if (y == n) {
            return solve(mat, n, x + 1, 0);
        }

        if (mat[x][y] != '.') {
            return solve(mat, n, x, y + 1);
        }

        // try all permutation from 1 to 9
        for (int i = 1; i <= 9; i++) {
            // if i is safe to put at grid i,j
            if (isSafe(mat, n, x, y, i)) {
                mat[x][y] = toChar(i);
                boolean result;
                // only increment y here, go row wise
                result = solve(mat, n, x, y + 1);

                if (result) {
                    return true;
                }
            }
        }
        // if true is not returned, bring the cell back to original
        // state and return false.
        mat[x][y] = '.';
        return false;

    }

    private void solveSudoku(char[][] mat) {
        int n = 9;

        solve(mat, n, 0, 0);
    }

}
