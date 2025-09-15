package BinaryTree;
import java.util.ArrayList;
import java.util.Arrays;

public class KthAncestor {
    // https://leetcode.com/problems/kth-ancestor-of-a-tree-node/submissions/1663751173/?envType=problem-list-v2&envId=tree

    private final int n;
    private final int parent[];
    private final int ancestor[][];

    public KthAncestor(int n, int[] parent) {
        this.n = n;
        this.parent = parent;
        ancestor = new int[n][30];

        preComputeAncestors();
    }

    /*
    concept of binary lifting is used here.
    k is represented as binary 1011010... 
    if the ith bit of k is set we move to the ansector[node][i] steps up.
     */
    public int getKthAncestor(int node, int k) {

        for (int i = 0; i < 30; i++) {
            if (((k >> i) & 1) != 0) {
                node = ancestor[node][i];
                if (node == -1)
                    return -1;
            }
        }

        return node;
    }

    public void preComputeAncestors() {

        for (int i = 0; i < n; i++) {
            Arrays.fill(ancestor[i], -1);
        }

        // Base case: 2^0th i.e 1st ancestor is the parent itself
        for (int i = 0; i < n; i++) {
            ancestor[i][0] = parent[i];
        }

        // we will fill the ancestor grid for in order of power of 2
        // for each node, we check if lastAncestor exist
        // if it does, we assign its j - 1 value to current i,j as 
        // it will take us 2^j + 2^(j - 1) moves to reach there.

        for (int j = 1; j < 30; j++) {
            for (int i = 1; i < n; i++) {
                int lastAncestor = ancestor[i][j - 1];

                if (lastAncestor != -1) {
                    ancestor[i][j] = ancestor[lastAncestor][j - 1];
                }
            }
        }
    }

    public static void main(String[] args) {
        int arr[] = { -1, 0, 0, 1, 1, 2, 2 };

        KthAncestor obj = new KthAncestor(7, arr);
    }

}
