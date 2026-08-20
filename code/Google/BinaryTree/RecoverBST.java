package Google.BinaryTree;

import java.util.ArrayList;
import java.util.List;

import Google.BinaryTree.MaximumPathSum.TreeNode;

public class RecoverBST {

    /*
    https://leetcode.com/problems/recover-binary-search-tree/description/
    
    You are given the root of a binary search tree (BST), 
    where the values of exactly two nodes of the tree were swapped by mistake. 
    Recover the tree without changing its structure.
    
    Input: root = [3,1,4,null,null,2]
    Output: [2,1,4,null,null,3]
    Explanation: 2 cannot be in the right subtree of 3 because 2 < 3. Swapping 2 and 3 makes the BST valid.
    
    
    Approach : 
    
    1. make the inorder list of the nodes. BST inorder should be sorted. find the nodes not at their actual place
    2. constant space method using prev, first and second pointer. do inorder traversal keeping track of last node
    that was traversed, in each traversal, check if prev value is greater than current, if yes, first is prev node
    and second will be our current node
    
    anamoly can be of two types, 1. adjacent swap , 2, non - adjacent swap.
    Hence we do second = curr irrepective of it is null or not, so that we find non-adjacent swap further down the traversal
    */

    private TreeNode first = null;
    private TreeNode second = null;
    private TreeNode prev = null;
    
    public void recoverTree(TreeNode root) {

        // swap the values of the node
        int temp = first.val;
        first.val = second.val;
        second.val = temp;

    }

    // linear time and constant space 
    private void searchNodes(TreeNode root) {

        if (root == null) {
            return;
        }

        searchNodes(root.left);

        if (prev != null && prev.val > root.val) {
            if (first == null) {
                first = prev;
            }

            second = root;
            
        }
        
        prev = root;

        searchNodes(root.right);
    }


    public static void main(String[] args) {
        RecoverBST obj = new RecoverBST();

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(1);
        root.right = new TreeNode(4);
        root.right.left = new TreeNode(2);

        obj.recoverTree(root);
    }
}
