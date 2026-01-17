#include<bits/stdc++.h>

using namespace std;

vector<int> nextGreaterElement(vector<int>& nums1, vector<int>& nums2) {

    int sizeOfN2 = nums2.size();

    vector<int> nextGreaterArray(sizeOfN2);
    stack<int> nextGreaterStack;

    // given nums1 is a subset of nums2, we will find all the next greater element
    // of nums2 and store their indexed in a map, and will fetch them while 
    // traversing nums1.

    map<int, int> posOfElementInN2;

    for (int i = sizeOfN2 - 1; i >= 0; i--) {

        // keep poping until you find an element bigger than the current
        while(!nextGreaterStack.empty() and nextGreaterStack.top() <= nums2[i])
            nextGreaterStack.pop();
        
        if(nextGreaterStack.empty())
            nextGreaterArray[i] = -1;
        
        else
            nextGreaterArray[i] = nextGreaterStack.top();

        nextGreaterStack.push(nums2[i]);

        // store the position of the current el in map
        posOfElementInN2[nums2[i]] = i;
    }

    int sizeOfN1 = nums1.size();

    vector<int> result(sizeOfN1);

    for (int i = 0; i < sizeOfN1; i++)
        result[i] = nextGreaterArray[posOfElementInN2[nums1[i]]];

    return result;
}