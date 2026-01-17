#include<bits/stdc++.h>

using namespace std;

int NthRoot(int n, int m) {
    
    if(n == 1 or m == 1 or m == 0)
        return m;

    int left = 1, right = m;

    while(left <= right) {
        int mid = (left + right) / 2;
        long currVal = 1;
        for (int i = 0; i < n; i++){
            if(currVal > m)
                break;
            currVal *= mid;
        }
            
        
        if(currVal == m)
            return mid;
        if(currVal < m)
            left = mid + 1;
        else
            right = mid - 1;
    }

    return -1;
}