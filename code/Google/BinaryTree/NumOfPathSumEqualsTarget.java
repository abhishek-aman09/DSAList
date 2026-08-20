package Google.BinaryTree;

import java.util.HashMap;
import java.util.Map;

public class NumOfPathSumEqualsTarget {

    /*
    https://leetcode.com/problems/path-sum-iii/
    
    
    Given the root of a binary tree and an integer targetSum, return the number of paths where the sum of the values along the path equals targetSum.
    The path does not need to start or end at the root or a leaf, but it must go downwards (i.e., traveling only from parent nodes to child nodes).
    
    Input: root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
    Output: 3
    Explanation: The paths that sum to 8 are shown.
    
    Approach : similar to prefix sum and hash map
    first put the current prefix into the map, then add curr value to prefix
    
    recursively call the left and right subtree with update prefix
    
    then check if the rem i.e prefix - target exist in the map, if it does, add the frequency

    subtract the root.val from prefix and reduce its frequency by one
    
    
    */
    
    public int pathSum(TreeNode root, int targetSum) {

        Map<Long, Integer> freqPrefixMap = new HashMap<>(); // map to store prefix values along with its frequency

        long prefix = 0l; // variable to store current prefix

        int countSum[] = new int[1]; // counter of num of path

        getSum(root, countSum, targetSum, prefix, freqPrefixMap);
    
        return countSum[0];
        
    }

    private void getSum(TreeNode root, int[] countSum, int targetSum, long prefix, Map<Long, Integer> freqPrefixMap) {
        if (root == null) {
            return;
        }

        freqPrefixMap.put(prefix, freqPrefixMap.getOrDefault(prefix, 0) + 1); // add the curr prefix to the map

        prefix += root.val; // update the prefix with curr val

        // recursively call its left and right tree
        getSum(root.left, countSum, targetSum, prefix, freqPrefixMap);

        getSum(root.right, countSum, targetSum, prefix, freqPrefixMap);

        long rem = prefix - targetSum; // check if the remainder exist in the map

        if (freqPrefixMap.containsKey(rem)) {
            countSum[0] += freqPrefixMap.get(rem);
        }

        prefix -= root.val; // remove the altered value of prefix and adjust the frequency accordingly

        freqPrefixMap.put(prefix, freqPrefixMap.get(prefix) - 1);

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
