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


// Given a BST. Implement hasNext(), next(),
// hasRight() and right() functionality.

class BSTIterator {
    public :
     stack<TreeNode *> stkLeft, stkRight;

     BSTIterator(TreeNode* root) {
         TreeNode *rootRight = root;
         while (root)
         {
             stkLeft.push(root);
             root = root->left;
         }
         while (rootRight)
         {
             stkRight.push(rootRight);
             rootRight = rootRight->right;
         }
     }

    // returns if the bst has any more next (predecessor) left
     bool hasNext() {
         return stkLeft.size() > 0;
     }

    // return if the bst has any more before (successor) left
     bool hasBefore() {
         return stkRight.size() > 0;
     }

    // return the predecessor
     TreeNode* next() {
        if(!hasNext())
            return NULL;

        TreeNode *nextNode = stkLeft.top();
        stkLeft.pop();
        if(nextNode->right) {
            TreeNode *curr = nextNode->right;
            while(curr) {
                stkLeft.push(curr);
                curr = curr->left;
            }
        }

        return nextNode;
     }

    // return the successor
    TreeNode* before() {
        if(!hasBefore())
            return NULL;

        TreeNode *beforeNode = stkRight.top();
        stkRight.pop();
        if(beforeNode->left) {
            TreeNode *curr = beforeNode->left;
            while(curr) {
                stkRight.push(curr);
                curr = curr->right;
            }
        }

        return beforeNode;
     }

};


bool findTarget(TreeNode* root, int k) {

    BSTIterator *bstIterator = new BSTIterator(root);

    TreeNode *left, *right;

    left = bstIterator->next();
    right = bstIterator->before();

    while(left and right and left != right) {
        int currVal = left->val + right->val;
        if (currVal == k)
            return true;
        else if(currVal < k)
            left = bstIterator->next();
        else
            right = bstIterator->before();
    }

    return false;
}
