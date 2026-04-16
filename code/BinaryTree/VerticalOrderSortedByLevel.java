package BinaryTree;

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

        Queue<Pair<TreeNode, Integer>> q = new LinkedList<>();

        Map<Integer, TreeMap<Integer, List<Integer>>> map = new TreeMap<>();

        List<List<Integer>> ans = new LinkedList<>();

        q.add(new Pair<>(root, 0));
        q.add(null);

        int rowNum = 0;

        while(!q.isEmpty()) {
            Pair<TreeNode, Integer> curr = q.poll();

            if(curr == null) {
                if(!q.isEmpty()) {
                    q.add(null);
                }
                rowNum++;
                continue;
            }

            if(!map.containsKey(curr.level)) {
                map.put(curr.level, new TreeMap<>());
            }

            if(!map.get(curr.level).containsKey(rowNum)) {
                map.get(curr.level).put(rowNum, new ArrayList<>());
            }

            List<Integer> temp = map.get(curr.level).get(rowNum);
            temp.add(curr.node.val);

            map.get(curr.level).put(rowNum, temp);

            if(curr.node.left != null) {
                q.add(new Pair(curr.node.left, curr.level - 1));
            }

            if(curr.node.right != null) {
                q.add(new Pair(curr.node.right, curr.level + 1));
            }
        }

        for (Map.Entry<Integer, TreeMap<Integer, List<Integer>>> columnEntry : map.entrySet()) {

            List<Integer> columnNodes = new ArrayList<>();
            
            // Get the TreeMap representing rows for THIS column
            TreeMap<Integer, List<Integer>> rows = columnEntry.getValue();
            
            for (Map.Entry<Integer, List<Integer>> rowEntry : rows.entrySet()) {
                List<Integer> nodesInThisRowAndCol = rowEntry.getValue();
                
                // 1. Sort nodes that are at the EXACT same Row and Col
                Collections.sort(nodesInThisRowAndCol);
                
                // 2. Add them to the combined list for this column
                columnNodes.addAll(nodesInThisRowAndCol);
            }
            
            // 3. Add the fully assembled column to the final answer
            ans.add(columnNodes);
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
