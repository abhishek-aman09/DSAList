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

Input: arr[] = [12, -1, -7, 8, -15, 30, 16, 28] , k = 3
Output: [-1, -1, -7, -15, -15, 0] 
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
