package Array.TwoPointer;

public class ContainerWithMostWater {

    /* https://leetcode.com/problems/container-with-most-water/description/
    You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
    
    Find two lines that together with the x-axis form a container, such that the container contains the most water.
    
    Return the maximum amount of water a container can store.
    
    Input: height = [1,8,6,2,5,4,8,3,7]
    Output: 49
    Explanation: Max water can be held between 8 and 7
    
    Approach : Similar to trapping rainwater problem.
    start left from zero and right from last index, for each pair of left and right, max water held will be decided based on smaller height among them
    if left is smaller, calculate the amount based on that, and increment it. similar for right

    
    */
    
    public int maxArea(int[] height) {

        int n = height.length;

        int left = 0;
        int  right = n - 1;

        int ans = 0;

        while (left < right) {
            int curr = 0;
            if (height[left] < height[right]) { // if left is smaller, it will be the threshold, calc height and move on
                curr = height[left] * (right - left);
                left++;
            } else {
                curr = height[right] * (right - left); // similar for right
                right--;
            }
            if (ans < curr) {
                ans = curr;
            }
        }

        return ans;

    }
    
}
