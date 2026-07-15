package Google.BinaryTree;

import java.util.HashMap;
import java.util.Map;

public class NumOfPathSumEqualsTarget {
    
    public int pathSum(TreeNode root, int targetSum) {

        Map<Long, Integer> freqPrefixMap = new HashMap<>();

        long prefix = 0l;

        int countSum[] = new int[1];

        getSum(root, countSum, targetSum, prefix, freqPrefixMap);
    
        return countSum[0];
        
    }

    private void getSum(TreeNode root, int[] countSum, int targetSum, long prefix, Map<Long, Integer> freqPrefixMap) {
        if (root == null) {
            return;
        }

        freqPrefixMap.put(prefix, freqPrefixMap.getOrDefault(prefix, 0) + 1);

        prefix += root.val;

        getSum(root.left, countSum, targetSum, prefix, freqPrefixMap);

        getSum(root.right, countSum, targetSum, prefix, freqPrefixMap);

        long rem = prefix - targetSum;

        if (freqPrefixMap.containsKey(rem)) {
            countSum[0] += freqPrefixMap.get(rem);
        }

        prefix -= root.val;

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
