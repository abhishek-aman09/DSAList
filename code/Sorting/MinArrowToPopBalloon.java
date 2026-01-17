package Sorting;

import java.util.Arrays;
import java.util.Comparator;

public class MinArrowToPopBalloon {
    
    public static int findMinArrowShots(int[][] points) {

        int n = points.length;

        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int rowA[], int rowB[]) {
                if (rowA[0] != rowB[0]) {
                    return rowA[0] < rowB[0] ? -1 : 1;
                }

                return rowB[0] < rowA[0] ? -1 : 1;
            }
        });

        int ans = 0;

        for (int i = 0; i < n;) {
            int end = points[i][1];
            i++;
            while (i < n && points[i][0] <= end) {
                i++;
            }
            ans++;
        }
        return ans;
    }

    public static void main(String[] args) {
        int arr[][] = {
                { 3, 9 }, { 7, 12 }, { 3, 8 }, { 6, 8 }, { 9, 10 }, { 2, 9 }, { 0, 9 }, { 3, 9 }, { 0, 6 }, { 2, 8 }
        };

        System.out.println(findMinArrowShots(arr));
    }
    
}
