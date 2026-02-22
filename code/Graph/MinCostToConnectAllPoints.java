package Graph;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class MinCostToConnectAllPoints {

    // https://leetcode.com/problems/min-cost-to-connect-all-points/description
    /*
    You are given an array points representing integer coordinates 
    of some points on a 2D-plane, where points[i] = [xi, yi].
    
    The cost of connecting two points [xi, yi] and [xj, yj] 
    is the manhattan distance between them: |xi - xj| + |yi - yj|, 
    where |val| denotes the absolute value of val.
    
    Return the minimum cost to make all points connected. 
    All points are connected if there is exactly one simple path between any two points.
    
    Input: points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
    Output: 20
    
    Approach : Perform minimum spanning tree.
    catch : we don't need any parent node, any point can connect to any point
    so, for each unvisited point, we have to traverse the whole point array
    to check which all coordianted are left to iterate.
    
    as they are coordintes, we use a hash fuction to calculte the hash key
    based on x and y coordinate.
    
    solution can be made efficient by
    1. precompute the hasKey for all points and make them false initially
    2. instead of using Edge class, we can use PriorityQueue<int[]> 
    3. Break out of queue if isVisted size == points.length
    
    
    */

    public int minCostConnectPoints(int[][] points) {

        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.dist, b.dist));

        pq.add(new Edge(points[0][0], points[0][1], 0));
        int ans = 0;
        Map<Integer, Boolean> isVisited = new HashMap<>();

        while (!pq.isEmpty()) {
            Edge curr = pq.poll();

            int hashKey = getHashFunction(curr.x, curr.y);

            if (isVisited.containsKey(hashKey)) {
                continue;
            }

            ans += curr.dist;
            isVisited.put(hashKey, true);

            for (int cor[] : points) {
                hashKey = getHashFunction(cor[0], cor[1]);
                if (!isVisited.containsKey(hashKey)) {
                    int dist = Math.abs(cor[0] - curr.x) + Math.abs(cor[1] - curr.y);
                    pq.add(new Edge(cor[0], cor[1], dist));
                }
            }
        }

        return ans;
    }
    
    private int getHashFunction(int x, int y) {
        // Scramble the bits so high-value coordinates don't cluster
        int h1 = x * 0x85ebca6b;
        int h2 = y * 0xc2b2ae35;
        
        // Combine and fold
        int combined = h1 ^ (h2 >>> 13);
        combined = combined * 0x85ebca6b;
        
        return combined ^ (combined >>> 16);
    }

    private static class Edge {
        int x;
        int y;
        int dist;

        Edge(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    public static void main(String[] args) {
        int points[][] = { { 0, 0 }, { 2, 2 }, { 3, 10 }, { 5, 2 }, { 7, 0 } };

        MinCostToConnectAllPoints obj = new MinCostToConnectAllPoints();

        System.out.println(obj.minCostConnectPoints(points));
    }

}
