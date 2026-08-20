package BinarySearch;

import java.util.HashSet;
import java.util.Set;

public class LongestCommonSubpath {

    /*
    https://leetcode.com/problems/longest-common-subpath/description/
    
    There is a country of n cities numbered from 0 to n - 1. In this country, there is a road connecting every pair of cities.
    
    There are m friends numbered from 0 to m - 1 who are traveling through the country. Each one of them will take a path consisting of some cities. Each path is represented by an integer array that contains the visited cities in order. The path may contain a city more than once, but the same city will not be listed consecutively.
    
    Given an integer n and a 2D integer array paths where paths[i] is an integer array representing the path of the ith friend, return the length of the longest common subpath that is shared by every friend's path, or 0 if there is no common subpath at all.
    
    A subpath of a path is a contiguous sequence of cities within that path.
    
    Input: n = 5, paths = [[0,1,2,3,4],
                       [2,3,4],
                       [4,0,1,2,3]]
    Output: 2
    Explanation: The longest common subpath is [2,3].
    
    Approach : Similar to generate all substring of len size by rolling Hash (check MaximumLenOfRepeatedSubstring). only difference is we calculate all the hash for first path.
    All the next path onwards we will filter the common paths in the set, so that, at the end, subPath common to all the paths remains.
    Also, we use double hashing, single hashing will cause heavy collision
    
    
    */
    // double hashing
    private static final long PRIME1 = 100003L;
    private static final long MOD1   = 1000000007L;

    private static final long PRIME2 = 100019L;
    private static final long MOD2 = 1000000009L;


    public int longestCommonSubpath(int n, int[][] paths) {

        int l = 1;

        int r = Integer.MAX_VALUE;

        for (int path[] : paths) { // we run binary search block till the length of shortest path
            r = Integer.min(path.length, r);
        }

        int maxCommonLen = 0;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            boolean isCommonLenPossible = checkCommonLen(mid, paths);

            if (isCommonLenPossible) {
                maxCommonLen = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return maxCommonLen;

    }
    
    private boolean checkCommonLen(int size, int[][] paths) {

        Set<Long> hashSet = new HashSet<>();

        computeAndPut(size, paths[0], hashSet); // first compute all hash of first path and put into set

        for (int i = 1; i < paths.length; i++) {
            hashSet = computeAndCheck(size, paths[i], hashSet); // next path onwards, only return the common path in the set

            if (hashSet.isEmpty()) { // if we have no common path, return false
                return false;
            }
        }

        return true;
    }

    // block to compute all hash of path 1 and put into set using double hashing
    private void computeAndPut(int size, int[] nums1, Set<Long> set) {

        long removeVal1 = 1L, removeVal2 = 1L;
        long hash1 = 0L, hash2 = 0L;

        // 1. Initial window
        for (int i = 0; i < size; i++) {
            hash1 = (hash1 * PRIME1 + nums1[i]) % MOD1;
            hash2 = (hash2 * PRIME2 + nums1[i]) % MOD2;

            if (i < size - 1) {
                removeVal1 = (removeVal1 * PRIME1) % MOD1;
                removeVal2 = (removeVal2 * PRIME2) % MOD2;
            }
        }

        set.add(combine(hash1, hash2));

        // 2. Slide the window
        for (int i = size; i < nums1.length; i++) {
            long leftVal1 = (nums1[i - size] * removeVal1) % MOD1;
            long leftVal2 = (nums1[i - size] * removeVal2) % MOD2;

            hash1 = (hash1 - leftVal1 + MOD1) % MOD1;
            hash2 = (hash2 - leftVal2 + MOD2) % MOD2;

            hash1 = (hash1 * PRIME1 + nums1[i]) % MOD1;
            hash2 = (hash2 * PRIME2 + nums1[i]) % MOD2;

            set.add(combine(hash1, hash2));
        }
    }

    // this block is for all the other path, we filter paths which are common
    private Set<Long> computeAndCheck(int size, int[] nums2, Set<Long> set) {
        Set<Long> newSet = new HashSet<>(); // set to store all new paths

        long removeVal1 = 1L, removeVal2 = 1L;
        long hash1 = 0L, hash2 = 0L;

        // 1. Initial window
        for (int i = 0; i < size; i++) {
            hash1 = (hash1 * PRIME1 + nums2[i]) % MOD1;
            hash2 = (hash2 * PRIME2 + nums2[i]) % MOD2;

            if (i < size - 1) {
                removeVal1 = (removeVal1 * PRIME1) % MOD1;
                removeVal2 = (removeVal2 * PRIME2) % MOD2;
            }
        }

        long combined = combine(hash1, hash2);
        if (set.contains(combined)) { // if this hash exist, we put into new
            newSet.add(combined);
        }

        // 2. Slide the window
        for (int i = size; i < nums2.length; i++) {
            long leftVal1 = (nums2[i - size] * removeVal1) % MOD1;
            long leftVal2 = (nums2[i - size] * removeVal2) % MOD2;

            hash1 = (hash1 - leftVal1 + MOD1) % MOD1;
            hash2 = (hash2 - leftVal2 + MOD2) % MOD2;

            hash1 = (hash1 * PRIME1 + nums2[i]) % MOD1;
            hash2 = (hash2 * PRIME2 + nums2[i]) % MOD2;

            combined = combine(hash1, hash2);
            if (set.contains(combined)) {
                newSet.add(combined);
            }
        }

        return newSet; // return new filtered hash set
    }
    
    private long combine(long h1, long h2) {
        return (h1 << 32) | (h2 & 0xFFFFFFFFL);
    }

    public static void main(String[] args) {
        int paths[][] = new int[][] { { 0, 1, 2, 3, 4 }, { 4, 3, 2, 1, 0 } };

        LongestCommonSubpath obj = new LongestCommonSubpath();

        System.out.println(obj.longestCommonSubpath(5, paths));
    }
    
}
