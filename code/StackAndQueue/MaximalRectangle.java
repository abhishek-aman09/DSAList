package StackAndQueue;

import java.util.Stack;

public class MaximalRectangle {

    // https://leetcode.com/problems/maximal-rectangle/description/

    /*
    Given a rows x cols binary matrix filled with 0's and 1's, 
    find the largest rectangle containing only 1's and return its area.
    
    Input: matrix = [["1","0","1","0","0"],
                    ["1","0","1","1","1"],
                    ["1","1","1","1","1"],
                    ["1","0","0","1","0"]]
    Output: 6
    
    Approach : We make use of the code of largest array in the histogram problem.
    
    each row forms a histogram with combined area of its row above, 
    if the el at above the current el is 1 we add the height, else we
    make it 0.
    
    largestRect array keep track of the histogram heights for each level.
    We then call the largestAreaHistogram method to find maximum area.
    */
    public int maximalRectangle(char[][] matrix) {

        int n = matrix.length;
        int m = matrix[0].length;

        // array to store heights at each level
        int largestRect[] = new int[m];
        int maxArea = 0;

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < m; j++) {

                // if current base is 0, height will be 0. Else add the base
                if (matrix[i][j] == '0') {
                    largestRect[j] = 0;
                } else {
                    largestRect[j]++;
                }
            }

            // Call the method of each row.
            int currArea = largestRectWithCurrRow(largestRect, i);
            if (currArea > maxArea) {
                maxArea = currArea;   
            }
        }

        return maxArea;

    }
    
    // Method to calculate area of histogram
    public int largestRectWithCurrRow(int arr[], int row) {

        int ans = 0;
        
        Stack<Integer> stk = new Stack<>();

        stk.add(-1);
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            while (!stk.isEmpty() && stk.peek() != -1 && arr[stk.peek()] >= arr[i]) {
                int currIndex = stk.pop();
                int currSum = (i - stk.peek() - 1) * arr[currIndex];
                if (currSum > ans) {
                    ans = currSum;
                }
            }
            stk.add(i);
        }

        while (!stk.isEmpty() && stk.peek() != -1) {
            int currIndex = stk.pop();

            int currSum = (n - stk.peek() - 1) * arr[currIndex];
            if (currSum > ans) {
                ans = currSum;
            }
        }

        return ans;
    }
    
    public static void main(String[] args) {
        char matrix[][] = {
            {'1', '0', '1', '0', '0'},
            {'1', '0', '1', '1', '1'},
            {'1', '1', '1', '1', '0'},
            {'1', '0', '0', '1', '0'} };
            
        MaximalRectangle obj = new MaximalRectangle();

        System.out.println(obj.maximalRectangle(matrix));
                                    
    }
    
}
