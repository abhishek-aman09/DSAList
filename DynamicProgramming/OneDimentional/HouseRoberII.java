package DynamicProgramming.OneDimentional;

import java.util.Arrays;

public class HouseRoberII {

    // Combination of circular list with maximumSumWithNoAdjacent
    /*
     * https://www.naukri.com/code360/problems/house-robber_839733?leftPanelTabValue=PROBLEM
     * All houses along this street are arranged in a circle.
     *  That means the first house is the neighbour of the last one.
     *  Meanwhile, adjacent houses have a security system connected,
     *  and it will automatically contact the police if two adjacent houses are broken into on the same night
     */

    public static long houseRobber(int[] valueInHouse) {
        int n = valueInHouse.length;

        if (n == 1) {
            return valueInHouse[0];
        }

        int[] dp = new int[n];
        Arrays.fill(dp, -1);

        // leaving last element
        int ansWithoutLastElement = solve(n - 1, valueInHouse, dp);

        for (int i = 0; i < n - 1; i++) {
            valueInHouse[i] = valueInHouse[i + 1];
        }
        Arrays.fill(dp, -1);

        // leaving the first element
        int ansWithoutFirtstElement = solve(n - 1, valueInHouse, dp);

        return Integer.max(ansWithoutLastElement, ansWithoutFirtstElement);
        
    }
    
    private static int solve(int n, int[] value, int[] dp) {
        if (n <= 0) {
            return 0;
        }

        if (dp[n] != -1) {
            return dp[n];
        }

        return dp[n] = Integer.max(
            solve(n - 1, value, dp),
            value[n - 1] + solve(n - 2, value, dp));
    }
    
    public static void main(String[] args) {
        int[] list = {1,3,2,1};
        System.out.println("Value is " + houseRobber(list));
    }
}
