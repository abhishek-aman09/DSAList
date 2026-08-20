package Google.BinarySearch;


public class SplitArrayLargestSum {

    /*
    https://leetcode.com/problems/split-array-largest-sum/description/
    
    Given an integer array nums and an integer k, split nums into k non-empty subarrays such that the largest sum of any subarray is minimized.
    
    Return the minimized largest sum of the split.
    
    A subarray is a contiguous part of the array.
    
    Input: nums = [7,2,5,10,8], k = 2
    Output: 18
    Explanation: There are four ways to split nums into two subarrays.
    The best way is to split it into [7,2,5] and [10,8], where the largest sum among the two subarrays is only 18.
    
    Approach : create a sum variable, and apply binary search on solution space.
    where right could be the sum of all the elements. (as array contains non negative elements, sum will be always increasing)
    
    take a mid, check if the array could be split in k subarrays where sum of any subarray is less than equal to mid.
    if yes, store it and check for left subpart, else check for right.
    
    
    */
    
    public int splitArray(int[] nums, int k) {

        int sum = 0;

        for (int el : nums) { // create the sum value
            sum += el;
        }

        int left = 0, right = sum;

        int maxSum = Integer.MAX_VALUE;

        while (left <= right) { // Apply binary search on space
            int mid = left + (right - left) / 2;

            boolean isPartitionPossible = checkPartitionPossible(nums, mid, k); // for any mid, check if partition is possible

            if (isPartitionPossible) { // if yes, store in maxSum, check for left subspace
                maxSum = mid;
                right = mid - 1;
            } else { // else check for right
                left = mid + 1;
            }
        }

        return maxSum;
    }

    private boolean checkPartitionPossible(int[] nums, int sum, int partitions) {

        int currSum = 0;

        for (int i = 0; i < nums.length; i++) {
            currSum += nums[i]; // continue adding the curr element 

            if (nums[i] > sum) { // if a number itself is bigger than max sum, return false
                return false;
            }

            if (currSum > sum) { // if sum goes beyond limit, start a new partition and decrease the num of partitions left
                currSum = nums[i];
                partitions--;
            }

            if (partitions == 0) { // if we have reached to a state where array has been partitioned into k parts, return false
                return false;
            }
        }

        return true;
    }
    
    public static void main(String[] args) {
        SplitArrayLargestSum obj = new SplitArrayLargestSum();

        int arr[] = new int[] { 7, 2, 5, 10, 8 };

        System.out.println(obj.splitArray(arr, 2));
    }
}
