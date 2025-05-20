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

vector<int> leftView(Node* root) {

    queue<Node *> listOfNodes;
    vector<int> answer;
    if(!root)
        return answer;
    listOfNodes.push(NULL);
    listOfNodes.push(root);
    

    while(!listOfNodes.empty()) {
        Node *currNode = listOfNodes.front();
        listOfNodes.pop();

        if(currNode == NULL and !listOfNodes.empty()) {
            listOfNodes.push(NULL);
        }

        if(!listOfNodes.empty() and currNode == NULL)
            answer.push_back(listOfNodes.front()->data);

        if(currNode) {
            if(currNode->left)
                listOfNodes.push(currNode->left);
            if(currNode->right)
                listOfNodes.push(currNode->right);
        }
    }
    return answer;
    
}