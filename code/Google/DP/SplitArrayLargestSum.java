package Google.DP;

import java.util.Arrays;

public class SplitArrayLargestSum {

    private static long MAX = Integer.MAX_VALUE;
    
    public int splitArray(int[] nums, int k) {

        int sum = 0;

        for (int el : nums) {
            sum += el;
        }

        int left = 0, right = sum;

        int maxSum = Integer.MAX_VALUE;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            boolean isPartitionPossible = checkPartitionPossible(nums, mid, k);

            if (isPartitionPossible) {
                maxSum = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return maxSum;



        // long dp[][] = new long[nums.length][k + 1];

        // for (long row[] : dp) {
        //     Arrays.fill(row, -1);
        // }

        // return (int) helper(0, nums.length, nums, k, dp);

    }
    
    private long helper(int currIndx, int n, int[] nums, int partitionLeft, long[][] dp) {

        if (currIndx == n) {
            if (partitionLeft == 0) {
                return 0;
            }
            return MAX;
        }

        if (partitionLeft < 0) {
            return MAX;
        }

        if (dp[currIndx][partitionLeft] != -1) {
            return dp[currIndx][partitionLeft];
        }

        long currSum = 0;
        long currMax = 0;
        long minSum = Long.MAX_VALUE;

        for (int i = currIndx + 1; i <= n; i++) {
            currSum += nums[i - 1];

            currMax = Long.max(currSum, helper(i, n, nums, partitionLeft - 1, dp));

            minSum = Long.min(currMax, minSum);
        }

        return dp[currIndx][partitionLeft] = minSum;
    }

    private boolean checkPartitionPossible(int[] nums, int sum, int partitions) {

        int currSum = 0;

        for (int i = 0; i < nums.length; i++) {
            currSum += nums[i];

            if (nums[i] > sum) {
                return false;
            }

            if (currSum > sum) {
                currSum = nums[i];
                partitions--;
            }

            if (partitions == 0) {
                return false;
            }
        }

        return true;
    }
    
    public static void main(String[] args) {
        SplitArrayLargestSum obj = new SplitArrayLargestSum();

        int arr[] = new int[] { 7, 2, 5, 10, 8 };

        System.out.println(obj.splitArray(arr, 2));
    }
}
