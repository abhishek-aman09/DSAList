package BinarySearch;

import java.util.Arrays;

public class FindRightInterval {
    /*
     * You are given an array of intervals, where intervals[i] = [starti, endi] and each starti is unique.
    The right interval for an interval i is an interval j such that startj >= endi and startj is minimized. Note that i may equal j.
    Return an array of right interval indices for each interval i. If no right interval exists for interval i, then put -1 at index i.
    
    Input: intervals = [[3,4],[2,3],[1,2]]
    Output: [-1,0,1]
    Explanation: There is no right interval for [3,4].
    The right interval for [2,3] is [3,4] since start0 = 3 is the smallest start that is >= end1 = 3.
    The right interval for [1,2] is [2,3] since start1 = 2 is the smallest start that is >= end2 = 2.
    
    https://leetcode.com/problems/find-right-interval/description/?envType=problem-list-v2&envId=array
    
     */
    public int[] findRightInterval(int[][] intervals) {

        int n = intervals.length;

        int ans[] = new int[n];

        Triplets[] sortedTriplets = new Triplets[n];

        for (int i = 0; i < n; i++) {
            sortedTriplets[i] = new Triplets(intervals[i][0], intervals[i][1], i);
        }

        // sorting on starting
        Arrays.sort(sortedTriplets,
                (a, b) -> a.start - b.start);

        for (int i = 0; i < n; i++) {
            ans[i] = getPosOfRight(sortedTriplets, intervals[i][1], n);
        }

        return ans;

    }
    
    private int getPosOfRight(Triplets[] sortedTriplets, int currEnd, int n) {
        int ans = -1;

        int l = 0, r = n - 1;

        while (l <= r) {
            int mid = l + ((r - l) / 2);

            if (sortedTriplets[mid].start >= currEnd) {
                ans = sortedTriplets[mid].pos;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }
    
    static class Triplets {
        int start;
        int end;
        int pos;

        Triplets(int start, int end, int pos) {
            this.start = start;
            this.end = end;
            this.pos = pos;
        }
    }
}
