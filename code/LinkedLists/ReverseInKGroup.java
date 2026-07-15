package LinkedLists;

import java.util.Stack;

public class ReverseInKGroup {

    /*
    https://leetcode.com/problems/reverse-nodes-in-k-group/description/
    
    Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified list.
    k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.
    You may not alter the values in the list's nodes, only nodes themselves may be changed.
    
    Approach : for every k nodes, call reverse function that will return head
    and tail of the reversed linked list. keep pointing the last tail to current
    head.
    
    */

    public static class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }


    public ListNode reverseKGroup(ListNode head, int k) {

        ListNode curr = head, newHead = null, prevTail = null;
        ListNode currHead = null;

        // run the loop till current reached end.
        while (curr != null) {
            int count = 0;
            // keep track of current head
            currHead = curr;

            // move the current k steps or till curr is null
            while (count < k && curr != null) {
                curr = curr.next;
                count++;
            }

            // if curr is null and we have count left, we need not to reverse the part
            if ((curr == null && count < k) && prevTail != null) {
                prevTail.next = currHead;
                break;
            }

            // call reverse block
            ListNode[] result = reverse(currHead, k);

            // assign new head
            if (newHead == null) {
                newHead = result[0];
            }

            // if prevTail is not nul, assign it to new head
            if (prevTail != null) {
                prevTail.next = result[0];
            }

            // change the prev tail to current head
            prevTail = result[1];           

        }

        // check if size of list is less than k, and return accordingly
        return newHead == null ? head : newHead;
        
    }

    private ListNode[] reverse(ListNode head, int k) {

        ListNode curr = head;
        // use stack to reverse the list
        Stack<ListNode> stk = new Stack<>();

        // tail will be pointing to null, hence push null
        stk.push(null);

        // push k nodes into the stack
        while (k-- > 0 && curr != null) {
            stk.push(curr);
            curr = curr.next;
        }

        ListNode newHead = stk.pop();

        curr = newHead;

        // reverse the list
        while (!stk.isEmpty()) {
            curr.next = stk.pop();
            curr = curr.next;
        }

        // return new head and tail of the list
        return new ListNode[]{newHead, head};

    }
    

    public static void main(String[] args) {
        ReverseInKGroup obj = new ReverseInKGroup();

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // Loop from 1 to 10 to create and link nodes
        for (int i = 1; i <= 5; i++) {
            current.next = new ListNode(i);
            current = current.next;
        }

        // The actual list starts from dummy.next
        ListNode root = dummy.next;

        ListNode ans = obj.reverseKGroup(root, 2);

        int counter = 0;
        while (ans != null) {
            counter++;
            System.out.print(ans.val + "  ");
            ans = ans.next;
            if (counter > 40) {
                break;
            }
        }

    }
    
}
