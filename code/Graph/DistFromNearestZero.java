package Graph;

import java.util.Arrays;

public class DistFromNearestZero {

    /*
    https://leetcode.com/problems/01-matrix/description/
    
    Given an m x n binary matrix mat, return the distance of the nearest 0 for each cell.
    
    The distance between two cells sharing a common edge is 1.
    
    
    Input: mat = [[0,0,0],[0,1,0],[1,1,1]]
    Output: [[0,0,0],[0,1,0],[1,2,1]]
    
    
    Approach : 
    Step 1 - Iterate over the grip and perform least dist from top and left
    Step 2 - Iterate over ther grid from bottom right and perform least dist from right and down.


    */



    public int[][] updateMatrix(int[][] mat) {

        int n = mat.length;
        int m = mat[0].length;

        int MAX = n + m;

        // down right traversal
        for(int i = 0; i  < n; i++) {
            for(int j= 0; j < m; j++) {
                if(mat[i][j] != 0) {
                    int up = i - 1 >= 0 ? mat[i - 1][j] : MAX;
                    int left = j - 1 >= 0 ? mat[i][j - 1] : MAX;

                    mat[i][j] = Integer.min(up, left) + 1;
                }
            }
        }

        // left up traversal

        for(int i = n - 1; i >= 0; i--) {
            for(int j = m - 1; j >= 0; j--) {
                if(mat[i][j] != 0) {
                    int down = i + 1 < n ? mat[i + 1][j] : MAX;
                    int right = j + 1 < m ? mat[i][j + 1] : MAX;

                    mat[i][j] = Integer.min(mat[i][j] , Integer.min(down, right) + 1);
                }
            }
        }
        

        return mat;
        
    }
    
    public static void main(String[] args) {
        DistFromNearestZero obj = new DistFromNearestZero();

        int mat[][] = {
                { 1, 0, 0 }, { 1, 1, 1 }, { 1, 1, 1 }
        };

        Arrays.stream(obj.updateMatrix(mat)).forEach((row) -> {
            Arrays.stream(row).forEach((el) -> System.out.print(el + " "));
            System.out.println();
        });
    }
    
}
