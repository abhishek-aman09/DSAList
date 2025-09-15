package Heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


public class FindKPairs {
    
    //https://leetcode.com/problems/find-k-pairs-with-smallest-sums/description/?envType=problem-list-v2&envId=heap-priority-queue

    
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {

        int n = nums1.length;
        int m = nums2.length;

        List<List<Integer>> ans = new ArrayList<>();

        if (n == 0 || m == 0) {
            return ans;
        }

        PriorityQueue<HeapStructure> minHeap = new PriorityQueue<>(new MyComparator());
        // int visited[][] = new int[n + 1][m + 1];

        Set<Pair<Integer, Integer>> visited = new HashSet<>();

        minHeap.add(new HeapStructure(nums1[0], nums2[0], 0, 0));

        while (ans.size() < k && !minHeap.isEmpty()) {
            HeapStructure curr = minHeap.poll();
            int posA = curr.posA, posB = curr.posB;

            visited.add(new Pair<Integer, Integer>(posA, posB));

            ans.add(List.of(curr.a, curr.b));

            if (posA + 1 < n && !visited.contains(new Pair<Integer, Integer>(posA + 1, posB))) {
                minHeap.add(new HeapStructure(nums1[posA + 1], nums2[posB], posA + 1, posB));
                visited.add(new Pair<Integer, Integer>(posA + 1, posB));
            }

            if (posB + 1 < m && !visited.contains(new Pair<Integer, Integer>(posA, posB + 1))) {
                minHeap.add(new HeapStructure(nums1[posA], nums2[posB + 1], posA, posB + 1));
                visited.add(new Pair<Integer, Integer>(posA, posB + 1));
            }
        }

        return ans;

    }
    
    class MyComparator implements Comparator<HeapStructure> {

        @Override
        public int compare(HeapStructure o1, HeapStructure o2) {
            int sumA = o1.a + o1.b;
            int sumB = o2.a + o2.b;
            if (sumA != sumB) {
                return sumA - sumB;
            }
            if (o1.a != o2.a) {
                return o1.a - o2.a;
            }

            return o1.b - o2.b;    
        }
        
    }

    private static class HeapStructure {
        int a;
        int b;
        int posA;
        int posB;

        HeapStructure(int a, int b, int posA, int posB) {
            this.a = a;
            this.b = b;
            this.posA = posA;
            this.posB = posB;
        }
        
    }
    

    public static void main(String[] args) {
        int a[] = { 1,1,2 };
        int b[] = { 1,2,3 };

        FindKPairs obj = new FindKPairs();

        List<List<Integer>> ans = obj.kSmallestPairs(a, b, 2);

        for (List<Integer> list : ans) {
            for (int el : list) {
                System.out.print(el);
            }
            System.out.println();
        }
    }
}
