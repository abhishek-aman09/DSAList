#include<bits/stdc++.h>

using namespace std;

// Cycle detection in directed graph using DFS is done using Active Stack Method

bool dfs(vector<vector<int>> graph,
vector<int> &isVisited,
vector<int> &activeStack,
int currNode
) {
    isVisited[currNode] = 1;
    activeStack.push_back(currNode);

    for(int child : graph[currNode]) {
        // if curr child is not visited but its subgraph
        // contains a cycle, return true
        if(!isVisited[child] and
            dfs(graph, isVisited, activeStack, child) )
            return true;

        // if child is visited and it is present in active stack,
        // it is an ansector of current node, return true
        if(isVisited[child] and
            find(activeStack.begin(), activeStack.end(), child) != activeStack.end())
            return true;
    }
    activeStack.pop_back();

    return false;
}

bool isCycle(vector<vector<int>> graph) {

    int sizeOfGraph = graph.size();

    vector<int> activeStack;
    vector<int> isVisited(sizeOfGraph, 0);

    return dfs(graph, isVisited, activeStack, 0);
}