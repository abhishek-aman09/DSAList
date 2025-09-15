#include<bits/stdc++.h>

using namespace std;

void nextPermutation(vector<int> &nums) {
    int sizeOfArray = nums.size();

    int firstDecreasingFromRight = -1;
    int i = sizeOfArray - 1;

    while(i > 0) {
        if(nums[i] > nums[i -1]) {
            firstDecreasingFromRight = i - 1;
            break;
        }
        i--;
    }

    if(firstDecreasingFromRight == -1) {
        reverse(nums.begin(), nums.end());
        return;
    }

    i = sizeOfArray - 1;

    while(i < firstDecreasingFromRight) {
        if(nums[i] > nums[i - 1])
            break;

        i--;
    }

    // i holds the position of first element from the right
    // that is greater than its left

    swap(nums[i], nums[firstDecreasingFromRight]);
    i = sizeOfArray - 1;

    for (int j = firstDecreasingFromRight + 1; j < i; j++, i--)
        swap(nums[j], nums[i]);
}