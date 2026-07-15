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

        TreeNode candidateA = null;
        TreeNode candidateB = null;

        List<TreeNode> list = new ArrayList<>();

        inorder(root, list);

        // find anamoly nodes in the meant to be sorted list
        if (list.get(0).val > list.get(1).val) {
            candidateA = list.get(0);
        }

        for (int i = 1; i < list.size() - 1; i++) {
            TreeNode currVal = list.get(i);
            TreeNode prevVal = list.get(i - 1);
            TreeNode nextVal = list.get(i + 1);

            if (currVal.val < prevVal.val || currVal.val > nextVal.val) {
                if (candidateA == null) {
                    candidateA = currVal;
                } else {
                    candidateB = currVal;
                }
            }
        }

        if (list.get(list.size() - 1).val < list.get(list.size() - 2).val) {
            candidateB = list.get(list.size() - 1);
        }

        // swap the values of the node
        int temp = candidateA.val;
        candidateA.val = candidateB.val;
        candidateB.val = temp;

    }
    // linear space and linear time
    private void inorder(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }

        inorder(root.left, list);

        list.add(root);

        inorder(root.right, list);
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
