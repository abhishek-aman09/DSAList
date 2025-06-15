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

// idea: inorder traversal of BST is always sorted.

// for Kth largest element do right -> root -> left (reverse of kth smallest);

void inorder(TreeNode* root, int *target, int *result) {
    
    if(!root or *target < 0)
        return;
    
    if(*result != -1)
        return;

    inorder(root->left, target, result);

    // Do target(k) decrement in inordre operation
    *target = *target - 1;

    if(*target == 0){
        *result = root->val;
        return;
    }

    inorder(root->right, target, result);;
}

int kthSmallest(TreeNode* root, int k) {

    int result = -1;

    inorder(root, &k, &result);

    return result;
}