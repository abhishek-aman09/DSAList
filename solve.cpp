#include<bits/stdc++.h>

using namespace std;

class Solution {
    private:
    bool detect(int row, int col, vector<vector<char>>& grid,vector<vector<int>>&visited)
    {
        int m = grid[0].size();
        char source = grid[row][col];
        visited[row][col] = 1;
        queue<pair<pair<int,int>,pair<int,int>>>q;
        q.push({{row,col}, {-1,-1}});
        while(!q.empty())
        {
            pair<int,int>parent = q.front().second;
            int rowindex= q.front().first.first;
            int colindex = q.front().first.second;
            q.pop();
            for(int i=0; i<m ; i++)
            {
                if(!visited[rowindex][colindex + i] && grid[rowindex][colindex + i] == source)
                {
                    visited[rowindex][colindex+i] =1;
                    q.push({{rowindex, colindex + i}, {rowindex,colindex}});
                }
                else if (parent != std::pair<int, int>(rowindex, colindex))
                return true;
            }
        }
        return false;
    }
public:
    bool containsCycle(vector<vector<char>>& grid) {
        int n=grid.size();
        int m=grid[0].size();

        vector<vector<int>>visited(n,vector<int>(m,0));

        for(int i=0; i<n; i++)
        {
            for(int j=0; j<m; j++)
            {
                if(!visited[i][j])
                {
                    
                    if(detect(i,j,grid,visited))
                    return true;
                }
            }
        }
        return false; 
    }
};