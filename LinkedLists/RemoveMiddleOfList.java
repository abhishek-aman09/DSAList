package LinkedLists;

public class RemoveMiddleOfList {
    // https://leetcode.com/problems/delete-the-middle-node-of-a-linked-list/submissions/1741593766/?envType=problem-list-v2&envId=linked-list

    
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
    
    public ListNode deleteMiddle(ListNode head) {

        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head, fast = head.next, prevSlow = slow;

        while (fast != null && fast.next != null) {
            prevSlow = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // condition if there are odd number of links
        if (fast == null) {
            prevSlow.next = slow.next;
        } else { // condition if they are even
           slow.next = slow.next.next; 
        }

        return head;
        
    }

}
