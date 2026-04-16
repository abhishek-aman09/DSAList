package LinkedLists;


public class Palindrome {
    /*
    Constant space and linear time approach
    
    The 3-Step Strategy
    Find the Middle: Use the "Slow and Fast Pointer" technique to locate the center of the list.
    
    Reverse the Second Half: Flip the nodes in the second half of the list so they point backward.
    
    Compare and Restore: Compare the first half and the reversed second half.
    
    
    */

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


    public boolean isPalindrome(ListNode head) {

        if(head == null || head.next == null) {
            return true;
        }

        ListNode slow = head, fast = head;

        while(fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        slow.next = reverse(slow.next);

        fast = slow.next;
        slow = head;

        // System.out.println(slow.val + " " + fast.val);

        while(fast != null) {
            if(slow.val != fast.val) {
                return false;
            }

            slow = slow.next;
            fast = fast.next;
        }

        return true;
    }

    private ListNode reverse(ListNode head) {

        ListNode curr = head;
        ListNode prev = null;

        while(curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return prev;
    }
    
}
