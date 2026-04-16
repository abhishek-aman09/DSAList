package BinaryTree;

public class FlattenBTIntoTree {

    /*
    https://leetcode.com/problems/flatten-binary-tree-to-linked-list/
    
    Given the root of a binary tree, flatten the tree into a "linked list":
    
    The "linked list" should use the same TreeNode class where the right child pointer points to the next node in the list and the left child pointer is always null.
    The "linked list" should be in the same order as a pre-order traversal of the binary tree.
    
    Input: root = [1,2,5,3,4,null,6]
    Output: [1,null,2,null,3,null,4,null,5,null,6]
    
    */

    public class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode(int x) { val = x; }
    }     

    public void flatten(TreeNode root) {
        helper(root);
    }

    private TreeNode helper(TreeNode root) {
        if (root == null) {
            return null;
        }

        // store current left and right
        TreeNode currLeft = root.left;
        TreeNode currRight = root.right;

        // make left as null
        root.left = null;

        // get new right by recursion and assign to root.right
        TreeNode newRight = helper(currLeft);
        root.right = newRight;

        // new Right could be null, try to reach to the leaf node on right
        while (newRight != null && newRight.right != null) {
            newRight = newRight.right;
        }

        // if the leaf is null, left subtree was null, else assign the right
        // of new right to currRight
        if (newRight != null) {
            newRight.right = currRight;
        } else {
            root.right = currRight;
        }

        // recursively call for root.right and return root
        helper(root.right);

        return root;

    }
    

}
