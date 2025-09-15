package StackAndQueue;

import java.util.Stack;

public class LargestRectangle {

    

    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        // linear time with 3 arrays and 3 traversal, calculate nse, pse and calculate
        // int[] nseArr = nse(heights, n);
        // int[] pseArr = pse(heights, n);

        int ans = Integer.MIN_VALUE;

        // for (int i = 0; i < n; i++) {
        //     ans = Integer.max(ans, heights[i] * (nseArr[i] - pseArr[i] - 1));
        // }

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
