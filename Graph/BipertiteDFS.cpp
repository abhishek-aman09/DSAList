#include <bits/stdc++.h>

using namespace std;

bool dfs(vector<vector<int>> graph, vector<int>& isVisited, vector<int>& colorArray, int curr, int color) {

    isVisited[curr] = 1;

    colorArray[curr] = color;

    for(int child : graph[curr]) {
        if(!isVisited[child]) {
            if(!dfs(graph, isVisited, colorArray, child, color * -1))
                return false;
        }
        else
        {
            if(colorArray[child] == color)
                return false;
        }
    }

    return true;
}

bool isBipartite(vector<vector<int>>& graph) {

    int sizeOfGraph = graph.size();

    vector<int> color(sizeOfGraph, 0);
    vector<int> isVisited(sizeOfGraph, 0);

    return dfs(graph, isVisited, color, 0, -1);
}