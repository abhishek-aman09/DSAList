package Google.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class BusRoutes {
    
    /*
    
    https://leetcode.com/problems/bus-routes/description/
    
    You are given an array routes representing bus routes where routes[i] is a bus route that the ith bus repeats forever.
    
    For example, if routes[0] = [1, 5, 7], this means that the 0th bus travels in the sequence 
    1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... forever.
    You will start at the bus stop source (You are not on any bus initially), and you want to go to the bus stop target. 
    You can travel between bus stops by buses only.
    
    Return the least number of buses you must take to travel from source to target. Return -1 if it is not possible.
    
    Approach :
    create a graph which maps stop number to the buses that stop, like bus 1, 2, 3, 4 stops at stop B, bus 2, 5, 6, 7 stops at stop B
    
    start with stop source with steps 0, check all the buses you can take from there, mark them visited and push all other buses
    that pass through that stop into the queues, 
    them.
    */
   
    public int numBusesToDestination(int[][] routes, int source, int target) {

        if(source == target) {
            return 0;
        }

        // Map stop number to the list of bus which go there
        Map<Integer, List<Integer>> graph = new HashMap<>();

        Queue<Integer> q = new LinkedList<>();

        // visited array will keep track of a bus route that is already visited or in the queue
        boolean isVisited[] = new boolean[routes.length];

        Arrays.fill(isVisited, false);

        // create graph
        for(int i = 0; i < routes.length; i++) {
            for(int stop : routes[i]) {
                graph.computeIfAbsent(stop, key -> new ArrayList<>()).add(i);
            }
        }

        int stops = 0;
        q.offer(source);
        q.offer(null);

        while(!q.isEmpty()) {
            Integer curr = q.poll(); // get the current stop

            if(Objects.isNull(curr)) { // standard level order stops increment using null
                if(!q.isEmpty()) {
                    q.offer(null);
                }

                stops++;
                continue;
            }

            if(curr == target) { // if we have reached destination, return the number of stops taken
                return stops;
            }

            List<Integer> connectedStops = graph.get(curr); // get list of buses routes that pass through the stop

            if(Objects.isNull(connectedStops)) {
                continue;
            }

            for(int route : connectedStops) { // for each bus route
                if(!isVisited[route]) { // if unvisited,
                isVisited[route] = true; // mark it visited
                    for(int subStops : routes[route]) { // and all the other stops in the route, push them into the queue
                        q.offer(subStops);
                    }
                }
            }
        }

        return -1;
        
    }
}
