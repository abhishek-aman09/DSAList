package Array.SlidingWindow;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class SlidingWindowMaximum {
    
    // https://leetcode.com/problems/sliding-window-maximum/description/
    /*
    You are given an array of integers nums, there is a sliding window of size k 
    which is moving from the very left of the array to the very right. 
    You can only see the k numbers in the window. 
    Each time the sliding window moves right by one position.
    
    Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
    Output: [3,3,5,5,6,7]
    Explanation: 
    Window position                Max
    ---------------               -----
    [1  3  -1] -3  5  3  6  7       3
    1 [3  -1  -3] 5  3  6  7       3
    1  3 [-1  -3  5] 3  6  7       5
    1  3  -1 [-3  5  3] 6  7       5
    1  3  -1  -3 [5  3  6] 7       6
    1  3  -1  -3  5 [3  6  7]      7
    
    Approach - Create a deque, first for window of size k, push all the elements from last, while pushing, pop out all the elements from back whose value is less or equal to than curr,
            we will have our first max in the front of the queue, run the loop for rest of the elements, for each iteration
            pop the elements whho does not belong to the current window from the front of the queue.
            then, pop the elements from last of the queue whose value is less than current element, then get max from front.
    
    */


    public int[] maxSlidingWindow(int[] nums, int k) {

        int n = nums.length;

        if (k > n) {
            return new int[] {};
        }

        Deque<Pair<Integer, Integer>> queue = new LinkedList<>();

        // push the first k elements in sorted order, pop the min from back and insert from back
        for (int i = 0; i < k; i++) {
            while (!queue.isEmpty() && queue.getLast().num <= nums[i]) {
                queue.pollLast();
            }

            queue.addLast(new Pair<>(nums[i], i));
        }

        int ans[] = new int[n - k + 1];

        ans[0] = queue.getFirst().num; // we will have first at the front


        for (int i = k, ind = 1; i < n && ind < (n - k + 1); i++, ind++) {
            // remove the elements from front who are out of bound for current window
            while (!queue.isEmpty() && queue.getFirst().pos <= (i - k)) {
                queue.pollFirst();
            }
            // remove elements from back whose value is less than current
            while (!queue.isEmpty() && queue.getLast().num <= nums[i]) {
                queue.pollLast();
            }

            // add from the last
            queue.addLast(new Pair<>(nums[i], i));
            // max will the element at the front
            ans[ind] = queue.getFirst().num;
        }
        
        return ans;

    }

    class Pair<K, V> {
        K num;
        V pos;

        Pair(K num, V pos) {
            this.num = num;
            this.pos = pos;
        }
    }

    public static void main(String[] args) {
        int arr[] = { 3, 1, 1, 3 };

        SlidingWindowMaximum obj = new SlidingWindowMaximum();

        Arrays.stream(obj.maxSlidingWindow(arr, 3)).forEach(el -> System.out.print(el + " "));
        
    }

}
