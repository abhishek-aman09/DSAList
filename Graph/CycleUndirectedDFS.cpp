#include<bits/stdc++.h>

using namespace std;

bool dfs(vector<vector<int>> graph, vector<int>& isVisited, int curr, int parent) {

    isVisited[curr] = 1;

    for(int child: graph[curr]) {
        if(!isVisited[child] and !dfs(graph, isVisited, child, curr)){
            continue;
        } else {
            if(child  != parent)
                return true;
        }
    }

    return false;
}

bool isCycle(vector<vector<int>> graph) {

    int sizeOfGraph = graph.size();

    vector<int> isVisited(sizeOfGraph, 0);

    bool ans = false;

    for (int i = 0; i < sizeOfGraph; i++) {
        if(!isVisited[i])
            ans = ans or dfs(graph, isVisited, i, -1);
    }

    return ans;
}

/**
 * if(!isVisited[child]){
 *   if(dfs(graph, isVisited, child, curr)) return true;
 * 
 * Need to find why this line was giving TLE
 * 
 */