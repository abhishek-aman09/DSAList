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

int height(TreeNode *root, int *diameter) {
    if(!root)
        return 0;

    int leftHeight = height(root->left, diameter);
    int rightHeight = height(root->right, diameter);

    // Remove this line to find the height of the tree
    *diameter = max(*diameter, 1 + leftHeight + rightHeight);

    return 1 + max(leftHeight, rightHeight);
}

int diameterOfBinaryTree(TreeNode* root) {
    int diameter = 0;
    height(root, &diameter);

    return diameter;
}