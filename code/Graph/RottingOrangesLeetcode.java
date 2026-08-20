package Graph;

import java.util.LinkedList;
import java.util.Queue;

public class RottingOrangesLeetcode {

    // https://leetcode.com/problems/rotting-oranges/description/
    /*
    You are given an m x n grid where each cell can have one of three values:
    
    0 representing an empty cell,
    1 representing a fresh orange, or
    2 representing a rotten orange.
    Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
    
    Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.
    
    Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
    Output: 4
    
    Approach : Create a queue to store cell that contains rotten oranges.
    
    Do a level order traversal and push all adjacent 2 into the queue.
    push null at end of each level to mark the level. As soon as you encounter
    a null, you completed the level, increment timer by one
    
    Q. Why intialize timer with -1 and not 0.
    A. Problem says level 1 rot at time = 0. As we increment timer at each level,
    we start it with -1.
    */
    
    public int orangesRotting(int[][] grid) {

        int n = grid.length;
        int m = grid[0].length;

        if (m == 1 && n == 1) {
            return 0;
        }

        // Do a level order traversal, easiest way

        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) {
                    queue.add(new Pair<>(i, j));
                }
            }
        }
        
        queue.add(null);
        int timer = -1;

        while (!queue.isEmpty()) {
            Pair<Integer, Integer> curr = queue.poll();

            if (curr == null) {
                if (!queue.isEmpty()) {
                    queue.add(null);
                }
                timer++;
                continue;
            }
            rotAdjacent(grid, curr.row, curr.col, n, m, queue);
            
        }

        for (int row[] : grid) {
            for (int el : row) {
                if (el == 1) {
                    return -1;
                }
            }
        }

        return timer;


    }
    
    private void rotAdjacent(int grid[][], int i, int j, int n, int m, Queue<Pair<Integer, Integer>> q) {

        if (isSafe(grid, i + 1, j, n, m)) {
            grid[i + 1][j] = 2;
            q.add(new Pair<>(i + 1, j));
        }
        if (isSafe(grid, i -1, j, n, m)) {
            grid[i - 1][j] = 2;
            q.add(new Pair<>(i - 1, j));
        } 
        if (isSafe(grid, i, j + 1, n, m)) {
            grid[i][j + 1] = 2;
            q.add(new Pair<>(i, j + 1));
        }
        if (isSafe(grid, i, j - 1, n, m)) {
            grid[i][j - 1] = 2;
            q.add(new Pair<>(i, j - 1));
        }
    }

    private boolean isSafe(int grid[][], int i, int j, int n, int m) {
        if (i < 0 || j < 0 || i >= n || j >= m || grid[i][j] == 0 || grid[i][j] == 2) {
            return false;
        }

        return true;
    }
    
    private static class Pair<K, V> {
        K row;
        V col;

        Pair(K row, V col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] args) {
        int arr[][] = { { 1, 2 } };

        RottingOrangesLeetcode obj = new RottingOrangesLeetcode();

        System.out.println(obj.orangesRotting(arr));
    }
}
