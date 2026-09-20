package Graph.FloydWarshal;

public class CityAtSmallestDistWithMinNeighbour {

    /*
    * ============================================================================
    * LEETCODE 1334: Find the City With the Smallest Number of Neighbors at a Threshold Distance
    * ============================================================================
    * Link: https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/
    *
    * DESCRIPTION:
    * There are n cities numbered from 0 to n - 1. Given the array edges where 
    * edges[i] = [fromi, toi, weighti] represents a bidirectional and weighted edge 
    * between cities fromi and toi, and given the integer distanceThreshold.
    *
    * Return the city with the smallest number of cities that are reachable through 
    * some path and whose distance is at most distanceThreshold. If there are 
    * multiple such cities, return the city with the greatest number.
    *
    * Notice that the distance of a path connecting cities i and j is equal to the 
    * sum of the edges' weights along that path.
    *
    * ----------------------------------------------------------------------------
    * EXAMPLE:
    * Input: n = 4, edges = [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], distanceThreshold = 4
    * Output: 3
    * Explanation:
    * The neighboring cities at a distanceThreshold = 4 for each city are:
    * - City 0 -> [City 1, City 2] (count = 2)
    * - City 1 -> [City 0, City 2, City 3] (count = 3)
    * - City 2 -> [City 0, City 1, City 3] (count = 3)
    * - City 3 -> [City 1, City 2] (count = 2)
    * Cities 0 and 3 have 2 reachable neighbors (the minimum).
    * City 3 is chosen because it has the largest city label among them.
    *
    * ----------------------------------------------------------------------------
    * APPROACH: Floyd-Warshall Algorithm (All-Pairs Shortest Path)
    *
    * 1. Why Floyd-Warshall?
    *    - Constraints: n <= 100, which is small enough that an O(n^3) solution 
    *      runs comfortably (100^3 = 10^6 operations, < 15ms in Java).
    *    - We need shortest path distances between EVERY pair of cities (i, j) 
    *      to evaluate reachable neighbors within distanceThreshold.
    *
    * 2. Safe Infinity (Avoiding Integer Overflow):
    *    - If we used Integer.MAX_VALUE, `dist[i][via] + dist[via][j]` would roll 
    *      over into negative numbers (signed 32-bit integer overflow).
    *    - Setting INF = Integer.MAX_VALUE / 4 (or 1e9 / 1e8) completely prevents 
    *      overflow during additions while remaining larger than any possible path sum 
    *      (max possible path in this problem is 100 * 10^4 = 10^6).
    *
    * 3. Dynamic Programming DP Transition:
    *    - Outer loop MUST be intermediate/intermediate bridge node `via` (k):
    *        dist[i][j] = min(dist[i][j], dist[i][via] + dist[via][j])
    *    - Base cases:
    *        dist[i][i] = 0
    *        dist[i][j] = edge weight for direct bidirectional edges
    *        dist[i][j] = INF if no direct edge connects i and j
    *
    * 4. Tie-Breaking Condition:
    *    - Condition: "If there are multiple such cities, return the city with the greatest number."
    *    - Since we iterate i from 0 up to n - 1:
    *        `if (currCount <= minNeighbour)`
    *      Using `<=` automatically overwrites the chosen node with the larger city index `i` 
    *      whenever counts are tied.
    *
    * 5. Complexity:
    *    - Time Complexity:  O(n^3) for the 3 nested loops of Floyd-Warshall + O(n^2) for neighbor counting.
    *                        Total operations ~ 10^6, easily passing within the 2-second limit.
    *    - Space Complexity: O(n^2) auxiliary matrix space to store distances between all city pairs.
    * ============================================================================
    */

    public int findTheCity(int n, int[][] edges, int distanceThreshold) {

        // Use a safe upper bound to prevent integer overflow when adding two distances
        final int INF = Integer.MAX_VALUE / 4;

        // dist[i][j] will store the shortest distance from city i to city j
        int[][] dist = new int[n][n];

        // Step 1: Initialize adjacency matrix
        // Distance to self is 0; distance to other nodes starts as INF
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    dist[i][j] = INF;
                }
            }
        }

        // Step 2: Populate direct bidirectional edge weights
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];

            dist[u][v] = weight;
            dist[v][u] = weight;
        }

        // Step 3: Floyd-Warshall Algorithm
        // Essential: The intermediate vertex `via` MUST be the outermost loop
        for (int via = 0; via < n; via++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // Check if path through `via` offers a shorter route
                    if (dist[i][j] > dist[i][via] + dist[via][j]) {
                        dist[i][j] = dist[i][via] + dist[via][j];
                    }
                }
            }
        }

        // Step 4: Count reachable neighbors within distanceThreshold for each city
        int minNeighbour = INF;
        int bestCityNode = 0;

        for (int i = 0; i < n; i++) {
            int currCount = 0;

            for (int j = 0; j < n; j++) {
                // Ignore self-distance (i != j)
                if (i != j && dist[i][j] <= distanceThreshold) {
                    currCount++;
                }
            }

            // Using '<=' naturally satisfies the tie-breaking rule:
            // If two cities have the same minimum count, the one with the larger index overwrites
            if (currCount <= minNeighbour) {
                bestCityNode = i;
                minNeighbour = currCount;
            }
        }

        return bestCityNode;
    }
    
}
