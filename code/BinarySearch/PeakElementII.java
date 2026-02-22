package BinarySearch;

public class PeakElementII {

    // https://leetcode.com/problems/find-a-peak-element-ii/

    /*
    A peak element in a 2D grid is an element that is strictly greater than 
    all of its adjacent neighbors to the left, right, top, and bottom.
    
    Given a 0-indexed m x n matrix mat where no two adjacent cells are equal, 
    find any peak element mat[i][j] and return the length 2 array [i,j].
    
    You may assume that the entire matrix is surrounded by an outer perimeter with the value -1 in each cell.
    
    You must write an algorithm that runs in O(m log(n)) or O(n log(m)) time.
    
    approach : apply binary search on the row 0 - n-1. for any row mid, 
                columns and get the greatest in the col. the el will surely be 
                greater than left and right. now check row - 1 and row + 1.
                if curr > top, go down and vice versa.
    
    */

    public int[] findPeakGrid(int[][] mat) {
        
        int n = mat.length;
        int m = mat[0].length;

        if(n == 1 && m ==1) {
            return new int[]{0, 0};
        }

        // applying binary search on rows
        int l = 0, r = n - 1;

        while(l <= r) {
            int midRow = l + (r - l) / 2;

            int maxCol = 0;

            // for each midRow, we fing col with max value
            for(int i = 0; i < m; i++){
                maxCol = mat[midRow][maxCol] < mat[midRow][i] ? i : maxCol;
            }

            // calucalte the top and down value of the cell
            int top = midRow - 1 >= 0 ? mat[midRow - 1][maxCol] : -1;
            int bottom = midRow + 1 < n ? mat[midRow + 1][maxCol] : -1;

            // if curr is greater than both, return it
            if(top < mat[midRow][maxCol] && bottom < mat[midRow][maxCol]) {
                return new int[]{midRow, maxCol};
            }
            // if curr is greater than top, greater element lies in bottom, go down
            if(mat[midRow][maxCol] > top) {
                l = midRow + 1;
            } else { // else go up
                r = midRow - 1;
            }
        }

        return new int[]{-1, -1};

    }
       
}
