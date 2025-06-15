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

// https://leetcode.com/problems/maximum-sum-bst-in-binary-tree/description/
class NodeWithIsBST {
    public:
        TreeNode *root;
        NodeWithIsBST *left;
        NodeWithIsBST *right;
        bool isBST;
        int bstSum;
        int leftLim;
        int rightLim;

        NodeWithIsBST(TreeNode* root) {
            this->root = root;
            this->isBST = true;
            this->left = nullptr;
            this->right = nullptr;
            this->bstSum = this->leftLim = this->rightLim = root->val;
        }
};

void sum(NodeWithIsBST *rootNode, int *maxSum) {
    if(rootNode->root == NULL)
        return;
    
    if(rootNode->root->left){
        rootNode->left = new NodeWithIsBST(rootNode->root->left);
        sum(rootNode->left, maxSum);
    }

    if(rootNode->root->right){
        rootNode->right = new NodeWithIsBST(rootNode->root->right);
        sum(rootNode->right, maxSum);
    }

    if(rootNode->left and rootNode->isBST){
        rootNode->leftLim = rootNode->left->leftLim;

        if (rootNode->left->isBST == false or
             rootNode->left->root->val >= rootNode->root->val or
             rootNode->left->leftLim >= rootNode->root->val or
             rootNode->left->rightLim >= rootNode->root->val)
            rootNode->isBST = false;
        else if(rootNode->left->root->val < rootNode->root->val and rootNode->left->isBST)
            rootNode->isBST = true;
    }

    if(rootNode->right and rootNode->isBST) {
        rootNode->rightLim = rootNode->right->rightLim;

        if (rootNode->right->isBST == false or 
            rootNode->right->root->val <= rootNode->root->val or
            rootNode->right->leftLim <= rootNode->root->val or
             rootNode->right->rightLim <= rootNode->root->val)
            rootNode->isBST = false;
        else if(rootNode->right->root->val > rootNode->root->val and rootNode->right->isBST)
            rootNode->isBST = true;
    }

    if(rootNode->isBST) {
        if(rootNode->left and rootNode->right)
            rootNode->bstSum += (rootNode->left->bstSum + rootNode->right->bstSum);
        else if(!rootNode->right and rootNode->left)
            rootNode->bstSum = rootNode->bstSum + rootNode->left->bstSum;
        else if(rootNode->right and !rootNode->left)
            rootNode->bstSum = rootNode->bstSum + rootNode->right->bstSum;
    }

    if(rootNode->isBST)
        *maxSum = max(rootNode->bstSum, *maxSum);
}

int maxSumBST(TreeNode* root) {

    NodeWithIsBST *rootNode = new NodeWithIsBST(root);

    int maxSum = 0;

    sum(rootNode, &maxSum);

    return maxSum;
}

