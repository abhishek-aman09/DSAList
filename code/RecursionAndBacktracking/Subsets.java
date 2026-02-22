package RecursionAndBacktracking;

import java.util.ArrayList;
import java.util.List;

public class Subsets {

    // https://leetcode.com/problems/subsets/

    /*
    Given an integer array nums of unique elements, return all possible subsets (the power set).
    
    The solution set must not contain duplicate subsets. Return the solution in any order.
    
    Example 1:
    Input: nums = [1,2,3]
    Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]
    
    */

    public List<List<Integer>> subsets(int[] nums) {
        
        int n = nums.length;
        // if array contains duplicate el and we want unique elements, sort the array
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();

        helper(nums, 0, n, temp, ans);

        return ans;
    }

    private void helper(int nums[], int i, int n, List<Integer> temp, List<List<Integer>> ans) {
        if (i >= n) {
            // create copy of current list
            List<Integer> list = List.copyOf(temp);
            ans.add(list);
            return;
        }

        // recursively call without adding the curr
        helper(nums, i + 1, n, temp, ans);
        // add the el
        temp.add(nums[i]);
        // recursively call after adding the curr
        helper(nums, i + 1, n, temp, ans);
        // remove the curr
        temp.remove((Integer)nums[i]);
    }
    
}
