package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacificAtlanticWaterFlow {

    // https://leetcode.com/problems/pacific-atlantic-water-flow/description/

    // for each dfs call, caller grid will be the parent
    // isPossible method has time complexity of (n * m)(n * m)
    // better approach - make two visited grid for pacific and atlantic
    // from each border row and colum mark visited where we can reach from
    // the given edge cell
    // at last, the cell we can reach from both end, is our anwer
    // time complexity - (n * m)

    public List<List<Integer>> pacificAtlantic(int[][] heights) {

        int n = heights.length;
        int m = heights[0].length;

        List<List<Integer>> ans = new ArrayList<>();

        boolean isVisitedPacific[][] = new boolean[n][m];
        
        for (int i = 0; i < n; i++) {
            dfs(heights, i, 0, n, m, isVisitedPacific, heights[i][0]);
        }

        for (int j = 0; j < m; j++) {
            dfs(heights, 0, j, n, m, isVisitedPacific, heights[0][j]);
        }

        boolean isVisitedAtlantic[][] = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            dfs(heights, i, m - 1, n, m, isVisitedAtlantic, heights[i][m - 1]);
        }

        for (int j = 0; j < m; j++) {
            dfs(heights, n - 1, j, n, m, isVisitedAtlantic, heights[n - 1][j]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (isVisitedAtlantic[i][j] && isVisitedPacific[i][j]) {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(i);
                    temp.add(j);
                    ans.add(temp);
                }
            }
        }

        return ans;

    }
    
    private void isPossible(int[][] grid, int i, int j, int n, int m, boolean[][] isVisited, boolean isBothPossible[][],
            int parent) {

        if (i < 0 || j < 0) {
            isBothPossible[0][0] = true;
            return;
        }

        if (i >= n || j >= m) {
            isBothPossible[0][1] = true;
            return;
        }

        if (isBothPossible[0][0] && isBothPossible[0][1]) {
            return;
        }

        if (isVisited[i][j]) {
            return;
        }

        if (grid[i][j] > parent) {
            return;
        }

        isVisited[i][j] = true;

        isPossible(grid, i + 1, j, n, m, isVisited, isBothPossible, grid[i][j]);
        isPossible(grid, i - 1, j, n, m, isVisited, isBothPossible, grid[i][j]);
        isPossible(grid, i, j + 1, n, m, isVisited, isBothPossible, grid[i][j]);
        isPossible(grid, i, j - 1, n, m, isVisited, isBothPossible, grid[i][j]);
    }
    
    public void dfs(int grid[][], int i, int j, int n, int m, boolean isVisited[][], int parent) {

        if (i >= n || i < 0 || j >= m || j < 0) {
            return;
        }

        if (isVisited[i][j]) {
            return;
        }

        if (grid[i][j] < parent) {
            return;
        }

        isVisited[i][j] = true;

        dfs(grid, i + 1, j, n, m, isVisited, grid[i][j]);
        dfs(grid, i - 1, j, n, m, isVisited, grid[i][j]);
        dfs(grid, i, j + 1, n, m, isVisited, grid[i][j]);
        dfs(grid, i, j - 1, n, m, isVisited, grid[i][j]);
    }


    public static void main(String[] args) {
        int grid[][] = {
            {1,2,2,3,5},
            {3,2,3,4,4},
            {2,4,5,3,1},
            {6,7,1,4,5},
            {5,1,1,2,4}
        };

        PacificAtlanticWaterFlow obj = new PacificAtlanticWaterFlow();

        obj.pacificAtlantic(grid);
    }
    
}
