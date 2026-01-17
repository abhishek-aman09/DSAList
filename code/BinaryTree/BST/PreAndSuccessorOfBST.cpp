#include <bits/stdc++.h>

using namespace std;

struct Node
{
	int key;
	struct Node *left;
	struct Node *right;
	
	Node(int x){
	    key = x;
	    left = NULL;
	    right = NULL;
	}
};

// Do inorder traversal

void findPreSuc(Node* root, Node*& pre, Node*& suc, int key){
    if(!root) return;

    // check if curr node is less than key and
    // (if pre is null or if pre holds a value less than curr node value)
    if(root->key < key and (!pre or pre->key < root->key))
        pre = root;
    
    // check if curr node is more than key and
    // (if suc is null or if suc holds a value more than curr node value
    if(root->key > key and (!suc or suc->key > root->key))
        suc = root;

    findPreSuc(root->left, pre, suc, key);
    findPreSuc(root->right, pre, suc, key);
}