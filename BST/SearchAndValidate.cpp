#include <bits/stdc++.h>

using namespace std;

struct TreeNode {
     int val;
     TreeNode *left;
     TreeNode *right;
     TreeNode() : val(0), left(nullptr), right(nullptr) {}
     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};


TreeNode* searchBST(TreeNode* root, int val) {

    if(!root)
        return NULL;

    if(root->val == val)
        return root;
    
    if(val < root->val)
        return searchBST(root->left, val);
    return searchBST(root->right, val);
}


// Check if given tree is BST or not

bool solve(TreeNode* root, long leftLim, long rightLim) {
    if(!root)
        return true;
    
    if(root->val <= leftLim or root->val >= rightLim)
        return false;
    return solve(root->left, leftLim, root->val) and solve(root->right, root->val, rightLim);
}

bool isValidBST(TreeNode* root) {

    return solve(root, LLONG_MIN, LLONG_MAX);
}