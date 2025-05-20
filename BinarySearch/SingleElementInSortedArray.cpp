#include<bits/stdc++.h>

using namespace std;

int singleNonDuplicate(vector<int>& nums) {

    int sizeOfNums = nums.size();

    if(sizeOfNums == 1)
        return nums[0];

    if(nums[0] != nums[1])
        return nums[0];
    if(nums[sizeOfNums - 1] != nums[sizeOfNums - 2])
        return nums[sizeOfNums - 1];

    int left = 1, right = sizeOfNums - 2;

    while(left <= right) {
        int mid = (left + right) / 2;

        if(nums[mid] != nums[mid + 1] and nums[mid] != nums[mid - 1])
            return nums[mid];

        // if mid is odd
        if(mid % 2) {
            // if pair of same num doesn't exist in even odd indexes then single element is at the left
            if(nums[mid] != nums[mid - 1])
                right = mid - 1;
            else
                left = mid + 1;
        } else {
            // if pair of same num doesn't exist in even odd indexes then single element is at the left
            if(nums[mid] != nums[mid + 1])
                right = mid - 1;
            else
                left = mid + 1;
        }
    }

    return -1;
}