package Graph;

import java.util.LinkedList;
import java.util.Queue;

public class SurroundedRegion {

    /*
    https://leetcode.com/problems/surrounded-regions/description/
    
    You are given an m x n matrix board containing letters 'X' and 'O', 
    Capture any 'O' which is not in contact with any edge 'O'.
    To capture a surrounded region, replace all 'O's with 'X's in-place within the original board. 
    You do not need to return anything.
    
    Example 1:
    
    Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
    
    Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
    
    Approach : Multi source dfs :
    iterate over the edges and put any 'O' into the queue and mark them as safe.
    iterate over the queue and put any 'O' into the queue
          
    iterate the grid, capture any 'O' that is not safe.
    */

    public void solve(char[][] board) {

        int n = board.length;
        int m = board[0].length;

        boolean isSafe[][] = new boolean[n][m];

        Queue<int[]> q = new LinkedList<>();

        for(int i = 0; i < n; i++) {
            if(board[i][0] == 'O') {
                q.add(new int[]{i, 0});
                isSafe[i][0] = true;
            }

            if(board[i][m - 1] == 'O') {
                q.add(new int[]{i, m - 1});
                isSafe[i][m - 1] = true;
            }
        }

        for(int j = 0; j < m; j++) {
            if(board[0][j] == 'O') {
                q.add(new int[]{0, j});
                isSafe[0][j] = true;
            }

            if(board[n - 1][j] == 'O') {
                q.add(new int[]{n - 1, j});
                isSafe[n - 1][j] = true;
            }
        }

        while(!q.isEmpty()) {
            int[] curr = q.poll();

            int r = curr[0];
            int c = curr[1];

            if(r + 1 < n && board[r + 1][c] == 'O' && !isSafe[r + 1][c]) {
                isSafe[r + 1][c] = true;
                q.add(new int[]{r + 1, c});
            }

            if(r - 1 >= 0 && board[r - 1][c] == 'O' && !isSafe[r - 1][c]) {
                isSafe[r - 1][c] = true;
                q.add(new int[]{r - 1, c});
            }

            if(c + 1 < m && board[r][c + 1] == 'O' && !isSafe[r][c + 1]) {
                isSafe[r][c + 1] = true;
                q.add(new int[]{r, c + 1});
            }

            if(c - 1 >= 0 && board[r][c - 1] == 'O' && !isSafe[r][c - 1]) {
                isSafe[r][c - 1] = true;
                q.add(new int[]{r, c - 1});
            }

        }


        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(board[i][j] == 'O' && !isSafe[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }
        
    }
    
}
