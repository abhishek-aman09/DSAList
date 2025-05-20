#include<bits/stdc++.h>

using namespace std;

void nextPermutation(vector<int> &nums) {
    int sizeOfArray = nums.size();

    int firstDecreasingFromLeft = -1;
    int i = sizeOfArray - 1;

    while(i > 0) {
        if(nums[i] > nums[i -1]) {
            firstDecreasingFromLeft = i - 1;
            break;
        }
        i--;
    }

    if(firstDecreasingFromLeft == -1) {
        reverse(nums.begin(), nums.end());
        return;
    }

    i = sizeOfArray - 1;

    while(i < firstDecreasingFromLeft) {
        if(nums[i] > nums[i - 1])
            break;

        i--;
    }

    // i holds the position of first element from the right
    // that is greater than its left

    swap(nums[i], nums[firstDecreasingFromLeft]);
    i = sizeOfArray - 1;

    for (int j = firstDecreasingFromLeft + 1; j < i; j++, i--)
        swap(nums[j], nums[i]);
}