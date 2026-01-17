package Array;

import java.util.Arrays;

public class NonOverlappingIntervals {

    /* https://leetcode.com/problems/non-overlapping-intervals/description/
    Given an array of intervals intervals where intervals[i] = [starti, endi],
    return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.
    
    Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
    Output: 1
    Explanation: [1,3] can be removed and the rest of the intervals are non-overlapping.
    
    
    */
    
    public int eraseOverlapIntervals(int[][] intervals) {

        int n = intervals.length;

        Arrays.sort(intervals, (a, b) -> {
            if (a[1] != b[1]) {
                return a[1] - b[1];
            }

            return a[0] - b[0];
        });

        int ans = 0, i = 0;

        while (i < n - 1) {
            int currEnd = intervals[i][1];
            int j = i + 1;

            while (j < n && intervals[j][0] < currEnd) {
                j++;
                ans++;
            }

            i = j;
        }

        return ans;
    }
    
}
