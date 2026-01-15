package Array.SlidingWindow;

import java.util.Arrays;
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
    
    Approach - Create a priority queue sorted in descending order of num
    
    for each window, pop the elements who are out of range,
    then push the curr el and the el on top will be the max el.
    
    */

    public int[] maxSlidingWindow(int[] nums, int k) {
        
        int n = nums.length;

        int ans[] = new int[n - k + 1];

        PriorityQueue<Pair<Integer, Integer>> elWithPos = new PriorityQueue<>(
            (a, b) -> b.first - a.first
        );

        for (int i = 0; i < k; i++) {
            elWithPos.add(new Pair<>(nums[i], i));
        }

        ans[0] = elWithPos.peek().first;

        for (int i = k; i < n; i++) {
            while (!elWithPos.isEmpty() && elWithPos.peek().second < i - k + 1) {
                elWithPos.poll();
            }
            elWithPos.add(new Pair<>(nums[i], i));

            ans[i - k + 1] = elWithPos.peek().first;
        }

        return ans;
    }

    class Pair<K, V> {
        K first;
        V second;

        Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) {
        int arr[] = { 1, 3, -1, -3, 5, 3, 6, 7 };

        SlidingWindowMaximum obj = new SlidingWindowMaximum();

        Arrays.stream(obj.maxSlidingWindow(arr, 3)).forEach(el -> System.out.print(el + " "));
        
    }

}
