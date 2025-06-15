package LinkedLists;

import java.util.Stack;

import LinkedLists.MergeKList.ListNode;

public class DoubleANumber {
    // Given a number as linked list. Double the number
    // https://leetcode.com/problems/double-a-number-represented-as-a-linked-list/description/?envType=problem-list-v2&envId=linked-list
    
    public ListNode doubleIt(ListNode head) {

        if (head == null) {
            return null;
        }

        Stack<Integer> stk = new Stack<>();

        ListNode curr = head;

        while (curr != null) {
            stk.add(curr.val);
            curr = curr.next;
        }

        ListNode newHead = new ListNode((stk.peek() * 2) % 10);

        int carry = (stk.peek() * 2) / 10;

        stk.pop();

        curr = newHead;

        while (!stk.empty()) {
            int num = stk.pop();
            num = (num * 2) + carry;
            int rem = num % 10;
            carry = num / 10;

            ListNode temp = new ListNode(rem);
            temp.next = curr;
            curr = temp;
        }

        if (carry > 0) {
            ListNode temp = new ListNode(carry);
            temp.next = curr;
            curr = temp;
        }
        
        return curr;
        
    }
}
