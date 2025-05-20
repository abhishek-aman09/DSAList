#include<bits/stdc++.h>

using namespace std;

int binarySearch(vector<int> nums, int left, int right, int target) {

    while(left <= right) {
        int mid = (left + right) / 2;
        if(nums[mid] == target)
            return mid;
        if(nums[mid] < target)
            left = mid + 1;
        else
            right = mid - 1;
    }

    return -1;
}

int search(vector<int> &nums, int target) {
    int sizeOfArray = nums.size();
    int result = -1;

    if(sizeOfArray == 1)
        return nums[0] == target ? 0 : -1;
    
    if(nums[0] < nums[sizeOfArray - 1])
        return binarySearch(nums, 0, sizeOfArray - 1, target);

    int left = 0, right = sizeOfArray - 1, pivot = -1;

    while(pivot == -1) {
        int mid = (left + right) / 2;

        if(nums[mid] > nums[mid + 1])
            pivot = mid;

        if(nums[mid] > nums[sizeOfArray - 1])
            left = mid + 1;
        else
            right = mid - 1;
    }

    int leftResult = binarySearch(nums, 0, pivot, target);
    int rightResult = binarySearch(nums, pivot + 1, sizeOfArray - 1, target);

    result = leftResult != -1 ? leftResult : result;
    result = rightResult != -1 ? rightResult : result;

    return result;
}