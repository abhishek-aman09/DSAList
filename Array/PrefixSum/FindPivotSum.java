package Array.PrefixSum;

public class FindPivotSum {

    /* https://leetcode.com/problems/find-pivot-index/
    Input: nums = [1,7,3,6,5,6]
    Output: 3
    Explanation:
    The pivot index is 3.
    Left sum = nums[0] + nums[1] + nums[2] = 1 + 7 + 3 = 11
    Right sum = nums[4] + nums[5] = 5 + 6 = 11
 
    Do not include el at pivot, also if multiple answer exist,
    return first possible index.

    */
    
    public int pivotIndex(int[] nums) {

        int n = nums.length;

        int pre[] = new int[n];

        pre[0] = nums[0];

        if (n == 1) {
            return 0;
        }

        // for conditions like [0, 0] or [1, 0]
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

        // for sample like [2, 1, -1] i.e. sum of 1 - n is 0
        if (pre[n - 1] - pre[0] == 0) {
            return 0;
        }

        for (int i = 1; i < n - 1; i++) {
            if (pre[i - 1] == pre[n - 1] - pre[i]) {
                return i;
            }
        }

        // for condition like [1, -1, 2] i.e sum of 0 -> n - 1 is 0
        if (pre[n - 2] == 0) {
            return n - 1;
        }

        return -1;
    }
    
}
