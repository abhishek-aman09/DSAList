package Google.Array;

import java.util.Arrays;

public class CheckGridCutIntoSection {

    /*
    https://leetcode.com/problems/check-if-grid-can-be-cut-into-sections/description/
    
    You are given an integer n representing the dimensions of an n x n grid, with the origin at the bottom-left corner of the grid. You are also given a 2D array of coordinates rectangles, where rectangles[i] is in the form [startx, starty, endx, endy], representing a rectangle on the grid. Each rectangle is defined as follows:
    
    (startx, starty): The bottom-left corner of the rectangle.
    (endx, endy): The top-right corner of the rectangle.
    Note that the rectangles do not overlap. Your task is to determine if it is possible to make either two horizontal or two vertical cuts on the grid such that:
    
    Each of the three resulting sections formed by the cuts contains at least one rectangle.
    Every rectangle belongs to exactly one section.
    Return true if such cuts can be made; otherwise, return false.
    
    
    Approach : Similare to merge sorted intervals
    You cannot put a vertical line if it crosses the x boundaries (x_start, x_end) of any rectangle,
    similarly you cannot put a horizontal line if it crosses any y boundaries (y_start, y_end)
    */

    public boolean checkValidCuts(int n, int[][] rectangles) {

        int len = rectangles.length;

        int[][] xCoordinates = new int[len][2];
        int[][] yCoordinates = new int[len][2];

        for (int i = 0; i < len; i++) {
            int[] x = new int[] { rectangles[i][0], rectangles[i][2] }; // create array of xStart and xEnd
            int[] y = new int[] { rectangles[i][1], rectangles[i][3] }; // create array of yStart and yEnd

            xCoordinates[i] = x;
            yCoordinates[i] = y;
        }

        // sort on basis of start time
        Arrays.sort(xCoordinates, (a, b) -> Integer.compare(a[0], b[0]));
        Arrays.sort(yCoordinates, (a, b) -> Integer.compare(a[0], b[0]));

        int numOfPartitions = mergerIntervals(xCoordinates); // merge them

        if (numOfPartitions > 2) { // if more than two intervals are present, then we can slice
            return true;
        }

        // do the same for y coordinates array
        numOfPartitions = mergerIntervals(yCoordinates);

        if (numOfPartitions > 2) {
            return true;
        }

        return false;

    }
    
    // simple merge intervals block
    private int mergerIntervals(int arr[][]) {

        int n = arr.length;
        int countOfNewIntervals = 0;
        if (n == 0) {
            return 0;
        }

        int currEnd = arr[0][1];

        for (int i = 1; i < n; i++) {
            if (arr[i][0] >= currEnd) {
                countOfNewIntervals++;
                currEnd = arr[i][1];
            } else {
                currEnd = Integer.max(currEnd, arr[i][1]);
            }
        }

        countOfNewIntervals++;

        return countOfNewIntervals;
    }

    public static void main(String[] args) {
        CheckGridCutIntoSection obj = new CheckGridCutIntoSection();

        int rectangles[][] = new int[][] { { 0, 2, 2, 4 }, { 1, 0, 3, 2 }, { 2, 2, 3, 4 }, { 3, 0, 4, 2 },
                { 3, 2, 4, 4 } };
            
        System.out.println(obj.checkValidCuts(4, rectangles));
    }
}
