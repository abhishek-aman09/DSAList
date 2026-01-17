#include <bits/stdc++.h>

using namespace std;

struct Node
{
    int data;
    struct Node* left;
    struct Node* right;
    
    Node(int x){
        data = x;
        left = right = NULL;
    }
};

vector<int> rightView(Node* root) {

    queue<Node *> listOfNodes;
    vector<int> answer;
    if(!root)
        return answer;

    listOfNodes.push(root);
    listOfNodes.push(NULL);

    while(!listOfNodes.empty()) {
        Node *currNode = listOfNodes.front();
        listOfNodes.pop();

        if(currNode == NULL and !listOfNodes.empty()) {
            listOfNodes.push(NULL);
            continue;
        }

        if(!listOfNodes.empty() and listOfNodes.front() == NULL)
            answer.push_back(currNode->data);

        if(currNode) {
            if(currNode->left)
                listOfNodes.push(currNode->left);
            if(currNode->right)
                listOfNodes.push(currNode->right);
        }
    }

    return answer;
}