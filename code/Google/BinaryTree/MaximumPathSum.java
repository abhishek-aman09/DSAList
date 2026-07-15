package Google.BinaryTree;

public class MaximumPathSum {

    /*
    https://leetcode.com/problems/binary-tree-maximum-path-sum/description/
    
    A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge 
    connecting them. A node can only appear in the sequence at most once. 
    Note that the path does not need to pass through the root.
    
    The path sum of a path is the sum of the node's values in the path.
    
    Given the root of a binary tree, return the maximum path sum of any non-empty path.
    
    Input: root = [-10,9,20,null,null,15,7]
    Output: 42
    Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.
    
    Approach : recursive call left and right nodes, and store the max of all possibilities into a max variable
    
    all possibilities : l, r, c, l + c, r + c and l + r + c
    
    return only the max sum which passes through the root as current root will be considered to parent call.

    at last return max variable
    */

    private static int MIN = Integer.MIN_VALUE / 10000;
    public int maxPathSum(TreeNode root) {

        int[] maxSum = new int[]{ MIN };

        getMaxSum(root, maxSum);

        return maxSum[0];
    }

    private int getMaxSum(TreeNode root, int[] maxSum) {
        if (root == null) {
            return MIN;
        }

        // recursively call left and right child
        int left = getMaxSum(root.left, maxSum);
        int right = getMaxSum(root.right, maxSum);

        int curr = root.val;

        // check max from all possible combinations
        maxSum[0] = Math.max(maxSum[0], 
                        Integer.max(left, 
                            Integer.max(right, 
                                Integer.max(left + curr, 
                                    Integer.max(curr,
                                        Integer.max(right + curr, right + curr + left))))));
                                                
        // return max only through the current root path
        return Integer.max(curr + left, Integer.max(curr, curr + right));

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
    
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        MaximumPathSum obj = new MaximumPathSum();

        System.out.println(obj.maxPathSum(root));
    }
}
