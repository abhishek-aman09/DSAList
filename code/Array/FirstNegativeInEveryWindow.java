package Array;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FirstNegativeInEveryWindow {

    /*
    https://www.geeksforgeeks.org/problems/first-negative-integer-in-every-window-of-size-k3345/1
    
    Given an array arr[]  and a positive integer k, find the first negative integer for each and every window(contiguous subarray) of size k.
    
    Note: If a window does not contain a negative integer, then return 0 for that window.
    
    Examples:
    
    Input: arr[] = [-8, 2, 3, -6, 10] , k = 2
    Output: [-8, 0, -6, -6]
    
    approach : 
    queue based : similar to max number in window of size K, push from back, and get from from. invalidate elements from front who are out of window from front
    two pointer : keep left = 0 and right at k - 1. for each window increment left, store the first index and break, if l == r, no negative elements
    */
    
    static List<Integer> firstNegInt(int arr[], int k) {

        int n = arr.length;

        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();

        int i = 0;

        List<Integer> ans = new ArrayList<>();
        if (n < k) {
            return ans;
        }

        while (i < k) {
            if (arr[i] < 0) {
                queue.add(new Pair<Integer, Integer>(arr[i], i));
            }
            i++;
        }

        if (queue.isEmpty()) {
            ans.add(0);
        } else {
            ans.add(queue.peek().first);
        }

        int left = 0;

        for (; i < n; i++) {

            while (!queue.isEmpty() && queue.peek().second <= left) {
                queue.poll();
            }

            left++;
            if (arr[i] < 0) {
                queue.add(new Pair<Integer, Integer>(arr[i], i));
            }

            if (queue.isEmpty()) {
                ans.add(0);
            } else {
                ans.add(queue.peek().first);
            }
        }

        return ans;

    }
    
    static class Pair<K,V> {
        
        K first;
        V second;

        Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }
        
    }
    
}
