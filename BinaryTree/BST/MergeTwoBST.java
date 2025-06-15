package BinaryTree.BST;

import java.util.ArrayList;
import java.util.List;

public class MergeTwoBST {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
     

    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> ans = new ArrayList<>();

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        preOrder(root2, list2);
        preOrder(root1, list1);

        mergeLists(list1, list2, ans);

        return ans;

    }

    void mergeLists(List<Integer> list1, List<Integer> list2, List<Integer> ans) {
        int n = list1.size();
        int m = list2.size();

        for(int el : list2) {
            System.out.println(el + "  ");
        }


        int i = 0, j = 0;

        while (i < n && j < m) {
            if (list1.get(i) < list2.get(j)) {
                ans.add(list1.get(i));
                i++;
            } else {
                ans.add(list2.get(j));
                j++;
            }
        }

        while (i < n) {
            ans.add(list1.get(i));
            i++;
        }

        while (j < m) {
            ans.add(list2.get(j));
            j++;
        }
    }
    

    public void preOrder(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }

        preOrder(root.left, list);

        list.add(root.val);

        preOrder(root.right, list);
    }

    

    
}