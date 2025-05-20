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

void insert(TreeNode* root, int currVal) {
    if(!root)
        return;
    
    if(currVal < root->val and !root->left)
        root->left = new TreeNode(currVal);
    else if(currVal > root->val and !root->right)
        root->right = new TreeNode(currVal);
    else if(currVal < root->val and root->left)
        insert(root->left, currVal);
    else
        insert(root->right, currVal);
}

TreeNode* bstFromPreorder(vector<int>& preorder) {

    int sizeOfArray = preorder.size();

    if(!sizeOfArray)
        return NULL;
    
    TreeNode *root = new TreeNode(preorder[0]);

   // traverse through the array and call insert method for each element
   
    for (int i = 1; i < sizeOfArray; i++)
        insert(root, preorder[i]);

    return root;
}