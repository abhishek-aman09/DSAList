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
 * Input: root = [3,9,20,null,null,15,7]
 * Output: [[3],[20,9],[15,7]]
 *             3
 *            / \       
 *           9   20       
 *              /  \              
 *             15   7        
 */


vector<vector<int>> zigzagLevelOrder(TreeNode* root) {

    bool isLeftToRight = true;

    stack<TreeNode *> leftStack, rightStack;

    vector<vector<int>> answer;

    if(root)
    leftStack.push(root);

    while(!leftStack.empty()  or !rightStack.empty()) {
        vector<int> currLevel;
        TreeNode *curr;
        if (isLeftToRight)
        {
            while(!leftStack.empty()) {

                curr = leftStack.top();
                leftStack.pop();

                currLevel.push_back(curr->val);

                if(curr->left)
                    rightStack.push(curr->left);
                if(curr->right)
                    rightStack.push(curr->right);
            }
            
        } else {
            while(!rightStack.empty()) {
                curr = rightStack.top();
                rightStack.pop();

                currLevel.push_back(curr->val);

                if(curr->right)
                    leftStack.push(curr->right);
                if(curr->left)
                    leftStack.push(curr->left);
            }
        }

        isLeftToRight = !isLeftToRight;
        answer.push_back(currLevel);
        currLevel.clear();
    }

    return answer;
}