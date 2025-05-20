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
 * A height-balanced binary tree is a binary tree in
 * which the depth of the two subtrees of every node never
 * differs by more than one.
 */


bool isHeightBalanced = true;

int height(TreeNode* root) {
    if(!root)
        return 0;

    int lh = height(root->left);
    int rh = height(root->right);

    if(abs(lh - rh) > 1)
        isHeightBalanced = false;

    return 1 + max(lh, rh);
}

bool isBalanced(TreeNode* root) {

    height(root);
    return isHeightBalanced;
}