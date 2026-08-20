package Google.BinarySearch;

public class FindSmallestInSortedRotated {

    /*
    https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/
    
    Suppose an array of length n sorted in ascending order is rotated between 1 and n times. For example, the array nums = [0,1,4,4,5,6,7] might become:
    
    [4,5,6,7,0,1,4] if it was rotated 4 times.
    [0,1,4,4,5,6,7] if it was rotated 7 times.
    Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
    
    Given the sorted rotated array nums that may contain duplicates, return the minimum element of this array.
    
    You must decrease the overall operation steps as much as possible.
    
    Input: nums = [2,2,2,0,1]
    Output: 0
    
    approach : slight modification of search in rotated array 2.

    Issue lies for inputs like [1,1,0,1,1,1,1,1] and [1,1,1,1,0,1,1]
    
    we simply cannot consider if there exist a pivot simply by
    comparing first and last element.
    
    we first check the mid of the array, if it matches the end element, 
    we recursively try to find the pivot in left and right part using
    findRecPivot method. The pivot may or may not be there.
    
    
    */

    private static final int NOT_FOUND = Integer.MAX_VALUE / 100;
    
    public int findMin(int[] nums) {

        int l = 0;
        int  r = nums.length - 1;

        int minEl = findPivotRecursively(l, r, nums); // simply call find pivote recursively for the whole array

        if (minEl == NOT_FOUND) { // if no pivot exist, i.e array is not sorted, return the first element
            return nums[0];
        }

        return minEl;

    }

    private int findPivotRecursively(int l, int r, int[] nums) {

        if(l >= r) { // if indices go out of bound, return pivot not found
            return NOT_FOUND;
        }

        int mid = l + (r - l) / 2;

        if (nums[mid] == nums[r]) { // we check if the middle element matches the last, if yes, we again recursively call the left and right part
            int left = findPivotRecursively(l, mid, nums);
            int right = findPivotRecursively(mid + 1, r, nums);

            if (left != NOT_FOUND) {
                return left;
            }
            return right;
        } else { // else call normal find rcursive method
            return findPivot(l, r, nums);
        }
    }
    
    // normal method to find pivot in an array.
    private int findPivot(int l, int r, int[] nums) {

        int minEl = NOT_FOUND;

        while (l <= r) {

            int mid = l + (r - l) / 2;

            if (nums[mid] >= nums[r]) {
                l = mid + 1;
            } else {
                minEl = Integer.min(minEl, nums[mid]);
                r = mid - 1;
            }
        }

        return minEl;

    }
    
    public static void main(String[] args) {
        FindSmallestInSortedRotated obj = new FindSmallestInSortedRotated();

        int nums[] = new int[] { 1, 2, 0, 0, 1 };

        System.out.println(obj.findMin(nums));
    }
}
