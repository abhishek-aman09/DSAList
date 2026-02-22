package LinkedLists;

public class DetectAndRemoveCycle {
    
    // https://www.geeksforgeeks.org/problems/remove-loop-in-linked-list/1

    /*
    Given the head of a singly linked list, 
    the task is to remove a cycle if present. 
    A cycle exists when a node's next pointer points back to a previous node, 
    forming a loop. Internally, a variable pos denotes the index of the node where the cycle starts, 
    but it is not passed as a parameter. The terminal will print true if a cycle is removed otherwise, 
    it will print false.
    
    
    Approach - 
    Start both pointer at head and break loop when they matches.
    check if they match to confirm the loop.
    move slow to head.
    if fast still matches the slow, loop is at the start.
    move fast till its next points to slow.
    
    other case, move both together till both next point to single node.
    
    make fast.next null.
    */

    public static void removeLoop(Node head) {
        if (head == null || head.next == null) {
            return;
        }

        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }

        if (slow != fast) {
            return;
        }

        slow = head;

        if (slow == fast) {
            while (fast.next != slow) {
                fast = fast.next;
            }
        } else {
            while (slow.next != fast.next) {
                slow = slow.next;
                fast = fast.next;
            }
        }

        fast.next = null;

    }
    
    private static class Node {
        int data;
        Node next;
    }
}
