#include<bits/stdc++.h>

using namespace std;

void dfs(vector<vector<int>>& adj, int currNode, vector<int>& isVisited, vector<int>& ans) {

    if(isVisited[currNode])
        return;
    isVisited[currNode] = 1;

    ans.push_back(currNode);

    for(int child : adj[currNode]) {
        if(!isVisited[child])
            dfs(adj, child, isVisited, ans);
    }
}

vector<int> dfsOfGraph(vector<vector<int>>& adj) {

    int sizeOfGraph = adj.size();

    vector<int> isVisited(sizeOfGraph);
    fill(isVisited.begin(), isVisited.end(), 0);

    vector<int> ans;

    dfs(adj, 0, isVisited, ans);

    return ans;
}