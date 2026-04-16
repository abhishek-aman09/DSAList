package Array;

import java.util.ArrayList;
import java.util.List;

public class SpiralTraversalMatrix {

    /*
    https://leetcode.com/problems/spiral-matrix/

    Do spiral traversal of matrix
    */
    

    static final int OUT_OF_BOUND = 101;
    public List<Integer> spiralOrder(int[][] matrix) {

        IntWrapper i = new IntWrapper(0);
        IntWrapper j = new IntWrapper(0);

        int n = matrix.length;
        int m = matrix[0].length;

        if(n == 1 && m == 1) {
            return List.of(matrix[0][0]);
        }

        List<Integer> ans = new ArrayList<>();

        while(isSafe(i, j, n, m, matrix)) {
            
            leftToRightRow(i, j, n, m, matrix, ans);
            // increment i val to go to next row for down iteration and same
            // of other increment and decrement
            i.val++;


            topToDownCol(i, j, n, m, matrix, ans);
            j.val--;


            rightToLeftRow(i, j, n, m, matrix, ans);
            i.val--;


            downToTopCol(i, j, n, m, matrix, ans);
            j.val++;

        }
        
        return ans;
    }

    private void leftToRightRow(IntWrapper i, IntWrapper j, int n, int m, int matrix[][], List<Integer> ans) {

        while(j.val < m && isSafe(i, j, n, m, matrix)) {
            ans.add(matrix[i.val][j.val]);
            matrix[i.val][j.val] = OUT_OF_BOUND;
            j.val++;
        }
        // decrease to bring j val into bounds or to a valid object
        // applicable throughout.
        j.val--;
    }

    private void topToDownCol(IntWrapper i, IntWrapper j, int n, int m, int matrix[][], List<Integer> ans) {

        while(i.val < n && isSafe(i, j, n, m, matrix)) {
            ans.add(matrix[i.val][j.val]);
            matrix[i.val][j.val] = OUT_OF_BOUND;
            i.val++;
        }

        i.val--;
    }

    private void rightToLeftRow(IntWrapper i, IntWrapper j, int n, int m, int matrix[][], List<Integer> ans) {

        while(j.val >= 0 && isSafe(i, j, n, m, matrix)) {
            ans.add(matrix[i.val][j.val]);
            matrix[i.val][j.val] = OUT_OF_BOUND;
            j.val--;
        }

        j.val++;
    }

    private void downToTopCol(IntWrapper i, IntWrapper j, int n, int m, int matrix[][], List<Integer> ans) {

        while(i.val >= 0 && isSafe(i, j, n, m, matrix)) {
            ans.add(matrix[i.val][j.val]);
            matrix[i.val][j.val] = OUT_OF_BOUND;
            i.val--;
        }

        i.val++;
    }

    private boolean isSafe(IntWrapper i, IntWrapper j, int n, int m, int matrix[][]) {
        return((i.val >= 0 && i.val< n) && (j.val >= 0 && j.val < m) && matrix[i.val][j.val] != OUT_OF_BOUND);
    }

    private class IntWrapper {
        int val;
        IntWrapper(int val) {
            this.val = val;
        }
    }
    

}
