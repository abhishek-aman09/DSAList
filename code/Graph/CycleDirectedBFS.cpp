#include<bits/stdc++.h>

using namespace std;

// Directed graph cycle detection using BFS is done using indegree method.

bool canFinish(int sizeOfGraph, vector<vector<int>>& graph) {

     vector<int> isVisited(sizeOfGraph, 0);
    vector<int> indegree(sizeOfGraph, 0);

    queue<int> bfsQueue;

    for(vector<int> curr : graph) {
        for(int child : curr)
        indegree[child]++;
    }

    int totalCourses = 0;

    for (int i = 0; i < sizeOfGraph; i++) 
        if(indegree[i] == 0)
            bfsQueue.push(i);
        

    while(!bfsQueue.empty()) {
        int currNode = bfsQueue.front();
        bfsQueue.pop();
        isVisited[currNode] = 1;

        totalCourses++;

        for(int child : graph[currNode]) {
            if(!isVisited[child]) {
                indegree[child]--;
                if(indegree[child] == 0)
                    bfsQueue.push(child);
            }
        }

    }

    return !(totalCourses == sizeOfGraph);
}
