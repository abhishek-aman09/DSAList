#include<bits/stdc++.h>

using namespace std;

vector<int> bfsOfGraph(vector<vector<int>> &adj) {

    int sizeOfGraph = adj.size();

    queue<int> levelOrderQueue;

    vector<int> isVisited(sizeOfGraph, 0);

    levelOrderQueue.push(0);
    vector<int> ans;

    while(!levelOrderQueue.empty()) {
        int curr = levelOrderQueue.front();
        levelOrderQueue.pop();

        isVisited[curr] = 1;
        ans.push_back(curr);

        for(int child : adj[curr]) {
            if(!isVisited[child]) {
                levelOrderQueue.push(child);
                isVisited[child] = 1;
            }
        }
    }

    return ans;
}