#include<bits/stdc++.h>

using namespace std;

/**
 * Method to find kth smallest element using lomuto partitioning.
 * It's average case is O(n) , worst is O(nlogn) but in general average is considered
 */
int kthElement(vector<int> &arr, int left, int right, int targerIndex) {
    if(left > right)
        return -1;

    // In lomuto partitioning we conside the last element to be the greatest 
    // and then we make it pivot for sorting out current iteration.

    // select a random index between left and right
    int randomIndex = left + (rand() % (right - left + 1));

    swap(arr[right], arr[randomIndex]);

    int leftSwappableIndex = left - 1;

    //Lomuto partioning block
    for (int i = left; i < right; i++) {
        if(arr[i] < arr[right]) {
            leftSwappableIndex++;
            swap(arr[i], arr[leftSwappableIndex]);
        }
    }

    leftSwappableIndex++;
    swap(arr[leftSwappableIndex], arr[right]);

    //Recursively calling the left and right block based on the condition.
    if(leftSwappableIndex + 1 == targerIndex)
        return arr[leftSwappableIndex];

    if(leftSwappableIndex + 1 < targerIndex)
        return kthElement(arr, leftSwappableIndex + 1, right, targerIndex);
    else 
        return kthElement(arr, left, leftSwappableIndex - 1, targerIndex);
}