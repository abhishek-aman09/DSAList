#include <bits/stdc++.h>

using namespace std;

class Node {
public:
    int val;
    vector<Node*> neighbors;
    Node() {
        val = 0;
        neighbors = vector<Node*>();
    }
    Node(int _val) {
        val = _val;
        neighbors = vector<Node*>();
    }
    Node(int _val, vector<Node*> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
};


Node* dfs(Node* currNode, map<int, Node*>& oldToNewMap) {

    int currVal = currNode->val;
    Node *temp = new Node(currVal);

    oldToNewMap[currVal] = temp;

    for(Node* childNode : currNode->neighbors) {
        if(oldToNewMap.find(childNode->val) != oldToNewMap.end()) {
            temp->neighbors.push_back(oldToNewMap[childNode->val]);
        } else {
            Node *newChild = dfs(childNode, oldToNewMap);
            temp->neighbors.push_back(newChild);
        }
    }

    return temp;
}

Node* cloneGraph(Node* node) {

    if(!node)
        return NULL;

    map<int, Node*> oldToNewMap;

    return dfs(node, oldToNewMap);
}