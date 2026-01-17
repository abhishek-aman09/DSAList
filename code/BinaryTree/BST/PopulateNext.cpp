#include <bits/stdc++.h>

using namespace std;

class Node {
public:
    int val;
    Node* left;
    Node* right;
    Node* next;

    Node() : val(0), left(NULL), right(NULL), next(NULL) {}

    Node(int _val) : val(_val), left(NULL), right(NULL), next(NULL) {}

    Node(int _val, Node* _left, Node* _right, Node* _next)
        : val(_val), left(_left), right(_right), next(_next) {}
};


Node* connect(Node* root) {

    if(!root) return root;

    queue<Node *> bfsQueue;
    bfsQueue.push(root);
    bfsQueue.push(NULL);

    while(!bfsQueue.empty()) {
        

        Node *curr = bfsQueue.front();
        bfsQueue.pop();


        if (curr == NULL) {
            if(!bfsQueue.empty())
                bfsQueue.push(NULL);
            continue;
        }

        curr->next = bfsQueue.front();
        if(curr->left and curr->right) {
            bfsQueue.push(curr->left);
            bfsQueue.push(curr->right);
        }
    }

    return root;
}