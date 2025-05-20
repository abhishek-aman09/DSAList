#include <bits/stdc++.h>

using namespace std;

struct Node {
    int data;
    Node *left;
    Node *right;

    Node(int val) {
        data = val;
        left = right = NULL;
    }
};

vector<int> topView(Node* root) {
    vector<int> answer;
    if(!root)
        return answer;

    queue<pair<Node*, int>> listOfNodesWithLevel;

    map<int, int> levelMap;

    listOfNodesWithLevel.push({root, 0});

    while(!listOfNodesWithLevel.empty()) {
        Node * currNode = listOfNodesWithLevel.front().first;
        int currLevel = listOfNodesWithLevel.front().second;
        listOfNodesWithLevel.pop();

        if(levelMap.find(currLevel) == levelMap.end())
        levelMap[currLevel] = currNode->data;

        if(currNode->left)
            listOfNodesWithLevel.push({currNode->left, currLevel - 1});
        if(currNode->right)
            listOfNodesWithLevel.push({currNode->right, currLevel + 1});
    }

    for(pair<int, int> curr : levelMap)
        answer.push_back(curr.second);

    return answer;
}