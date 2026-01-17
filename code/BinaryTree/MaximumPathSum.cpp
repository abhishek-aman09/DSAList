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

int solve(TreeNode* root, int *ans) {

        if(!root)
            return 0;

        int leftSum = solve(root->left, ans);
        int rightSum = solve(root->right, ans);

        if(!root->left and !root->right){
            *ans = max(*ans, root->val);
            return root->val;
        }
            

        if(root->left and !root->right){
            *ans = max(*ans, max(leftSum, max(root->val, leftSum + root->val)));
            return max(root->val + leftSum, root->val);
        }

        if(!root->left and root->right){
            *ans = max(*ans,max(rightSum, max(root->val, rightSum + root->val)));
            return max(root->val, rightSum + root->val);
        }

        int ls = root->val + leftSum;
        int rs = root->val + rightSum;
        int ts = leftSum + rightSum + root->val;

        *ans = max(*ans,
                max(ls,
                    max(rs,
                        max(root->val,
                            max(leftSum, 
                                max(ts, rightSum)))))

        );
        
        return max(ls, max(root->val, rs));
    }

    int maxPathSum(TreeNode* root) {
        if(!root)
            return 0;

        int ans = root->val;

        solve(root, &ans);

        return ans;
    }