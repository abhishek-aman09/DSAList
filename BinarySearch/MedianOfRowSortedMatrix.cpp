#include<bits/stdc++.h>

using namespace 
std;

int getNumLessThanCurr(vector<vector<int>> mat, int curr, int m) {

    int answer = 0;

    for(vector<int> row : mat) {

        int left = 0, right = m - 1, pos = 0;

        while(left <= right) {

            int mid = (left + right) / 2;
            if(row[mid] <= curr) {
                pos = mid;
                left = mid + 1;
            } else
                right = mid - 1;

        }
        if(pos != 0 or (pos == 0 and row[pos] <= curr))
        answer += (pos + 1);
    }

    return answer;
}

int median(vector<vector<int>> &mat) {

    int n = mat.size();
    int m = mat[0].size();

    // if we put all elements of n*m matrix in an array,
    // median will be (m*n)/2th element.

    int left = INT16_MAX, right = 0;

    for (int i = 0; i < n; i++){
        left = min(left, mat[i][0]);
        right = max(right, mat[i][m - 1]);
    }

    int halfOfElements = (n * m) / 2;

    while(left <= right) {
        int mid = (left + right) / 2;
        int numLessThanMid = getNumLessThanCurr(mat, mid, m);

        if(numLessThanMid <= halfOfElements)
            left = mid + 1;
        else
            right = mid - 1;
    }

    return left;
}