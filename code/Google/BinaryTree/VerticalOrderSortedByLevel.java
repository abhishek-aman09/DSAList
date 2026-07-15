package Google.BinaryTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class VerticalOrderSortedByLevel {

    /*

    https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/
    
    Given the root of a binary tree, calculate the vertical order traversal of the binary tree.
    
    For each node at position (row, col), its left and right children will be at positions (row + 1, col - 1)
     and (row + 1, col + 1) respectively. The root of the tree is at (0, 0).
    
    The vertical order traversal of a binary tree is a list of top-to-bottom orderings for each 
    column index starting from the leftmost column and ending on the rightmost column. 
    There may be multiple nodes in the same row and same column. In such a case, sort these nodes by their values.
    
    Return the vertical order traversal of the binary tree.
    
    Approach : Make a map of map of Int and list of int
    
    the outer map key will be the level while the inner map key will be the
    rows in a particular level. use treemap to keep things sorted in order.
    
    for each level, we store all rows and list of node in the particular row\
    
    eg : on level 0 lies 3 row with value, [0], [4, 5], [8,9,10,11]
    
    
    
    */

    public class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode(int x) { val = x; }
    }  
    

    public List<List<Integer>> verticalTraversal(TreeNode root) {

        if(root == null) {
            return new ArrayList<>();
        }

        Queue<Pair<TreeNode, Integer>> bfsQueue = new LinkedList<>();

        // Level wise sorted map of row wise sorted map of nodes
        Map<Integer, TreeMap<Integer, List<Integer>>> levelAndRowwiseSortedMap = new TreeMap<>();

        List<List<Integer>> ans = new LinkedList<>();

        // add current node with its level
        bfsQueue.add(new Pair<>(root, 0));
        bfsQueue.add(null);

        int currRowNum = 0;

        while(!bfsQueue.isEmpty()) {
            Pair<TreeNode, Integer> curr = bfsQueue.poll();

            if (curr == null) {
                if (!bfsQueue.isEmpty()) {
                    bfsQueue.add(null);
                }
                currRowNum++;
                continue;
            }
            
            TreeNode currNode = curr.node;
            int currLevel = curr.level;

            // if the current level is not present in tree, assign with empty map
            if(!levelAndRowwiseSortedMap.containsKey(currLevel)) {
                levelAndRowwiseSortedMap.put(currLevel, new TreeMap<>());
            }

            // if the current row in current level is not present, assign with empty list
            if(!levelAndRowwiseSortedMap.get(currLevel).containsKey(currRowNum)) {
                levelAndRowwiseSortedMap.get(currLevel).put(currRowNum, new ArrayList<>());
            }

            List<Integer> temp = levelAndRowwiseSortedMap.get(currLevel).get(currRowNum); // fetch the list of nodes
            temp.add(currNode.val); // add current node to current row list

            if(currNode.left != null) {  // if the left of current node is not null, push it into the queue
                bfsQueue.add(new Pair<>(currNode.left, currLevel - 1));
            }

            if(currNode.right != null) { // if the right of curr is not null, push into the queue 
                bfsQueue.add(new Pair<>(currNode.right, currLevel + 1));
            }
        }

        for (Map.Entry<Integer, TreeMap<Integer, List<Integer>>> levelEntry : levelAndRowwiseSortedMap.entrySet()) {

            List<Integer> levelNodes = new ArrayList<>();
            
            // Get the TreeMap representing rows for current level
            TreeMap<Integer, List<Integer>> rows = levelEntry.getValue();
            
            for (Map.Entry<Integer, List<Integer>> rowEntry : rows.entrySet()) {
                List<Integer> nodesInRowOfCurrColumn = rowEntry.getValue();
                
                // 1. Sort nodes that are for the curr row
                Collections.sort(nodesInRowOfCurrColumn);
                
                // 2. Add them to the combined list for this level
                levelNodes.addAll(nodesInRowOfCurrColumn);
            }
            
            // 3. Add the fully assembled level to the final answer
            ans.add(levelNodes);
        }


        return ans;

        
    }

    private static class Pair<K, V> {
        K node;
        V level;

        Pair(K node, V level) {
            this.node = node;
            this.level = level;
        }
    }
    
}
