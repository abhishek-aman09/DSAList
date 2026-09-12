package BinarySearch;

public class SearchInRotatedArrayII {
    /*
    https://leetcode.com/problems/search-in-rotated-sorted-array-ii/description/
    There is an integer array nums sorted in non-decreasing order (not necessarily with distinct values).
    
    Before being passed to your function, nums is rotated at an unknown pivot index k (0 <= k < nums.length) such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed). For example, [0,1,2,4,4,4,5,6,6,7] might be rotated at pivot index 5 and become [4,5,6,6,7,0,1,2,4,4].
    
    Given the array nums after the rotation and an integer target, return true if target is in nums, or false if it is not in nums.
    
    You must decrease the overall operation steps as much as possible.
    
    Input: nums = [2,5,6,0,0,1,2], target = 0
    Output: true
    
    Approach : 
    
    Issue lies for inputs like [1,1,0,1,1,1,1,1] and [1,1,1,1,0,1,1]
    
    we simply cannot consider if there exist a pivot simply by
    comparing first and last element.
    
    we first check the mid of the array, if it matches the end element, 
    we recursively try to find the pivot in left and right part using
    findRecPivot method. The pivot may or may not be there.

    lastly check if pivot exist and call getItem accordingly.
    
    */

    public boolean search(int[] nums, int target) {

        int n = nums.length;

        // QUICK BOUNDARY CHECKS:
        // Immediate early exit if target matches either endpoint.
        if (target == nums[0])
            return true;

        if (target == nums[n - 1])
            return true;

        int pivot = -1;

        // HEURISTIC CHECK FOR DUPLICATES:
        // If mid-element differs from rightmost element, standard binary search works.
        // Otherwise, duplicates might hide the inflection point, so recurse.
        if (nums[(n - 1) / 2] != nums[n - 1]) {
            pivot = findPivot(nums, 0, n - 1);
        } else {
            pivot = findRecPivot(nums, 0, n - 1);
        }

        // If no pivot was identified, search the whole array.
        if (pivot == -1)
            return getIndex(nums, 0, n - 1, target);

        // Search the two independently sorted subarrays split at 'pivot'.
        return getIndex(nums, 0, pivot - 1, target) || getIndex(nums, pivot, n - 1, target);
    }

    // RECURSIVE PIVOT SEARCH:
    // Used when duplicate values obscure which subarray contains the rotation drop.
    private int findRecPivot(int nums[], int l, int r) {
        if (l >= r) {
            return -1;
        }

        int mid = l + (r - l) / 2;

        // If mid matches the right boundary, the drop could be in either half:
        // e.g., [1, 1, 1, 0, 1] vs [1, 0, 1, 1, 1]. Recurse into both.
        if (nums[mid] == nums[r]) {
            int left = findRecPivot(nums, l, mid);
            int right = findRecPivot(nums, mid + 1, r);

            if (left != -1) {
                return left;
            }
            return right;
        } else {
            // Once the ambiguity is broken, delegate back to iterative binary search.
            return findPivot(nums, l, r);
        }
    }

    // ITERATIVE PIVOT FINDER:
    // Finds the index of the smallest element in a rotated sorted array without duplicates.
    private int findPivot(int nums[], int l, int r) {

        int pivot = -1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            // If mid <= r, the inflection point lies at or to the left of mid.
            if (nums[mid] <= nums[r]) {
                if (pivot == -1 || nums[pivot] >= nums[mid]) {
                    pivot = mid;
                }
                r = mid - 1; // Shrink search range to the left
            } else {
                // Drop occurs in the right half
                l = mid + 1;
            }
        }

        return pivot;
    }

    private boolean getIndex(int nums[], int l, int r, int target) {

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (nums[mid] == target) {
                return true;
            }

            if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return false;
    }
    
    public static void main(String[] args) {
        SearchInRotatedArrayII obj = new SearchInRotatedArrayII();

        int arr[] = { 2, 2, 2, 0, 2, 2 };

        System.out.println(obj.search(arr, 0));
    }
    
}
