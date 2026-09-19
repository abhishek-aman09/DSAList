package Google.Graph;

public class FindMinCityAtThreshold {

    /*
    
    https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/
    
    There are n cities numbered from 0 to n-1. Given the array edges where edges[i] = [fromi, toi, weighti] represents a bidirectional and weighted edge between cities fromi and toi, 
    and given the integer distanceThreshold.
    
    Return the city with the smallest number of cities that are reachable through some path and whose distance is at most distanceThreshold, 
    If there are multiple such cities, return the city with the greatest number.
    
    Notice that the distance of a path connecting cities i and j is equal to the sum of the edges' weights along that path.
    
    Input: n = 5, edges = [[0,1,2],[0,4,8],[1,2,3],[1,4,2],[2,3,1],[3,4,1]], distanceThreshold = 2
    Output: 0
    Explanation: The figure above describes the graph. 
    The neighboring cities at a distanceThreshold = 2 for each city are:
    City 0 -> [City 1] 
    City 1 -> [City 0, City 4] 
    City 2 -> [City 3, City 4] 
    City 3 -> [City 2, City 4]
    City 4 -> [City 1, City 2, City 3] 
    The city 0 has 1 neighboring city at a distanceThreshold = 2.
    
    * ============================================================================
    * APPROACH: Floyd-Warshall Algorithm (All-Pairs Shortest Path)
    * ============================================================================
    * 
    * 1. Problem Essence:
    *    We need to find a city that has the smallest number of reachable cities 
    *    within a given `distanceThreshold`. If there is a tie, we break it by 
    *    selecting the city with the greatest numerical index.
    *
    * 2. Why Floyd-Warshall?
    *    - Constraints: n <= 100.
    *    - Because n is small, an O(n^3) All-Pairs Shortest Path algorithm is 
    *      ideal and trivial to implement.
    *    - Floyd-Warshall computes the shortest path between every pair of vertices 
    *      (i, j) by testing whether routing through an intermediate node ("via") 
    *      yields a shorter path.
    *
    * 3. Handling Overflow:
    *    - We initialize non-diagonal distances with `INF = Integer.MAX_VALUE / 4`.
    *    - Using a fractional MAX_VALUE avoids 32-bit signed integer overflow when 
    *      computing `dist[i][via] + dist[via][j]`.
    *
    * 4. Tie-Breaking Strategy:
    *    - By iterating node index `i` from 0 up to n - 1 and updating the answer 
    *      whenever `currCount <= minNeighbour` (using `<=` instead of `<`), we 
    *      naturally favor the greater node index in the event of equal counts.
    *
    * 5. Complexity:
    *    - Time Complexity:  O(n^3) due to the three nested loops of Floyd-Warshall.
    *    - Space Complexity: O(n^2) for the n x n distance matrix.
    * ============================================================================
    */


    public int findTheCity(int n, int[][] edges, int distanceThreshold) {

        // Use a safe infinity constant to avoid integer overflow during addition
        final int INF = Integer.MAX_VALUE / 4;

        // dist[i][j] stores the shortest distance between city i and city j
        int dist[][] = new int[n][n];

        // 1. Initialize base distances: 0 to self, INF to all other cities
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    dist[i][j] = INF;
                }
            }
        }

        // 2. Populate direct edge weights (graph is bidirectional)
        for (int edge[] : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];

            dist[u][v] = w;
            dist[v][u] = w;
        }

        // 3. Floyd-Warshall: Try every node as an intermediate vertex ("via")
        for (int via = 0; via < n; via++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // Relaxation step: check if path i -> via -> j is shorter
                    if (dist[i][j] > dist[i][via] + dist[via][j]) {
                        dist[i][j] = dist[i][via] + dist[via][j];
                    }
                }
            }
        }

        // 4. Find the city with the minimum reachable cities within distanceThreshold
        int minNeighbour = INF;
        int node = 0;

        for (int i = 0; i < n; i++) {
            int currCount = 0;

            // Count reachable distinct cities from city i
            for (int j = 0; j < n; j++) {
                if (dist[i][j] <= distanceThreshold && i != j) {
                    currCount++;
                }
            }

            // Tie-breaker: '<=' ensures if two cities have the same count,
            // the city with the larger index overwrites 'node'.
            if (currCount <= minNeighbour) {
                node = i;
                minNeighbour = currCount;
            }
        }

        return node;
    }


    
}
