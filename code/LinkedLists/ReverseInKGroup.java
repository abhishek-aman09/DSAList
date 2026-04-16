package LinkedLists;

public class ReverseInKGroup {

    public static class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }


    public ListNode reverseKGroup(ListNode head, int k) {

        ListNode curr = head;

        int temp = 1;

        while(temp++ < k && curr != null) {
            curr = curr.next;
        }

        if(curr == null || curr.next == null) {
            return reverseList(head);
        }

        ListNode newHead = curr;

        while(head != null) {
            head = reverse(head, k);
        }

        return newHead;
        
    }

    private ListNode reverse(ListNode head, int k) {

        ListNode curr = head, prev = null, next = null;

        while(curr != null && k-- > 0) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        head.next = next;

        return next;

    }


    public ListNode reverseList(ListNode head) {

        ListNode curr = head, prev = null, next = null;

        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return prev;

    }
    

    public static void main(String[] args) {
        ReverseInKGroup obj = new ReverseInKGroup();

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;

        // Loop from 1 to 10 to create and link nodes
        for (int i = 1; i <= 10; i++) {
            current.next = new ListNode(i);
            current = current.next;
        }

        // The actual list starts from dummy.next
        ListNode root = dummy.next;

        obj.reverseKGroup(root, 2);

    }
    
}
