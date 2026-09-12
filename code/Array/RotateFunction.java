package Array;

public class RotateFunction {
    
    /*
    https://leetcode.com/problems/rotate-function/description/?envType=problem-list-v2&envId=array
    
     * You are given an integer array nums of length n.
    
    Assume arrk to be an array obtained by rotating nums by k positions clock-wise.
     We define the rotation function F on nums as follow:
    
    F(k) = 0 * arrk[0] + 1 * arrk[1] + ... + (n - 1) * arrk[n - 1].
    Return the maximum value of F(0), F(1), ..., F(n-1).
    
    The test cases are generated so that the answer fits in a 32-bit integer.
    
    approach : find the totalSum and rotateSum of the array. start traversing from right, as rotation is done clockwise.
    the last element weight drops by (n - 1) * el. rest of the array elements value increase by one unit. Hence the total increment in value is equal to totalSum.
     */
    
    public static int maxRotateFunction(int[] nums) {


        int ans = 0;
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int sumOfArray = 0;

        for (int i = 0; i < n; i++) {
            ans += (i * nums[i]); // represents the rotateSum
            sumOfArray += nums[i]; // represents the totalSum
        }

        int temp = ans;

        for (int i = n - 1; i > 0; i--) {
            int el = nums[i]; 
            temp = temp + (sumOfArray - (n * el)); // temp will be updated by sumArray - (n * el).
            ans = Integer.max(ans, temp);
        }

        return ans;
    }
    
    public static void main(String[] args) {
        int arr[] = { 1,2,3,4,5,6,7,8,9,10 };

        System.out.println(maxRotateFunction(arr));
    }

}
