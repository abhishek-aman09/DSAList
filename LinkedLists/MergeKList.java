package LinkedLists;

import java.util.PriorityQueue;

public class MergeKList {

    // merge K lists
    // https://leetcode.com/problems/merge-k-sorted-lists/description/?envType=problem-list-v2&envId=linked-list

    /*
     * Intution : create a heap that will store the current el and the pos of 
     * that el in araay of array. top the pop, and push the next el int the
     * array at that pos into the queue.
     * 
     * Do this until the heap is empty.
     */

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    public static ListNode mergeKLists(ListNode[] lists) {

        int n = lists.length;

        ListNode head = null, temp = null;

        PriorityQueue<NodeWithPos> pq = new PriorityQueue<>(
                (a, b) ->  a.el - b.el
        );

        for (int i = 0; i < n; i++) {
            if (lists[i] != null) {
                pq.add(new NodeWithPos(lists[i].val, i));
            }
        }

        while (!pq.isEmpty()) {
            NodeWithPos curr = pq.poll();

            if (head == null) {
                head = lists[curr.pos];
                temp = head;
            } else {
                temp.next = lists[curr.pos];
                temp = temp.next;
            }
            
            lists[curr.pos] = lists[curr.pos].next;

            if (lists[curr.pos] != null) {
                pq.add(new NodeWithPos(lists[curr.pos].val, curr.pos));
            }
        }

        return head;
        
    }

    static class NodeWithPos {
        final int el;
        final int pos;

        NodeWithPos(int el, int pos) {
            this.el = el;
            this.pos = pos;
        }
    }
    
    public static void main(String[] args) {

        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(4);
        head1.next.next = new ListNode(5);

        ListNode head2 = new ListNode(1);
        head2.next = new ListNode(3);
        head2.next.next = new ListNode(4);

        ListNode head3 = new ListNode(2);
        head3.next = new ListNode(6);

        ListNode head4 = null;
        
        ListNode[] arr = { head1, head2, head3, head4 };

        ListNode head = mergeKLists(arr);

        while (head != null) {
            System.out.print(head.val + "  ");
            head = head.next;
        }

    }

}
