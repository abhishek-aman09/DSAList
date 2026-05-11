package Google.Heaps;

import java.util.PriorityQueue;

public class MinRefuelingStops {
    
    /*
    https://leetcode.com/problems/minimum-number-of-refueling-stops/description/
    
    A car travels from a starting position to a destination which is target miles east of the starting position.
    
    There are gas stations along the way. The gas stations are represented as an array stations where stations[i] = [positioni, fueli] indicates that the ith gas station is positioni miles east of the starting position and has fueli liters of gas.
    
    The car starts with an infinite tank of gas, which initially has startFuel liters of fuel in it. It uses one liter of gas per one mile that it drives. When the car reaches a gas station, it may stop and refuel, transferring all the gas from the station into the car.
    
    Return the minimum number of refueling stops the car must make in order to reach its destination. If it cannot reach the destination, return -1.
    
    Note that if the car reaches a gas station with 0 fuel left, the car can still refuel there. If the car reaches the destination with 0 fuel left, it is still considered to have arrived.
    
    Input: target = 100, startFuel = 10, stations = [[10,60],[20,30],[30,30],[60,40]]
    Output: 2
    Explanation: We start with 10 liters of fuel.
    We drive to position 10, expending 10 liters of fuel.  We refuel from 0 liters to 60 liters of gas.
    Then, we drive from position 10 to position 60 (expending 50 liters of fuel),
    and refuel from 10 liters to 50 liters of gas.  We then drive to and reach the target.
    We made 2 refueling stops along the way, so we return 2.
    
    Approach : create a max heap on fuel capacity. pop out the top if you run out of fuel.
    
    
    */

    public int minRefuelStops(int target, int startFuel, int[][] stations) {

        int n = stations.length;

        int currCapacity = startFuel;
        int currIndex = 0;
        int numberOfStops = 0;

        // construct a max heap with the max capacity of fuel at the top
        PriorityQueue<Integer> maxCapacityHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

        while (currCapacity < target) {


            // iterate throught the array and put the station in range into the
            // max heap
            while (currIndex < n && currCapacity >= stations[currIndex][0]) {

                maxCapacityHeap.offer(stations[currIndex][1]);
                currIndex++;
            }

            // if we have no more stations left, we cannot reach target
            if (maxCapacityHeap.isEmpty()) {
                return -1;
            }

            // we poll the max fuel and add its cap to our current capacity.
            numberOfStops++;
            currCapacity = currCapacity + maxCapacityHeap.poll();

        }

        return numberOfStops;

    }
    
    public static void main(String[] args) {
        MinRefuelingStops obj = new MinRefuelingStops();

        int[][] stations = new int[][] { { 10, 60 }, { 20, 30 }, { 30, 30 }, { 60, 40 } };
        int startFuel = 10;
        int target = 100;

        System.out.println(obj.minRefuelStops(target, startFuel, stations));
    }
    

}
