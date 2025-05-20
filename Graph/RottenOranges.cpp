#include<bits/stdc++.h>

using namespace std;

/**
 * Given a matrix where each cell in the matrix can have values 0, 1 or 2 which has the following meaning:
0 : Empty cell
1 : Cells have fresh oranges
2 : Cells have rotten oranges

We have to determine what is the earliest time after which all the oranges are rotten. A rotten orange at index [i, j] can rot other fresh orange at indexes [i-1, j], [i+1, j], [i, j-1], [i,j+1] (up, down, left and right) in unit time.

Note: Your task is to return the minimum time to rot all the fresh oranges. If not possible returns -1.

Examples:

Input: mat[][] = 
[[0, 1, 2], 
[0, 1, 2], 
[2, 1, 1]]
Output: 1
Explanation: Oranges at positions (0,2), (1,2), (2,0) will rot oranges at (0,1), (1,1), (2,2) and (2,1) in unit time.
 */

void rotAdjacent(vector<vector<int> >& mat, int i, int j, int rows, int column, queue<pair<int, int>> &q) {

    if(i - 1 >= 0 and mat[i - 1][j] == 1) {
        mat[i - 1][j] = 2;
        q.push({i - 1, j});
    }
    if(j - 1 >= 0 and mat[i][j - 1] == 1) {
        mat[i][j - 1] = 2;
        q.push({i, j - 1});
    }
    if(i + 1 < rows and mat[i + 1][j] == 1) {
        mat[i + 1][j] = 2;
        q.push({i + 1, j});
    }
    if(j + 1 < column and mat[i][j + 1] == 1) {
        mat[i][j + 1] = 2;
        q.push({i, j + 1});
    }
}

int orangesRotting(vector<vector<int> >& mat) {

    int rows = mat.size();

    if(!rows)
        return 0;

    int column = mat[0].size();

    int timeElapsed = 0;

    queue<pair<int, int>> q;

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < column; j++) {
            if(mat[i][j] == 2) {
                q.push({i, j});
            }
        }
    }

    q.push({-1, -1});

    while(!q.empty()) {
        pair<int, int> currCell = q.front();
        q.pop();

        if(currCell.first == -1){
            if(!q.empty()) {
                timeElapsed++;
                q.push({-1, -1});
                continue;
            } else
                break;
        }

        rotAdjacent(mat, currCell.first, currCell.second, rows, column, q);
    }

    for(vector<int> row : mat) 
        for(int cell : row)
            if(cell == 1)
                timeElapsed = -1;

    return timeElapsed;
}

int main() {

}