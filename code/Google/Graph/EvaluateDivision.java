package Google.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluateDivision {

    /*
    https://leetcode.com/problems/evaluate-division/description/
    
    You are given an array of variable pairs equations and an array of real numbers values, where equations[i] = [Ai, Bi] and values[i] represent the equation Ai / Bi = values[i]. Each Ai or Bi is a string that represents a single variable.
    
    You are also given some queries, where queries[j] = [Cj, Dj] represents the jth query where you must find the answer for Cj / Dj = ?.
    
    Return the answers to all queries. If a single answer cannot be determined, return -1.0.
    
    Note: The input is always valid. You may assume that evaluating the queries will not result in division by zero and that there is no contradiction.
    
    Note: The variables that do not occur in the list of equations are undefined, so the answer cannot be determined for them.
    
    
    Input: equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]
    Output: [6.00000,0.50000,-1.00000,1.00000,-1.00000]
    Explanation: 
    Given: a / b = 2.0, b / c = 3.0
    queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? 
    return: [6.0, 0.5, -1.0, 1.0, -1.0 ]
    note: x is undefined => -1.0
    
    
    Approach : 
    for any pair of a / b with val x, push b, x in child of a and a, 1/x in child of b.
    created a visited map for the same a and b as well.
    Now, do a dfs, if we find the target denominator, we return the fraction,
    if not, we return -1
    */
    
    private final Double undeterMinedFraction = -1.0d;

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {

        int len = equations.size();


        // Map to store graph of child with values for each node
        HashMap<String, List<Pair<String, Double>>> graph = new HashMap<>();
        // Map to store is visited
        Map<String, Boolean> isVisited = new HashMap<>();

        for (int i = 0; i < len; i++) {

            List<String> eq = equations.get(i);

            String u = eq.get(0);
            String v = eq.get(1);

            // push b and x in childList of a
            graph.computeIfAbsent(u, key -> new ArrayList<>()).add(new Pair<>(v, values[i]));
            // push a and 1 / x in childlist of b
            graph.computeIfAbsent(v, key -> new ArrayList<>()).add(new Pair<>(u, 1 / values[i]));
            isVisited.put(u, false);
            isVisited.put(v, false);

        }

        int qLen = queries.size();
        double ans[] = new double[qLen];
        int qIndex = 0;

        for (List<String> query : queries) {
            String u = query.get(0);
            String v = query.get(1);

            // for each query, reset the visited map
            resetVisited(isVisited);

            // if either node does not exist, we put -1
            if (!graph.containsKey(u) || !graph.containsKey(v)) {
                ans[qIndex++] = undeterMinedFraction;
                continue;
            } else if (u.equals(v)) { // if node divided by itself, we put one
                ans[qIndex++] = 1.0d;
                continue;
            }

            // else, we compute the fraction
            ans[qIndex++] = getFraction(u, 1.0d, v, isVisited, graph);

        }

        return ans;

    }
    
    private double getFraction(String u, double currFraction, String target,
            Map<String, Boolean> isVisited, Map<String, List<Pair<String, Double>>> graph) {
                    
        if (Boolean.TRUE.equals(isVisited.get(u))) {
            return -1;
        }

        // if the node does not have a child, we return -1
        isVisited.put(u, true);
        if (graph.get(u) == null) {
            return undeterMinedFraction;
        }

        List<Pair<String, Double>> childList = graph.get(u);
        
        // Iterate the child list of the current node
        for (Pair<String, Double> child : childList) {
            String v = child.denominator;
            Double fraction = child.value;

            // if child is the target, we return the fraction
            if (v.equals(target)) {
                return currFraction * fraction;
            }

            // else if any recursive call return positive value, return it
            if (Boolean.FALSE.equals(isVisited.get(v))) {
                double netFraction = getFraction(v, currFraction * fraction, target, isVisited, graph);
                if (netFraction != undeterMinedFraction) {
                    return netFraction;
                }
            }

        }

        return undeterMinedFraction;
    }
    
    private void resetVisited(Map<String, Boolean> isVisited) {
        for (String key : isVisited.keySet()) {
            isVisited.put(key, false);
        }
    }

    private static class Pair<K, V> {
        K denominator;
        V value;

        public Pair(K denominator, V value) {
            this.denominator = denominator;
            this.value = value;
        }
    }

}
