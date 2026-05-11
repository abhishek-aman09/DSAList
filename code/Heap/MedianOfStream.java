package Heap;

import java.util.PriorityQueue;

public class MedianOfStream {

    // https://leetcode.com/problems/find-median-from-data-stream/

    /*
    The median is the middle value in an ordered integer list. If the size of the list is even, 
    there is no middle value, and the median is the mean of the two middle values.
    
    For example, for arr = [2,3,4], the median is 3.
    For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
    Implement the MedianFinder class:
    
    MedianFinder() initializes the MedianFinder object.
    void addNum(int num) adds the integer num from the data stream to the data structure.
    double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer will be accepted.\
    
    
    Approach : Make two heaps, min heap, have largest num on top and max heap,
        have smallest number on the top.
        minHeap will store left part of stream while max will store right.
        always insert num in max heap, then insert the top of max heap into min heap.
        This will ensure the top of min half is always less than top of max half.
        if min heap is larger in size, balance the heaps. 
        if its size get more than max, insert its top el in max heap.
    
        if stream size is odd, top of max heap is our ans. If it is even, sum
        of top of both heaps is our ans.
    
    */

    private static class MedianFinder {

        private PriorityQueue<Integer> minHeap;
        private PriorityQueue<Integer> maxHeap;

        public MedianFinder() {
            this.maxHeap = new PriorityQueue<>((a,b) -> Integer.compare(a, b));
            this.minHeap = new PriorityQueue<>((a,b) -> Integer.compare(b, a));
        }
        
        public void addNum(int num) {

            maxHeap.add(num);

            minHeap.add(maxHeap.poll());

            if (minHeap.size() > maxHeap.size()) {
                maxHeap.add(minHeap.poll());
            }
            
        }
        
        public double findMedian() {
            if (maxHeap.size() > minHeap.size()) {
                return maxHeap.peek();
            }

            int median = minHeap.peek() + maxHeap.peek();

            return (double) (median) / 2;
        }
    
    }

    public static void main(String[] args) {
        MedianFinder obj = new MedianFinder();

        obj.addNum(1);
        System.out.println(obj.findMedian());
        obj.addNum(2);
        System.out.println(obj.findMedian());
        obj.addNum(3);
        System.out.println(obj.findMedian());
        // obj.addNum(-4);
        // System.out.println(obj.findMedian());
        // obj.addNum(-5);
        // System.out.println(obj.findMedian());
    }
    
}
