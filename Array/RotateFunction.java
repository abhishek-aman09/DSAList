package Array;

public class RotateFunction {
    
    /*
     * You are given an integer array nums of length n.
    
    Assume arrk to be an array obtained by rotating nums by k positions clock-wise.
     We define the rotation function F on nums as follow:
    
    F(k) = 0 * arrk[0] + 1 * arrk[1] + ... + (n - 1) * arrk[n - 1].
    Return the maximum value of F(0), F(1), ..., F(n-1).
    
    The test cases are generated so that the answer fits in a 32-bit integer.
    https://leetcode.com/problems/rotate-function/description/?envType=problem-list-v2&envId=array
    
     */
    
    public static int maxRotateFunction(int[] nums) {


        int ans = 0;
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int sumOfArray = 0;

        for (int i = 0; i < n; i++) {
            ans += (i * nums[i]);
            sumOfArray += nums[i];
        }

        int temp = ans;

        for (int i = n - 1; i > 0; i--) {
            int el = nums[i];
            temp = temp + (sumOfArray - (n * el));
            ans = Integer.max(ans, temp);
        }

        return ans;
    }
    
    public static void main(String[] args) {
        int arr[] = { 1,2,3,4,5,6,7,8,9,10 };

        System.out.println(maxRotateFunction(arr));
    }

}
