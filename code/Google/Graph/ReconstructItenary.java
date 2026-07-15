package Google.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReconstructItenary {

    /*
    https://leetcode.com/problems/reconstruct-itinerary/description/
    
    You are given a list of airline tickets where tickets[i] = [fromi, toi] represent the departure and the arrival airports 
    of one flight. Reconstruct the itinerary in order and return it.
    
    All of the tickets belong to a man who departs from "JFK", thus, the itinerary must begin with "JFK". 
    If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order 
    when read as a single string.
    
    For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
    You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.
    
    Input: tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
    Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
    Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"] but it is larger in lexical order.
    
    Approach : Standard Hierholzer's Algorithm to find a Eulers path in a graph.
    
    We store the detinations from each airports in reverse lexical order and perform the standard Heirholzer's algorithm.
    
    */

    public List<String> findItinerary(List<List<String>> tickets) {

        Map<String, List<String>> graph = new HashMap<>();

        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            // create the graph
            graph.computeIfAbsent(from, key -> new ArrayList<>()).add(to);
        }

        // sort the destination list in reverse order.
        for (String key : graph.keySet()) {
            List<String> dest = graph.get(key);
            Collections.sort(dest, (a, b) -> b.compareTo(a));
            graph.put(key, dest);
        }

        // list to store the path
        List<String> path = new ArrayList<>();

        // call dfs with JFK as source
        getEulersPath(graph, "JFK", path);

        // the path formed is reversed, so return the reverse of the path
        return path.reversed();

    }
    
    private void getEulersPath(Map<String, List<String>> graph, String curr, List<String> currPath) {

        List<String> dest = graph.get(curr);
        // iterate the dest list of the current airport
        while (graph.get(curr) != null && !dest.isEmpty()) {
            int desLen = dest.size(); // fetch the size of list
            String nextDest = dest.get(desLen - 1); // take the last (lexically smallest) dest first
            graph.get(curr).remove(desLen - 1); // pop out the used destination from list(visited)
            getEulersPath(graph, nextDest, currPath); // call for its child
        } // this call will stop at final dest as it will have no more outgoing flights

        currPath.add(curr); // finally add the current into the path
    }
    
}
