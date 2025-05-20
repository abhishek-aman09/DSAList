#include<bits/stdc++.h>

using namespace std;


bool isCycle(vector<vector<int>> graph) {

    int sizeOfGraph = graph.size();

    vector<int> isVisited(sizeOfGraph, 0);

    queue<pair<int, int>> nodeWithParent;

    // Loop is to check for any disconnected components
    for (int i = 0; i < sizeOfGraph; i++) {

        if(!isVisited[i])
        nodeWithParent.push({i, -1});

        while(!nodeWithParent.empty()) {

            int curr = nodeWithParent.front().first;
            int parent = nodeWithParent.front().second;

            nodeWithParent.pop();

            isVisited[curr] = 1;

            for(int child : graph[curr]) {
                if(!isVisited[child]) {
                    isVisited[child] = 1;
                    nodeWithParent.push({child, curr});
                } else {
                    if(child != parent)
                        return true;
                }
            }
        }
    }

    
    return false;
}