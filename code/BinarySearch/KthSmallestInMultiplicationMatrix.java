package BinarySearch;

public class KthSmallestInMultiplicationMatrix {

    // https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/description/

    /*
    Nearly everyone has used the Multiplication Table. 
    The multiplication table of size m x n is an integer matrix mat 
    where mat[i][j] == i * j (1-indexed).
    
    Given three integers m, n, and k, 
    return the kth smallest element in the m x n multiplication table.
    
    Input: m = 3, n = 3, k = 5
    Output: 3
    Explanation: The 5th smallest number is 3.
    
    Approach : 
    1. transfer in 1D array, sort and return. T : (mn)log(mn) S : mn
    2. Use Hashmap to count freq of each el and return. T : mn S : mn
    3. Use binary search with left = 1 and right = m
    for each mid, check number of elements in matrix less than equal to mid.
    if count is >= k, this could be our ans. Store it and check its left. 
    Or else, check the right.
    
    */
    

    public int findKthNumber(int m, int n, int k) {

        if (m == 1 && n == 1) {
            return 1;
        }

        int left = 1, right = m * n;

        int lastElFreqMoreThanK = 0;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int numOfElLessThanMid = 0;

            for (int i = 1; i <= m; i++) {

                if (mid <= i * n) {
                    numOfElLessThanMid += (mid / i);
                } else {
                    numOfElLessThanMid += n;
                }
            }

            if (numOfElLessThanMid >= k) {
                lastElFreqMoreThanK = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return lastElFreqMoreThanK;

    }
    

}
