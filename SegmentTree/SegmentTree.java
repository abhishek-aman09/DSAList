package SegmentTree;

import java.util.Arrays;

public class SegmentTree {
    private static int tree[];
    private static int n;

    /*
     * Properties of segment tree
     * You can do range and update operation in log n
     * In memory it is stored as array.
     * left child = 2 * i + 1. right child = 2 * i + 2. parent = floor((i - 1)/2); like heap
     */
    
    private static void buildSegmentTree(int arr[]) {
        n = arr.length;

        int treeSize = 4 * n;
        tree = new int[treeSize];
        Arrays.fill(tree, -1);

        construct(arr, tree, 0, arr.length - 1, 0);
    }

    private static int construct(int arr[], int[] tree, int start, int end, int treeIndex) {
        if (start > end) {
            return 0;
        }
        if (start == end) {
            tree[treeIndex] = arr[start];
            return arr[start];
        }

        int mid = (start + end) / 2;

        // value at i is sum of its left and right children.

        tree[treeIndex] = construct(arr, tree, start, mid, 2 * treeIndex + 1) +
                construct(arr, tree, mid + 1, end, 2 * treeIndex + 2);

        return tree[treeIndex];
    }

    private static void rangeSum(int l, int r) {
        System.out.println( "Sum of range is " + getSum(l, r, tree, 0, n - 1, 0));
    }
    
    /*
    tl = treeLeft, tr = treeRight, ti = treeIndex.
     * tl and tr will be the range for which the node holds the sum.
     * ti will be the corresponding index in the tree for which tl and tr holds the range
     */
    private static int getSum(int l, int r, int segTree[], int tl, int tr, int ti) {
        // if treeLeft is more than right index or
        // if treeRight is less than left index, then we are going out of bounds
        if (tl > r || tr < l) {
            return 0;
        }

        // if treeLeft and treeRight is in range of left and right, return the sum i.e node's value

        if (tl >= l && tr <= r) {
            return segTree[ti];
        }

        // else if tl < l or tr > r, we recursively call to get the sum for left and right child
        int mid = (tl + tr) / 2;

        return getSum(l, r, segTree, tl, mid, 2 * ti + 1) +
                getSum(l, r, segTree, mid + 1, tr, 2 * ti + 2);
    }

    private static void updateIndex(int index, int value, int arr[]) {
        int diff = value - arr[index];
        arr[index] = value;

        update(index, diff, 0, n - 1, 0);
    }
    
    private static void update(int index, int value, int treeLeft, int treeRight, int treeIndex) {
        // if index is not included in current range, skip it
        if (treeRight < index || treeLeft > index) {
            return;
        }
        
        tree[treeIndex] += value;

        // if both matches and equal to index, we will have an infinite recursive call,
        // hence we return for this condition.
        if (treeLeft == treeRight) {
            return;
        }

        int mid = (treeLeft + treeRight) / 2;

        update(index, value, treeLeft, mid, 2 * treeIndex + 1);
        update(index, value, mid + 1,treeRight,2 * treeIndex + 2);
    }

    public static void main(String[] args) {
        int arr[] = { 10, 20, 30, 40, 50, 60 };

        buildSegmentTree(arr);
        rangeSum(0, 3);
        rangeSum(1, 2);
        rangeSum(2, 5);

        updateIndex(3, 100, arr);
        updateIndex(5, 10, arr);

        for (int el : arr) {
            System.out.print(el + "   ");
        }
         
        System.out.println();

        for (int el : tree) {
            System.out.print(el + "  ");
        }
    }
}
