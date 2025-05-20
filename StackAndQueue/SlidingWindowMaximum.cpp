#include<bits/stdc++.h>

using namespace std;

vector<int> maxSlidingWindow(vector<int>& nums, int k) {
        
    if(k == 1)
        return nums;

    vector<int> result;

    int arrayLength = nums.size();

    if(k > arrayLength)
        return result;

    deque<pair<int,int> > maxFrontWithPos;

    for (int i = 0; i < k; i++) {
        while(!maxFrontWithPos.empty() and maxFrontWithPos.back().first <= nums[i])
            maxFrontWithPos.pop_back();

        maxFrontWithPos.push_back(make_pair(nums[i], i));
    }

    int left = 0, right = k - 1;

    while(right < arrayLength) {
        result.push_back(maxFrontWithPos.front().first);
        left++;
        right++;
        if(right == arrayLength)
            break;
        
        if(maxFrontWithPos.front().second < left)
            maxFrontWithPos.pop_front();
        
        while(!maxFrontWithPos.empty() and maxFrontWithPos.back().first <= nums[right])
            maxFrontWithPos.pop_back();

        maxFrontWithPos.push_back(make_pair(nums[right], right));
    }

    return result;
}