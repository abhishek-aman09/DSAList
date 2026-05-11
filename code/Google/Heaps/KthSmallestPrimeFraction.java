package Google.Heaps;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class KthSmallestPrimeFraction {
    
    /*
    https://leetcode.com/problems/k-th-smallest-prime-fraction/description/
    
    You are given a sorted integer array arr containing 1 and prime numbers, where all the integers of arr are unique. You are also given an integer k.
    For every i and j where 0 <= i < j < arr.length, we consider the fraction arr[i] / arr[j].
    Return the kth smallest fraction considered. Return your answer as an array of integers of size 2, where answer[0] == arr[i] and answer[1] == arr[j].
    
    Example 1:
    
    Input: arr = [1,2,3,5], k = 3
    Output: [2,5]
    Explanation: The fractions to be considered in sorted order are:
    1/5, 1/3, 2/5, 1/2, 3/5, and 2/3.
    The third fraction is 2/5.
    
    Approach : similar to merge K sorted arrays.
    for each array the numerator will be fixed and we will
    change the denominator.
    time =  O(K(log N)) space = O(N)
    */

    public int[] kthSmallestPrimeFraction(int[] arr, int k) {

        int n = arr.length;

        if (n == 2) {
            return arr;
        }

        // Sorted on the fraction, smallest fraction on top
        PriorityQueue<int[]> numWithPos = new PriorityQueue<>(
                Comparator.comparingDouble(a -> ((double) a[0]) / a[1]));

        // as num is fixed, push the pos of curr denom
        // as array is sorted in ascending order, we can
        // decrease the denom ind, that will increase the 
        // fraction
        for (int i = 0; i < n - 1; i++) {
            numWithPos.offer(new int[] { arr[i], arr[n - 1], n - 1 });
        }

        int count = 0;
        int ans[] = {};

        while (count < k) {
            // poll the top
            int[] curr = numWithPos.poll();
            int numerator = curr[0];
            int denominator = curr[1];
            int denomPos = curr[2];

            count++;

            if (count == k) {
                ans = new int[] { numerator, denominator };
                break;
            }

            // if we have more fraction with curr num, push it into the queue
            if (arr[denomPos - 1] != numerator) {
                numWithPos.offer(new int[] { numerator, arr[denomPos - 1], denomPos - 1 });
            }

        }

        return ans;

    }
    
    public static void main(String[] args) {
        KthSmallestPrimeFraction obj = new KthSmallestPrimeFraction();

        int arr[] = { 1, 2, 3, 5 };
        int k = 3;

        Arrays.stream(obj.kthSmallestPrimeFraction(arr, k)).forEach(el -> System.out.print(el + "  "));
        System.out.println();
    }

}
