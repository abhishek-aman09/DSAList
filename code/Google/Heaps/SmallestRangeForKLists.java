package Google.Heaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class SmallestRangeForKLists {

    /*
    https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/description/
    
    You have k lists of sorted integers in non-decreasing order. Find the smallest range that includes at least one number from each of the k lists.
    
    We define the range [a, b] is smaller than range [c, d] if b - a < d - c or a < c if b - a == d - c.
    
    
    
    Example 1:
    
    Input: nums = [[4,10,15,24,26],[0,9,12,20],[5,18,22,30]]
    Output: [20,24]
    Explanation: 
    List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
    List 2: [0, 9, 12, 20], 20 is in range [20,24].
    List 3: [5, 18, 22, 30], 22 is in range [20,24].
    
    Approach : push one el from each list in the queue and compute the current range
    */
    
    public int[] smallestRange(List<List<Integer>> nums) {

        int n = nums.size();

        // create a queue for max size of n
        Queue<ElWithListNumberAndPos> minQueueForElFromEachList = new PriorityQueue<>(
                (a, b) -> Integer.compare(a.el, b.el)
        );

        // this will be used to store the max in current window
        ElWithListNumberAndPos max = new ElWithListNumberAndPos(Integer.MIN_VALUE, n, n);

        // put the first of each list into the queue
        for (int i = 0; i < n; i++) {
            ElWithListNumberAndPos temp = new ElWithListNumberAndPos(nums.get(i).get(0), i, 0);
            minQueueForElFromEachList.offer(temp);
                    max = temp.el > max.el ? temp : max;
        }

        int start = 0;
        int end = Integer.MAX_VALUE / 4;

        while (minQueueForElFromEachList.size() == n) {

            // poll out the min
            ElWithListNumberAndPos min = minQueueForElFromEachList.poll();

            // if the range is smaller than current range, update it
            if ((max.el - min.el) < (end - start)) {
                start = min.el;
                end = max.el;
            }

            // get list for current min
            List<Integer> minList = nums.get(min.listIndex);
            
            // if there are any more numbers left in the list, 
            // push into the queue and update the max accordingly
            if (min.pos + 1 < minList.size()) {
                ElWithListNumberAndPos temp = new ElWithListNumberAndPos(minList.get(min.pos + 1), min.listIndex, min.pos + 1);
                minQueueForElFromEachList.offer(temp);
                max = temp.el > max.el ? temp : max;
            }

        }
        
        return new int[] { start, end };

    }
    
    private static class ElWithListNumberAndPos {
        int el;
        int listIndex;
        int pos;

        public ElWithListNumberAndPos(int el, int listIndex, int pos) {
            this.el = el;
            this.listIndex = listIndex;
            this.pos = pos;
        }
    }
    

    public static void main(String[] args) {
        SmallestRangeForKLists obj = new SmallestRangeForKLists();

        List<Integer> a = List.of(10, 10);
        List<Integer> b = List.of(11, 11);

        List<List<Integer>> nums = new ArrayList<>();
        nums.add(a);
        nums.add(b);

        Arrays.stream(obj.smallestRange(nums)).forEach(el -> System.out.print(el + "  "));
    }
}
