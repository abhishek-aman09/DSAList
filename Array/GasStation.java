package Array;

public class GasStation {
    // https://leetcode.com/problems/gas-station/description/

    /*
    There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].

    You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith station to its next (i + 1)th station. You begin the journey with an empty tank at one of the gas stations.

    Given two integer arrays gas and cost, return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1. If there exists a solution, it is guaranteed to be unique.    
    */    
    public int canCompleteCircuit(int[] gas, int[] cost) {

        int n = gas.length;

        long sum = 0;

        long minSum = Long.MAX_VALUE;
        int firstPositive = -1;

        for (int i = 0; i < n; i++) {

            int currDiff = gas[i] - cost[i];
            sum += currDiff;

            minSum = Long.min(minSum, sum);

            if (currDiff >= 0 && firstPositive == -1) {
                firstPositive = i;
            } else if (sum <= minSum && sum < 0) {
                firstPositive = -1;
            }
        }

        if (sum >= 0) {
            return firstPositive;
        }

        return -1;

    }
    
    public static void main(String[] args) {
        GasStation obj = new GasStation();

        int gas[] = { 3, 1, 1 };
        int cost[] = { 1, 2, 2 };

        System.out.println(obj.canCompleteCircuit(gas, cost));
    }

       

}
