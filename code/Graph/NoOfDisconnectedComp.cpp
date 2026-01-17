#include<bits/stdc++.h>

using namespace std;


void dfs(vector<vector<char>> grid, vector<vector<int>>& isVisited, int i, int j, int rows, int column) {
    
    if(i < 0 or i >= rows or j < 0 or j >= column)
        return;

    isVisited[i][j] = 1;

    if((i - 1) >= 0 and grid[i - 1][j] == '1' and !isVisited[i - 1][j])
        dfs(grid, isVisited, i - 1, j, rows, column);

    if((i + 1) < rows and grid[i + 1][j] == '1' and !isVisited[i + 1][j])
        dfs(grid, isVisited, i + 1, j, rows, column);

    if((j + 1) < column and grid[i][j + 1] == '1' and !isVisited[i][j + 1])
        dfs(grid, isVisited, i , j + 1, rows, column);

    if((j - 1) >= 0 and grid[i][j - 1] == '1' and !isVisited[i][j - 1])
        dfs(grid, isVisited, i , j - 1, rows, column);
}

int numIslands(vector<vector<char>>& grid) {

    int rows = grid.size();
    if(!rows)
        return 0;
    int columns = grid[0].size();

    vector <vector<int>> isVisited(rows, vector<int>(columns, 0));

    int ans = 0;

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
            char curr = grid[i][j];
            if(!isVisited[i][j] and curr == '1') {
                ans++;
                dfs(grid, isVisited, i, j, rows, columns);
            }
        }
    }

    return ans;
}