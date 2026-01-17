#include<bits/stdc++.h>

using namespace std;

int trap(vector<int>& height) {
    int size = height.size();

    int maxTrapWater = 0;

    int left = 0, right = size - 1, lmax = 0, rmax = 0;

    while(left < right) {
        if(height[left] <= height[right]) {
            lmax = max(lmax, height[left]);
            maxTrapWater += (lmax - height[left++]);
        } else {
            rmax = max(rmax, height[right]);
            maxTrapWater += (rmax - height[right--]);
        }
    }

    return maxTrapWater;
}