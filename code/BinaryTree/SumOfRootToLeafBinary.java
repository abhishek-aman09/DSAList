package BinaryTree;

import java.util.ArrayList;
import java.util.List;

public class SumOfRootToLeafBinary {

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
 
    public int sumRootToLeaf(TreeNode root) {
        
        List<Integer> bits = new ArrayList<>();

        return helper(root, bits);
    }

    private int helper(TreeNode root, List<Integer> bits) {
        if(root == null) {
            return 0;
        }

        bits.add(root.val);

        if (root.left == null && root.right == null) {
            int ans = getNumFromBits(bits);
            bits.remove(bits.size() - 1);
            return ans;
        }

        
        int left = 0;
        int right = 0;

        if (root.left != null) {
            left = helper(root.left, bits);
        }

        if (root.right != null) {
            right = helper(root.right, bits);
        }
        
        bits.remove(bits.size() - 1);

        return left + right;
    }

    private int getNumFromBits(List<Integer> bits) {
        int n = bits.size() - 1;

        int ans = 0;

        n = (int) Math.pow(2, n);

        for (int el : bits) {
            ans += (el * n);
            n /= 2;
        }

        return ans;
    }
    
    public static void main(String[] args) {
        SumOfRootToLeafBinary obj = new SumOfRootToLeafBinary();

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(0);
        root.left.left = new TreeNode(0);
        root.left.right = new TreeNode(1);
        root.right = new TreeNode(1);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(1);

        System.out.println(obj.sumRootToLeaf(root));

    }
    
}
