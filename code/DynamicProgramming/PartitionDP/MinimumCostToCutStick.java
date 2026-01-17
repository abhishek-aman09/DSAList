package DynamicProgramming.PartitionDP;

import java.util.Arrays;

public class MinimumCostToCutStick {
    
    /** https://leetcode.com/problems/minimum-cost-to-cut-a-stick/description/
     * Given a wooden stick of length n units. The stick is labelled from 0 to n.
     * Given an integer array cuts where cuts[i] denotes a position you should perform a cut at.
    
        You should perform the cuts in order, you can change the order of the cuts as you wish.
    
        The cost of one cut is the length of the stick to be cut,
        the total cost is the sum of costs of all cuts. When you cut a stick, 
        it will be split into two smaller sticks (i.e. the sum of their lengths is the length of the stick before the cut). 
    
        Approach Similar to MCM.
        We need to define a new array with boundaries i.e. 0 and n at both ends
        this will help us determine the length of the rod without passing the length


    */


    public int minCost(int n, int[] cuts) {

        int len = cuts.length;

        if (len < 1) {
            return 0;
        }

        // defing a new array with boundary
        int cutsWithBoundary[] = new int[len + 2];

        cutsWithBoundary[0] = 0;
        cutsWithBoundary[len + 1] = n;

        for (int i = 1; i < len + 1; i++) {
            cutsWithBoundary[i] = cuts[i - 1];
        }

        Arrays.sort(cutsWithBoundary);

        int dp[][] = new int[len + 2][len + 2];

        for(int row[] : dp) {
            Arrays.fill(row, -1);
        }


        return helper(cutsWithBoundary, 0, len + 1, dp);
        
    }

    private int helper(int cuts[], int i, int j, int dp[][]) {
        // here for each call i is left boundary and j is the right one
        // base condition is to check that we have at least one cut point
        // between i and j
        if (j - i <= 1) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int minCost = Integer.MAX_VALUE;

        // As i and j are boundaries, k will go from i + 1 to j - 1 (excluding the boundaries)
        // the sub calls will also be made to left cut i.e i to k and right cut i.e k to j
        for (int k = i + 1; k < j; k++) {
            int cost = cuts[j] - cuts[i] + helper(cuts, i, k, dp) + helper(cuts, k , j, dp);
            if (cost < minCost) {
                minCost = cost;
            }
        }

        return dp[i][j] = minCost;
    }

    public static void main(String[] args) {
        int arr[] = { 5 };

        MinimumCostToCutStick obj = new MinimumCostToCutStick();

        System.out.println(obj.minCost(7, arr));
    }
}
