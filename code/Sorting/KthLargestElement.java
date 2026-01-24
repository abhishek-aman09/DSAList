package Sorting;

import java.util.concurrent.ThreadLocalRandom;

public class KthLargestElement {
    
    // https://leetcode.com/problems/kth-largest-element-in-an-array/description/


    /*
    Given an integer array nums and an integer k, return the kth largest element in the array.
    
    Note that it is the kth largest element in the sorted order, not the kth distinct element.
    
    Input: nums = [3,2,1,5,6,4], k = 2
    Output: 5
    
    
    Approach:
    1.  Using quick select algorithm : take l = 0 and r = n - 1.
        call lomuto partitioning on range, if the returned ind is less than k,
        move l to ind + 1 else move r to ind - 1. 
        TC : lomuto takes O(n) and at most n ranges can be checked. In worst
        case, time complexity would be N^2 (if array have multiple duplicate elements).
        SC : constant 
    
    2.  Using two way partitioning (Dutch national flag).
        Select pivot randomly and replace it with last element.
        perform DNF algo, you will have l and r pointers. (include the nums[right])
        elements left to l will be less than pivot while el to right will be large.
    
        return the left and right pointer.
        if returned l is less than k and r is greater. k lies in the sorted range.
        return nums[k].
        if k is less than l, move r to right - 1, else l to left + 1.
    
        TC : O(n) at most two times array will be sorted.
        SC : constant.
          
    */

    public int findKthLargest(int[] nums, int k) {

        int n = nums.length;

        int l = 0, r = n - 1;

        k = n - k;

        while (l <= r) {

            int currIndex[] = threeWayPartitioning(nums, l, r);

            if (k >= currIndex[0] && k <= currIndex[1]) {
                return nums[k];
            }

            if (k < currIndex[0]) {
                r = currIndex[0] - 1;
            } else {
                l = currIndex[1] + 1;
            }

        }

        return -1;

    }
    
    private int[] threeWayPartitioning(int nums[], int l, int r) {

        int randomIndex = ThreadLocalRandom.current().nextInt(l, r + 1);

        swap(nums, randomIndex, l);

        int pivot = nums[l];
        int lt = l;
        int gt = r;
        int ind = l + 1;

        while (ind <= gt) {
            if (nums[ind] < pivot) {
                swap(nums, ind, lt);
                lt++;
                ind++;
            } else if (nums[ind] > pivot) {
                swap(nums, ind, gt);
                gt--;
            } else {
                ind++;
            }
        }

        return new int[] { lt, gt };

    }
    
    private void swap(int nums[], int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    private int getIndexUsingLomutoPartitioning(int nums[], int l, int r) {

        int randomIndex = ThreadLocalRandom.current().nextInt(l, r + 1);

        int temp;

        temp = nums[randomIndex];
        nums[randomIndex] = nums[r];
        nums[r] = temp;

        int j = l - 1;

        for (int i = l; i < r; i++) {
            if (nums[i] <= nums[r]) {
                j++;
                temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }

        j++;

        temp = nums[j];
        nums[j] = nums[r];
        nums[r] = temp;

        return j;

    }
    
    public static void main(String[] args) {
        
        KthLargestElement obj = new KthLargestElement();

        int arr[] = {3,2,1,5,6,4};

        System.out.println(obj.findKthLargest(arr, 2));
    }
}
