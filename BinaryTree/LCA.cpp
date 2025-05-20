#include<bits/stdc++.h>

using namespace std;

struct TreeNode {
     int val;
     TreeNode *left;
     TreeNode *right;
     TreeNode() : val(0), left(nullptr), right(nullptr) {}
     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};


/**
 * This code is valid if p and q exist.
 */


TreeNode* lowestCommonAncestor(TreeNode* root, TreeNode* p, TreeNode* q) {
        
    // If root value matches to either p or q, return root 
    // to verify that either node exist as child node.
    if(!root or root->val == p->val or root->val == q->val)
        return root;

    // Check if left and right return the desired child nodes.
    TreeNode *leftChild = lowestCommonAncestor(root->left, p, q);
    TreeNode *rightChild = lowestCommonAncestor(root->right, p, q);

    if(leftChild != NULL and rightChild != NULL)
        return root;
    if(leftChild == NULL)
        return rightChild;
    else
        return leftChild;
}