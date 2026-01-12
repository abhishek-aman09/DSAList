package Array;

public class PivotIndex {

    /*
    https://leetcode.com/problems/find-pivot-index/description/
    Given an array of integers nums, calculate the pivot index of this array.

    The pivot index is the index where the sum of all the numbers strictly to the left of the index is equal to the sum of all the numbers strictly to the index's right.

    If the index is on the left edge of the array, then the left sum is 0 because there are no elements to the left. This also applies to the right edge of the array.

    Return the leftmost pivot index. If no such index exists, return -1.
    */
    
    public int pivotIndex(int[] nums) {

        int n = nums.length;

        int pre[] = new int[n];

        pre[0] = nums[0];
        // special condition for n = 1
        if (n == 1) {
            return 0;
        }
        // condition for n = 2
        // return the first occurence
        if (n == 2) {
            if (nums[1] == 0) {
                return 0;
            }
            if (nums[0] == 0) {
                return 1;
            }  
        }

        for (int i = 1; i < n; i++) {
            pre[i] = pre[i - 1] + nums[i];
        }

        // condition to check if 0 could be the pivot index
        if (pre[n - 1] - pre[0] == 0) {
            return 0;
        }

        // condition to check if pivot exist between 1 and n - 2
        for (int i = 1; i < n - 1; i++) {
            if (pre[i - 1] == pre[n - 1] - pre[i]) {
                return i;
            }
        }

        // condition to check if pivot is n - 1
        if (pre[n - 2] == 0) {
            return n - 1;
        }

        return -1;
    }

    public static void main(String[] args) {
        PivotIndex obj = new PivotIndex();

        int arr[] = {-1, 1, 2};

        System.out.println(obj.pivotIndex(arr));
    }

}
