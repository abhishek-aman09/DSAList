#include<bits/stdc++.h>

using namespace std;

// topological sort using bfs
vector<int> topologicalSort(vector<vector<int>>& adj) {

    int sizeOfGraph = adj.size();

    vector<int> ans;
    vector<int> isVisited(sizeOfGraph,0);
    vector<int> inDegree(sizeOfGraph, 0);

    queue<int> bfsQueue;

    for(vector<int> curr : adj) {
        for(int child : curr)
            inDegree[child]++;
    }

    for (int i = 0; i < sizeOfGraph; i++) 
        if(inDegree[i] == 0)
            bfsQueue.push(i);
    
    while(!bfsQueue.empty()) {

        int currNode = bfsQueue.front();
        bfsQueue.pop();

        ans.push_back(currNode);

        isVisited[currNode] = 1;

        for(int child : adj[currNode]) {
            inDegree[child]--;
            if(inDegree[child] == 0 and !isVisited[child]) 
            {
                isVisited[child] = 1;
                bfsQueue.push(child);
            }
        }
    }

    return ans;
}


// topological sort using dfs

void dfs(vector<vector<int>> adj, vector<int> &isVisited, vector<int> &inDegree, vector<int>& ans, int curr) {

    isVisited[curr] = 1;

    ans.push_back(curr);

    for(int child : adj[curr]) {
        inDegree[child]--;
        if(inDegree[child] == 0 and !isVisited[child]) 
        {
            isVisited[child] = 1;
            dfs(adj, isVisited, inDegree, ans, child);
        }
    }
}

vector<int> topologicalSort(vector<vector<int>>& adj) {

    int sizeOfGraph = adj.size();

    vector<int> ans;
    vector<int> isVisited(sizeOfGraph, 0);
    vector<int> inDegree(sizeOfGraph, 0);

    for(vector<int> curr : adj) {
        for(int child : curr)
            inDegree[child]++;
    }

    for (int i = 0; i < sizeOfGraph; i++) 
        if(inDegree[i] == 0 and !isVisited[i])
            dfs(adj, isVisited, inDegree, ans, i);

    return ans;
}