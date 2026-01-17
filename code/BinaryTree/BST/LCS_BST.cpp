#include<bits/stdc++.h>

using namespace std;

struct TreeNode {
      int val;
      TreeNode *left;
      TreeNode *right;
      TreeNode(int x) : val(x), left(NULL), right(NULL) {}
};


TreeNode* lowestCommonAncestor(TreeNode* root, TreeNode* p, TreeNode* q) {

    if(!root or root == p or root == q)
        return root;

    TreeNode *leftTree, *rightTree;

    if(root->val < p->val)
        leftTree = lowestCommonAncestor(root->right, p, q);
    else
        leftTree = lowestCommonAncestor(root->left, p, q);
    
    if(root->val < q->val)
        rightTree = lowestCommonAncestor(root->right, p, q);
    else
        rightTree = lowestCommonAncestor(root->left, p, q);

    if(leftTree and rightTree and leftTree != rightTree)
        return root;
    if(leftTree)
        return leftTree;
    return rightTree;
}