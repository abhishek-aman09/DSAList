package Graph;

import java.util.Objects;

public class StepByStepFromSrcToTrgt {

    /*
    https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/description/
    
    You are given the root of a binary tree with n nodes. Each node is uniquely assigned a value from 1 to n. You are also given an integer startValue representing the value of the start node s, and a different integer destValue representing the value of the destination node t.
    
    Find the shortest path starting from node s and ending at node t. Generate step-by-step directions of such path as a string consisting of only the uppercase letters 'L', 'R', and 'U'. Each letter indicates a specific direction:
    
    'L' means to go from a node to its left child node.
    'R' means to go from a node to its right child node.
    'U' means to go from a node to its parent node.
    Return the step-by-step directions of the shortest path from node s to node t.
    
    Input: root = [5,1,2,3,null,6,4], startValue = 3, destValue = 6
    Output: "UURL"
    Explanation: The shortest path is: 3 → 1 → 5 → 2 → 6.
    
    Approach : Find Lowest Common Ancestor for src and target. get paths from
    lca to src and target. if lca is not eq to src and dst. Invert the path from
    lca to src. append both the path and return it. 
    special cases if lca is equal to src or target.
    */

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    public String getDirections(TreeNode root, int startValue, int destValue) {

        if (Objects.isNull(root)) {
            return "";
        }

        StringBuilder resultPath = new StringBuilder();

        // get the lowest common ansector
        TreeNode lca = getLowestCommonAncestor(root, startValue, destValue);

        // get path from lca to src
        StringBuilder pathFromLcaToSrc = new StringBuilder();
        getPathFromAncestorToNode(lca, startValue, pathFromLcaToSrc);

        // invert the path
        pathFromLcaToSrc = invertPath(pathFromLcaToSrc.length());

        // get path from lca to dest
        StringBuilder pathFromLcaToDst = new StringBuilder();
        getPathFromAncestorToNode(lca, destValue, pathFromLcaToDst);


        // if lca is unique, append both the path
        if (Objects.nonNull(lca) && lca.val != startValue && lca.val != destValue) {          

            resultPath.append(pathFromLcaToSrc.toString());
            resultPath.append(pathFromLcaToDst.toString());

        } else if (Objects.nonNull(lca) && lca.val == destValue) {
        // if lca is eq to dest, return the src path
            resultPath.append(pathFromLcaToSrc);
        } else { // else return the dest path
            resultPath.append(pathFromLcaToDst);
        }

        return resultPath.toString();

    }
    
    private StringBuilder invertPath(int pathLen) {
        // invert path is bascially replacing all down steps with Up
        StringBuilder invertedPath = new StringBuilder();
        while (pathLen-- > 0) {
            invertedPath.append('U');
        }

        return invertedPath;
    }

    private TreeNode getLowestCommonAncestor(TreeNode root, int p, int q) {
        if (root == null) {
            return null;
        }

        if (root.val == p || root.val == q) {
            return root;
        }

        TreeNode left = getLowestCommonAncestor(root.left, p, q);
        TreeNode right = getLowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) {
            return root;
        }

        if (left == null) {
            return right;
        }

        return left;
    }
    
    private boolean getPathFromAncestorToNode(TreeNode root, int targetVal, StringBuilder currPath) {

        if (root == null) {
            return false;
        }

        if (root.val == targetVal) {
            return true;
        }

        currPath.append('L');
        boolean left = getPathFromAncestorToNode(root.left, targetVal, currPath);

        if (left) {
            return true;
        }

        currPath.setLength(currPath.length() - 1);
        currPath.append('R');
        boolean right = getPathFromAncestorToNode(root.right, targetVal, currPath);

        if (right) {
            return true;
        }

        currPath.setLength(currPath.length() - 1);
        return false;
    }
    
}
