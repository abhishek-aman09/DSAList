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

TreeNode* constructBST(vector<int> nums, int left, int right) {

    if(left > right)
        return NULL;
    
    int mid = (left + right) / 2;

    TreeNode *root = new TreeNode(nums[mid]);
    root->left = constructBST(nums, left, mid - 1);
    root->right = constructBST(nums, mid + 1, right);

    return root;
}

TreeNode* sortedArrayToBST(vector<int>& nums) {

    int sizeOfNums = nums.size();

    return constructBST(nums, 0, sizeOfNums - 1);
}