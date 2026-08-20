package BinarySearch;

import java.util.HashSet;
import java.util.Set;

public class MaximumLengthOfRepeatedSubarray {

    /*
    https://leetcode.com/problems/maximum-length-of-repeated-subarray/description/
    
    Given two integer arrays nums1 and nums2, return the maximum length of a subarray that appears in both arrays.
    
    Input: nums1 = [1,2,3,2,1], nums2 = [3,2,1,4,7]
    Output: 3
    Explanation: The repeated subarray with maximum length is [3,2,1].
    
    Approach : Run binary search on size of the subarray, for any size mid, check if there are any subarrays that exist in both
    to check subarray of len size, use rolling hash, compute and put into set for nums1 and compute and check of hash in nums2.
    */

    public int findLength(int[] nums1, int[] nums2) {

        int n = nums1.length;
        int m = nums2.length;

        int l = 1, r = Math.min(n, m);

        int maxLen = 0;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            boolean isCurrSizePossible = getMaxLenOfCurrSize(mid, nums1, nums2);

            if (isCurrSizePossible) { // if same array of this size does exist, we check for the greater sizes
                maxLen = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }

        }

        return maxLen;

    }

    // use as big prime number as possible to avoid collision.
    // this block we create hash for all subarrays of len size in nums1 and put them into set. we do the same for nums2 and check if its hash already exist in the array
    private boolean getMaxLenOfCurrSize(int size, int nums1[], int nums2[]) {

        Set<Long> set = new HashSet<>();

        long removeVal = 1l;
        final long prime = 211l;
        final long MOD = 1000000007l;
        long hash = 0;

        // compute hash of all possible subarray of lenght size for nums1 and put it into set
        for (int i = 0; i < size; i++) {
            hash = (hash * prime + nums1[i]) % MOD;
            if (i < size - 1) {
                removeVal = (removeVal * prime) % MOD;
            }
        }

        set.add(hash);
        // formula for computing the new hash is basically newHash = ((oldHash - prime ^ (size -1)) * prime + newElement) % MOD
        for (int i = size; i < nums1.length; i++) {
            long leftVal = (nums1[i - size] * removeVal) % MOD; // calculate left val that need to be removed

            hash = (hash - leftVal + MOD) % MOD; // remove the left val and add MOD to make value positive if it goes negative

            hash = (hash * prime + nums1[i]) % MOD; // multipy hash with prime to balance the index shift and add new variable

            set.add(hash); // put new hash into the set
        }

        // compute hash of all subarrays of len size for nums2 and check if they exist

        hash = 0;
        
        for (int i = 0; i < size; i++) {
            hash = (hash * prime + nums2[i]) % MOD;
        }

        // if set contains a hash of that size, return true
        if (set.contains(hash)) {
            return true;
        }

        for (int i = size; i < nums2.length; i++) {
            long leftVal = (nums2[i - size] * removeVal) % MOD;

            hash = (hash - leftVal + MOD) % MOD;

            hash = (hash * prime + nums2[i]) % MOD;

            if (set.contains(hash)) {
                return true;
            }
        }

        return false;
    }
    
    
}
