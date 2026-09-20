package StackAndQueue;

import java.util.Stack;

/*
 * ============================================================================
 * LEETCODE 1944: Number of Visible People in a Queue
 * ============================================================================
 * Link: https://leetcode.com/problems/number-of-visible-people-in-a-queue/
 *
 * DESCRIPTION:
 * There are n people standing in a queue numbered from 0 to n - 1 from left 
 * to right. You are given an array heights of distinct integers where heights[i] 
 * represents the height of the i-th person.
 *
 * A person can see another person to their right in the queue if everybody in 
 * between is strictly shorter than both of them. More formally, the i-th person 
 * can see the j-th person (i < j) if for all k with i < k < j, 
 * min(heights[i], heights[j]) > heights[k].
 *
 * Return an array answer of length n where answer[i] is the number of people 
 * the i-th person can see to their right in the queue.
 *
 * ----------------------------------------------------------------------------
 * EXAMPLE:
 * Input: heights = [10,6,8,5,11,9]
 * Output: [3,1,2,1,1,0]
 * Explanation:
 * - Person 0 (height 10) can see Person 1 (6), Person 2 (8), and Person 4 (11).
 *   Cannot see Person 3 because Person 2 blocks them.
 * - Person 4 (height 11) can see Person 5 (9).
 * - Person 5 cannot see anyone to the right -> 0.
 *
 * ----------------------------------------------------------------------------
 * APPROACH: Monotonic Decreasing Stack (Traversing Right to Left)
 *
 * 1. Line-of-Sight Blockage Rule:
 *    - For person `i`, anyone shorter than person `i` to their right can be seen 
 *      provided nobody between them is taller.
 *    - Once a person is popped by someone taller to their left, they can NEVER 
 *      be seen by anyone further to the left, because this taller person will 
 *      block the line of sight.
 *    - This optimal popping of "dominated" elements is the signature of a 
 *      Monotonic Stack.
 *
 * 2. Direction of Traversal:
 *    - Iterate from right to left (i = n - 1 down to 0).
 *    - Maintain a monotonic strictly decreasing stack of heights.
 *
 * 3. Step-by-Step State Transitions:
 *    For person `i`:
 *      a) Pop all people in the stack who are strictly shorter than heights[i].
 *         Each popped person is directly visible to person `i`, so increment `count`.
 *      b) After popping, check the stack:
 *         - If the stack is NOT empty, the top of the stack is the FIRST person 
 *           taller than heights[i] to the right. Person `i` can see them too, 
 *           but they block vision of anyone further right. 
 *           Hence, add 1: `ans[i] = count + 1`.
 *         - If the stack is empty, nobody taller exists to the right:
 *           `ans[i] = count`.
 *      c) Push heights[i] (or index i) onto the stack.
 *
 * 4. Optimization Note:
 *    - Storing values `heights[i]` directly in the stack instead of indices avoids 
 *      an extra array lookup (`heights[stk.peek()]`).
 *    - Replacing `java.util.Stack` (synchronized overhead) with a primitive array 
 *      stack `int[] stk` drops runtime to ~10-15 ms.
 *
 * 5. Complexity:
 *    - Time Complexity:  O(N) — each person is pushed onto the stack once and 
 *                        popped at most once.
 *    - Space Complexity: O(N) — monotonic stack storing at most N elements.
 * ============================================================================
 */
public class NumberOfVisiblePeopleInAQueue {

    // Standard solution with Stack collection
    public int[] canSeePersonsCount(int[] heights) {
        int n = heights.length;
        int[] ans = new int[n];
        Stack<Integer> stk = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            int count = 0;

            // Pop all individuals shorter than heights[i]; all of them are visible to i
            while (!stk.isEmpty() && stk.peek() < heights[i]) {
                stk.pop();
                count++;
            }

            // If stack is not empty, person i can also see the first taller person blocking vision
            ans[i] = stk.isEmpty() ? count : count + 1;

            // Push current person's height
            stk.push(heights[i]);
        }

        return ans;
    }

    /*
     * ------------------------------------------------------------------------
     * Fast Primitive Array-Based Monotonic Stack (10 ms on LeetCode)
     * Zero object allocations, zero synchronization overhead
     * ------------------------------------------------------------------------
     */
    public int[] canSeePersonsCountOptimized(int[] heights) {
        int n = heights.length;
        int[] ans = new int[n];

        // Flat primitive array acts as the monotonic stack
        int[] stack = new int[n];
        int top = 0; // stack pointer; top == 0 means empty

        for (int i = n - 1; i >= 0; i--) {
            int h = heights[i];
            int visible = 0;

            // Pop all strictly shorter people
            while (top > 0 && stack[top - 1] < h) {
                top--;
                visible++;
            }

            // If someone taller remains, they are also visible (and block anything past them)
            ans[i] = (top > 0) ? visible + 1 : visible;

            // Push current person
            stack[top++] = h;
        }

        return ans;
    }
}
