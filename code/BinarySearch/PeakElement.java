package BinarySearch;

public class PeakElement {
    
    // https://leetcode.com/problems/find-peak-element/
    /*
    A peak element is an element that is strictly greater than its neighbors.
    
    Given a 0-indexed integer array nums, find a peak element, and return its index. 
    If the array contains multiple peaks, return the index to any of the peaks.
    
    You may imagine that nums[-1] = nums[n] = -∞. In other words, 
    an element is always considered to be strictly greater than a neighbor that is outside the array.
    
    You must write an algorithm that runs in O(log n) time.
    
    Approach (log n) : run binary search from 1 to n - 2;
    if an elemnt is greater than left but not right, peak is at right
    and vice versa
    
    
    */

    public int findPeakElement(int[] nums) {

        int n = nums.length;

        if(n == 1) {
            return 0;
        }

        if(nums[0] > nums[1]) {
            return 0;
        }

        if(nums[n - 1] > nums[n - 2]) {
            return n - 1;
        }

        int l = 1;
        int r = n - 2;

        while(l <= r) {
            int mid = l + (r - l) / 2;
            if(nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                return mid;
            }

            if(nums[mid] > nums[mid - 1]) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return 0;
        
    }
}
