package Google.BinarySearch;

import java.util.Arrays;

public class SumOfMutatedClosestToTarget {
    
    /*
    https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/description/
    
    Given an integer array arr and a target value target, return the integer value such that when we change all 
    the integers larger than value in the given array to be equal to value, the sum of the array gets as close as possible (in absolute difference) 
    to target.
    
    In case of a tie, return the minimum such integer.
    
    Notice that the answer is not neccesarilly a number from arr.
    
    
    Given an integer array arr and a target value target, return the integer value such that when we change all 
    the integers larger than value in the given array to be equal to value, the sum of the array gets as close as possible (in absolute difference) 
    to target.
    
    In case of a tie, return the minimum such integer.
    
    Notice that the answer is not neccesarilly a number from arr.
    
    
    Approach : Use binary search to search the number in solution space which is from 0 to largest number in array.
    for each element we will check how many numbers are smaller than the num, the sum of smaller numbers will be the prefix sum till that index.
    next the greater elements will be converted to curr. if the current sum is greater than target, we reduce the range else we increase
    
    at the last, left pointer will contain the closest greater number whose conversion sum is greater than the target, we then have only
    two candidtes, left and left - 1, i.e. closest to left and right, we check the min and return
    
    */

    public int findBestValue(int[] arr, int target) {

        int n = arr.length;

        Arrays.sort(arr); // sort the array

        int left = 0;
        int  right = arr[n - 1];

        long prefixSum[] = new long[n]; // form a prefix sum array

        long sum = 0;

        for (int i = 0; i < n; i++) {
            sum += arr[i];
            prefixSum[i] = sum;
        }

        while (left <= right) { // perform binary search
            int mid = left + (right - left) / 2;

            long currSum = getSum(arr, prefixSum, mid);

            if (currSum >= target) { 
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        int val1 = left; // closest element to the right of target
        int val2 = left - 1; // closest element to the left of target

        long sum1 = getSum(arr, prefixSum, val1);
        long sum2 = getSum(arr, prefixSum, val2);

        // Tie-breaker: choose the one with the smaller absolute difference.
        // If differences are equal, return val2 because val2 (left - 1) is strictly smaller than val1 (left).
        if (Math.abs(sum1 - target) >= Math.abs(sum2 - target)) {
            return val2;
        } else {
            return val1;
        }

    }

    // method to get the sum if array is mutated with mid
    private long getSum(int[] arr, long[] prefixSum, int mid) {
    int n = arr.length;
    int indexLessThanMid = countOfElementsLessThanCurr(arr, mid); // get num of elements with el less than mid
    
    if (indexLessThanMid == -1) { // if none are small, return n * mid
        return (long) n * mid;
    } else {
        long currSum = prefixSum[indexLessThanMid]; // else get prefixsum till ind and add rest
        currSum += (long) (n - indexLessThanMid - 1) * mid;
        return currSum;
    }
}
    
    private int countOfElementsLessThanCurr(int arr[], int currNum) {

        int l = 0;
        int r = arr.length - 1;

        int count = -1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (arr[mid] <= currNum) {
                count = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return count;
    }
    
    public static void main(String[] args) {
        SumOfMutatedClosestToTarget obj = new SumOfMutatedClosestToTarget();

        int arr[] = new int[] { 2, 3, 5 };

        System.out.println(obj.findBestValue(arr, 11));
    }

}
