package Array;

import java.util.ArrayList;
import java.util.List;

public class InsertIntervals {

    // https://leetcode.com/problems/insert-interval/description/
    /*
    You are given an array of non-overlapping intervals intervals where intervals[i] = [starti, endi] 
    represent the start and the end of the ith interval and intervals is sorted in ascending order by start. 
    You are also given an interval newInterval = [start, end] that represents the start and end of another interval.
    
    Insert newInterval into intervals such that intervals is still sorted in ascending order by starti 
    and intervals still does not have any overlapping intervals (merge overlapping intervals if necessary).
    
    Return intervals after the insertion.
    
    Note that you don't need to modify intervals in-place. You can make a new array and return it.
    
    Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
    Output: [[1,5],[6,9]]
    
    */
   
    public int[][] insert(int[][] intervals, int[] newInterval) {

        int n = intervals.length;

        // if newInterval lies at the end of array, just add it at end
        if(newInterval[0] > intervals[n - 1][1]) {
            int [][]result = new int[n + 1][2];
            int i = 0;
            for(int row[] : intervals) {
                result[i++] = row;
            }
            result[n] = newInterval;
            return result;
        }

        // if newInterval lies in front of the array.
        if(newInterval[1] < intervals[0][0]) {
            int [][]result = new int[n + 1][2];
            int i = 1;
            result[0] = newInterval;
            for(int row[] : intervals) {
                result[i++] = row;
            }
            
            return result;
        }

        // List to store the merged list post insertion
        List<int[]> result = new ArrayList<>();

        int i = 0;
        // flag to check if the interval is merged, we do not merge it with another interval once merged.
        boolean isMerged = false;

        while(i < n) {
            int end = intervals[i][1];
            int start = intervals[i][0];

            // if the start of new is less than curr end and end of new is greater than curr end and not merged
            // i.e we cannot merge new 4, 5 in curr 7, 8. New should be in range.
            if(end >= newInterval[0] && start <= newInterval[1] && !isMerged) {
                isMerged = true;
                start = Integer.min(start, newInterval[0]); // start will be the min of two starts
                end = Integer.max(end, newInterval[1]); // end will be max of two ends.

                // perform merge intervals till condition satisfies.
                int j = i + 1;
                while(j < n && intervals[j][0] <= end) {
                    end = Integer.max(end, intervals[j][1]);
                    j++;
                }

                i = j;
                // this check is for conditions like curr = 1,2 | 5,6 and new is 3,4
                // then they should be inserted as independent pair in between
            } else if(!isMerged && end > newInterval[0] && start > newInterval[1]) {
                isMerged = true;
                result.add(new int[] {newInterval[0], newInterval[1]});
                i++;
            } else {
                i++;
            }
            // 
            result.add(new int[] {start, end});
        }

        int ans[][] = new int[result.size()][];

        i = 0;

        for(int row[] : result) {
            ans[i++] = row;
        }

        return ans;

        
    }
    
}
