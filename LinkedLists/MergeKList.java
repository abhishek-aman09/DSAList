package LinkedLists;

public class MergeKList {

    // merge K lists
    // https://leetcode.com/problems/merge-k-sorted-lists/description/?envType=problem-list-v2&envId=linked-list

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    public static ListNode mergeKLists(ListNode[] lists) {

        int n = lists.length;

        ListNode head = null;
        int val = Integer.MAX_VALUE;

        for (ListNode curr : lists) {
            if (curr != null) {
                if (curr.val < val) {
                    val = curr.val;
                    head = curr;
                }
            }
        }

        ListNode currHead = head;
        int currIndex = -1;
        int countNull = 0;
        boolean hasNodes = true;

        while (hasNodes) {

            countNull = 0;
            int currMin = Integer.MAX_VALUE;
            boolean wasHead = false;

            for (int i = 0; i < n; i++) {
                ListNode curr = lists[i];

                if (curr == null) {
                    countNull++;
                    continue;
                }

                if (curr == head) {
                    currIndex = i;
                    lists[i] = lists[i].next;
                    wasHead = true;
                    break;
                }

                if (currMin > curr.val) {
                    currIndex = i;
                    currMin = curr.val;
                }

            }

            if (wasHead) {
                continue;
            }

            if (countNull == n) {
                hasNodes = false;
                break;
            }

            currHead.next = lists[currIndex];
            currHead = currHead.next;

            lists[currIndex] = lists[currIndex].next;
            
        }

        return head;

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
