package StackAndQueue;

import java.util.Stack;

public class LargestRectangle {

    // https://leetcode.com/problems/largest-rectangle-in-histogram/description/
    /*
    Given an array of integers heights representing the histogram's bar height 
    where the width of each bar is 1, return the area of the largest rectangle 
    in the histogram.
    
    Input: heights = [2,1,5,6,2,3]
    Output: 10
    Explanation: The above is a histogram where width of each bar is 1.
    The largest rectangle is shown in the red area, which has an area = 10 units.
    
    Approach : Maitain a monotonic stack with smallest element at the top.
    
    keep popping el greater than curr el. For each pop, the current top of
    stack is the previous smaller element (pse). calculate the area by multiplying
    the val by (i - top of stack) * popped element.
    
    Outside the loop, the right boundary will be n. So, each el area will be
    (n - top of stack) * popped element.
    */

    public int largestRectangleArea(int[] heights) {
        int n = heights.length;

        int ans = Integer.MIN_VALUE;

        // method to calculate pse and nse in one traversal
        Stack<Integer> stk = new Stack<>();

        // this will act as end of array elements
        stk.add(-1);

        for (int i = 0; i < n; i++) {
            // if the curr el is smaller than the top el of stack, then curr el will be
            // the nse for that top element and we have the stack int monotonic order
            // hence we can calculate the pse with help of that
            while (!stk.empty() && stk.peek() != -1 && heights[stk.peek()] >= heights[i]) {
                int top = stk.pop();
                ans = Integer.max(ans, heights[top] * (i - stk.peek() - 1));
            }

            stk.add(i);
        }

        while (!stk.empty() && stk.peek() != -1) {
            int top = stk.pop();
                ans = Integer.max(ans, heights[top] * (n - stk.peek() - 1));
         }

        return ans;

    }
    
    // another method is to calculate the pse and nse while traversing the array
    private int[] nse(int heights[], int n) {

        int[] next = new int[n];
        Stack<Integer> stk = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!stk.empty() && heights[stk.peek()] >= heights[i]) {
                stk.pop();
            }
            if (stk.empty()) {
                next[i] = n;
            } else {
                next[i] = stk.peek();
            }

            stk.add(i);
        }

        return next;
    }

    private int[] pse(int heights[], int n) {

        int[] prev = new int[n];
        Stack<Integer> stk = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!stk.empty() && heights[stk.peek()] >= heights[i]) {
                stk.pop();
            }
            if (stk.empty()) {
                prev[i] = -1;
            } else {
                prev[i] = stk.peek();
            }

            stk.add(i);
        }

        return prev;
    }
}
