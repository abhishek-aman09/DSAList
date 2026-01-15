package Array;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NextGreaterElement {
    

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {

        int n = nums2.length;

        Stack<Integer> stk = new Stack<>();

        Map<Integer, Integer> nextEl = new HashMap<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!stk.isEmpty() && stk.peek() < nums2[i]) {
                stk.pop();
            }

            if (stk.isEmpty()) {
                nextEl.put(nums2[i], -1);
            } else {
                nextEl.put(nums2[i], stk.peek());
            }

            stk.add(nums2[i]);
        }

        int result[] = new int[nums1.length];

        for (int i = 0; i < nums1.length; i++) {
            result[i] = nextEl.get(nums1[i]);
        }
        
        return result;
    }
}
