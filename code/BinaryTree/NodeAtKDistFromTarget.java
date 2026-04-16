package BinaryTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class NodeAtKDistFromTarget {

    /*
    https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/description/

    This logic can be extended to burn/infect tree from root node as well LC 2385
    
    Given the root of a binary tree, the value of a target node target, 
    and an integer k, return an array of the values of all nodes that have a distance k from the target node.
    
    You can return the answer in any order.
    
    Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
    Output: [7,4,1]
    Explanation: The nodes that are a distance 2 from the target node (with value 5) have values 7, 4, and 1.
    
    Approach : create a map that store node to parent relation for each node.
    
    now start dfs with level from target node (parent, left and right) checking if it already exist in nodeWithLevel
    map. iterate the map and get all nodes with k dist from target
    
    */


    public class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode(int x) { val = x; }
    } 



    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {

        Map<TreeNode, TreeNode> nodeToParent = new HashMap<>();

        inorder(root, null, nodeToParent);

        Map<TreeNode, Integer> nodeWithLevel = new HashMap<>();

        nodeWithLevel.put(target, 0);

        levelFromTarget(target, nodeToParent, 0, nodeWithLevel);

        List<Integer> ans = new ArrayList<>();

        for (Map.Entry<TreeNode, Integer> pair : nodeWithLevel.entrySet()) {
            if (pair.getValue() == k) {
                ans.add(pair.getKey().val);
            }
        }

        return ans;

    }
    
    private void inorder(TreeNode root, TreeNode parent, Map<TreeNode, TreeNode> nodeToParent) {

        if (root == null) {
            return;
        }

        nodeToParent.put(root, parent);
        inorder(root.left, root, nodeToParent);
        inorder(root.right, root, nodeToParent);
    }
    
    private void levelFromTarget(TreeNode root, Map<TreeNode, TreeNode> nodeToParent, int level,
            Map<TreeNode, Integer> nodeWithLevel) {
        
        if (root == null) {
            return;
        }

        nodeWithLevel.put(root, level);

        if (!nodeWithLevel.containsKey(nodeToParent.get(root))) {
            levelFromTarget(nodeToParent.get(root), nodeToParent, level + 1, nodeWithLevel);
        }

        if (root.left != null && !nodeWithLevel.containsKey(root.left)) {
            levelFromTarget(root.left, nodeToParent, level + 1, nodeWithLevel);
        }
        if (root.right != null && !nodeWithLevel.containsKey(root.right)) {
            levelFromTarget(root.right, nodeToParent, level + 1, nodeWithLevel);
        }
    }    
    
}
