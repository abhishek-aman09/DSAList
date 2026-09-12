package Array;

public class GasStation {
    // https://leetcode.com/problems/gas-station/description/

    /*
    There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].
    
    You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith station to its next (i + 1)th station. You begin the journey with an empty tank at one of the gas stations.
    
    Given two integer arrays gas and cost, return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1. 
    If there exists a solution, it is guaranteed to be unique.    
    
    Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
    Output: 3
    Explanation:
    Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
    Travel to station 4. Your tank = 4 - 1 + 5 = 8
    Travel to station 0. Your tank = 8 - 2 + 1 = 7
    Travel to station 1. Your tank = 7 - 3 + 2 = 6
    Travel to station 2. Your tank = 6 - 4 + 3 = 5
    Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
    Therefore, return 3 as the starting index.
    
    approach : similar to kadence algo. keep a sum variable that store the extra gas we can have. if that sum becomes positive, we can start from there
    if after becoming positive, it becomes negative again (reaching the minima), we reset the value as we cannot complete the circuit.
    */    
    public int canCompleteCircuit(int[] gas, int[] gasNeeded) {

        int n = gas.length;

        long sum = 0;

        long minSum = Long.MAX_VALUE;
        int firstPositive = -1; // variable to store the first positive index from where we can start travelling

        for (int i = 0; i < n; i++) {

            int currDiff = gas[i] - gasNeeded[i];
            sum += currDiff; // store the sum

            minSum = Long.min(minSum, sum); // this is to store the minima of the balance we would require to complete circuit
            // for gas = [6,1,4,3,5] and cost = [3,8,2,4,2] we get the diff array as sum as 3, -4, -2, -3 and 0. So we need to store -4 in order to reset firstPositive. we dont reset it just because sum went from -2 to -3.
            if (currDiff >= 0 && firstPositive == -1) { // if we have positive balance update it
                firstPositive = i;
            } else if (sum == minSum && sum < 0) { // if sum has reached minima, we reset the first positive.
                firstPositive = -1;
            }
        }

        if (sum >= 0) { // check if we have sum as non negative integer.
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
