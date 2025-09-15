package LinkedLists;

import java.util.ArrayList;

public class maximaMinima {
    /*
     * A critical point in a linked list is defined as either a local maxima or a local minima.
    
    A node is a local maxima if the current node has a value strictly greater than the previous node and the next node.
    
    A node is a local minima if the current node has a value strictly smaller than the previous node and the next node.
    
    Note that a node can only be a local maxima/minima if there exists both a previous node and a next node.
    
    Given a linked list head, return an array of length 2 containing [minDistance, maxDistance] where minDistance
    is the minimum distance between any two distinct critical points and maxDistance is the maximum distance between 
    any two distinct critical points. If there are fewer than two critical points, return [-1, -1].
    
    https://leetcode.com/problems/find-the-minimum-and-maximum-number-of-nodes-between-critical-points/description/?envType=problem-list-v2&envId=linked-list
    
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

    public int[] nodesBetweenCriticalPoints(ListNode head) {

        int ans[] = {-1, -1};
        if (head == null || head.next == null || head.next.next == null) {
            return ans;
        }

        ArrayList<Integer> diffArray = new ArrayList<>();

        ListNode left = head, mid = head.next, right = head.next.next;
        int i = 1;

        while (right != null) {
            if (mid.val > left.val && mid.val > right.val) {
                diffArray.add(i);
            } else if (mid.val < left.val && mid.val < right.val) {
                diffArray.add(i);
            }

            left = left.next;
            mid = mid.next;
            right = right.next;
            i++;
        }

        if (diffArray.size() < 2) {
            return ans;
        }

        int min = Integer.MAX_VALUE;

        int max = diffArray.get(diffArray.size() - 1) - diffArray.get(0);

        for (i = 0; i < diffArray.size() - 1; i++) {
            min = Integer.min(min, diffArray.get(i + 1) - diffArray.get(i));
        }

        ans[0] = min;
        ans[1] = max;

        return ans;
    }
}
