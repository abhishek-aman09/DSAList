package StackAndQueue;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class NextGreaterElementII {
    /*
    https://leetcode.com/problems/next-greater-element-ii/description/
    
    Given a circular integer array nums (i.e., the next element of nums[nums.length - 1] is nums[0]), return the next greater number for every element in nums.
    
    The next greater number of a number x is the first greater number to its traversing-order next in the array, which means you could search circularly to find its next greater number. 
    If it doesn't exist, return -1 for this number.
    
    Input
    nums =
    [1,2,3,4,5,6,5,4,5,1,2,3]
    Output
    [2,3,4,5,6,-1,6,5,6,2,3,4]
    
    Approach - 2 monotonic stack approach. Use the primary stack as to
    find the next greater element, while popping from main stack,
    keep the pooped element in temp stack, for all the elements
    in temp stack, the current stk top is the next greater element.
    
    when out of the loop, the main stack will be sorted in descending order,
    run the loop from 0 to n - 1, and whenever you encounter an elem
    current stk top, make it as the next greater element. 

    come out of loop, mark next greater to -1 for all the elements remaining in the main stack.
    */
    
    public int[] nextGreaterElements(int[] nums) {

        int n = nums.length;

        int ans[] = new int[n];

        Stack<Integer> stk = new Stack<>();
        Stack<Integer> temp = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!stk.isEmpty() && nums[stk.peek()] < nums[i]) {
                temp.add(stk.pop());
            }

            stk.add(i);

            while (!temp.isEmpty()) {
                ans[temp.pop()] = nums[stk.peek()];
            }
        }

        int i = 0;

        while (!stk.isEmpty() && i < n) {
            while (i < n && nums[i] <= nums[stk.peek()]) {
                i++;
            }
            if (i >= n) {
                break;
            }
            ans[stk.peek()] = nums[i];
            stk.pop();
        }

        while (!stk.isEmpty()) {
            ans[stk.peek()] = -1;
            stk.pop();
        }


        return ans;

    }
    
    public static void main(String[] args) {
        NextGreaterElementII obj = new NextGreaterElementII();

        int arr[] = { 1, 2, 3, 4, 5, 6, 5, 4, 5, 1, 2, 3 };

        Arrays.stream(obj.nextGreaterElements(arr)).forEach(el -> System.out.print(el + " "));
    }
}
