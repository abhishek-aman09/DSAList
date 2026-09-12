package Array.PrefixSum;

public class FindPivotSum {

    /* https://leetcode.com/problems/find-pivot-index/
    
    Given an array of integers nums, calculate the pivot index of this array.
    
    The pivot index is the index where the sum of all the numbers strictly to the left of the index is equal to the sum of all the numbers strictly to the index's right.
    
    If the index is on the left edge of the array, then the left sum is 0 because there are no elements to the left. This also applies to the right edge of the array.
    
    Return the leftmost pivot index. If no such index exists, return -1.
    
    
    Input: nums = [1,7,3,6,5,6]
    Output: 3
    Explanation:
    The pivot index is 3.
    Left sum = nums[0] + nums[1] + nums[2] = 1 + 7 + 3 = 11
    Right sum = nums[4] + nums[5] = 5 + 6 = 11
    
    Do not include el at pivot, also if multiple answer exist,
    return first possible index.
    
    approach : create a prefix sum array. Special condition for first and last index
    for first index, if the prefix sum on last (n - 1) element is equal to the prefix sum of first element, it means the net sum of the rest of the array excluding first is zero, so first is pivot
    for last index, if the prefix sum till second last index is zero, then we have pivot at last element

    for other cases, compare prefix sum from 0 to i -1 and i + 1 to n - 1.
    
    */
    
    public int pivotIndex(int[] nums) {

        int n = nums.length;

        int pre[] = new int[n];

        pre[0] = nums[0];

        // prefix array
        for (int i = 1; i < n; i++) {
            pre[i] = pre[i - 1] + nums[i];
        }

        // if prefix of last is equal to first, first is pivot
        if (pre[n - 1] == pre[0]) {
            return 0;
        }

        // general case, prefix of left == prefix of right
        for (int i = 1; i < n - 1; i++) {
            if (pre[i - 1] == pre[n - 1] - pre[i]) {
                return i;
            }
        }

        // if prefix of second last is 0, last is prefix
        if (pre[n - 2] == 0) {
            return n - 1;
        }

        return -1;
    }
    
}
