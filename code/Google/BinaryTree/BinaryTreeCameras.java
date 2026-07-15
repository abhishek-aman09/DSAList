package Google.BinaryTree;

import java.util.HashSet;
import java.util.Set;

public class BinaryTreeCameras {

    /*
    https://leetcode.com/problems/binary-tree-cameras/description
    
    You are given the root of a binary tree. We install cameras on the tree nodes where 
    each camera at a node can monitor its parent, itself, and its immediate children.
    
    Return the minimum number of cameras needed to monitor all nodes of the tree.
    
    Input: root = [0,0,null,0,0]
    Output: 1
    
    Approach : Do a postorder traversal with these points in mind
    
    1. whenever you put a camera on a node, make sure to put its parent and both children for maximum coverage
    2. we do post order traversal as we can check left and right subtree coverage before deciding on if we need a
    camera on the root.

    for ref : root val = 0 (uncovered), val = 1 (covered) and val = 2 (camera) for constrant space complexity
    */
    
    public int minCameraCover(TreeNode root) {

        int count[] = new int[1];

        countCameras(root, null, null, count);

        return count[0];

    }
    
    private void countCameras(TreeNode root, TreeNode parent, TreeNode grandParent, int count[]) {

        if (root == null) {
            return;
        }
        // do post-order, first left and then right
        countCameras(root.left, root, parent, count);
        countCameras(root.right, root, parent, count);

        // if after post order, root is covered either by itself or parent, we do nothing
        if (root.val == 2 || root.val == 1 || (parent != null && parent.val == 2)) { 
            return;
        }

        // if it is not covered, we check
        if(parent == null) { // if root has no parent, we put the camera on the node (make its value 2 and increase count)
            root.val = 2;
            count[0]++;
        } else { // else we put camera on the parent and also mark grandparent as covered
            root.val = 1;
            parent.val = 2;
            count[0]++;

            if(grandParent != null) {
                grandParent.val = 1;
            }
        }
    }
    
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        TreeNode(int x, TreeNode left, TreeNode right) {
            this.val = x;
            this.left = left;
            this.right = right;
        }
    }
}
