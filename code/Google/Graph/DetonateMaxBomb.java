package Google.Graph;

import java.util.ArrayList;
import java.util.List;

public class DetonateMaxBomb {

    /*
    https://leetcode.com/problems/detonate-the-maximum-bombs/description/
    
    You are given a list of bombs. The range of a bomb is defined as the area where its effect can be felt. 
    This area is in the shape of a circle with the center as the location of the bomb.
    
    The bombs are represented by a 0-indexed 2D integer array bombs where bombs[i] = [xi, yi, ri]. 
    xi and yi denote the X-coordinate and Y-coordinate of the location of the ith bomb, whereas ri denotes the radius of its range.
    
    You may choose to detonate a single bomb. When a bomb is detonated, it will detonate all bombs that lie in its range. 
    These bombs will further detonate the bombs that lie in their ranges.
    
    Given the list of bombs, return the maximum number of bombs that can be detonated if you are allowed to detonate only one bomb.
    
    Input: bombs = [[2,1,3],[6,1,4]]
    Output: 2
    Explanation:
    The above figure shows the positions and ranges of the 2 bombs.
    If we detonate the left bomb, the right bomb will not be affected.
    But if we detonate the right bomb, both bombs will be detonated.
    So the maximum bombs that can be detonated is max(1, 2) = 2.
    
    Approach : construct a directed graph from the given data.
    Why directed? There could be scenarios where Bomb A could detonate Bomb B but Bomb B cannot detonate A
    
    create a visited array for each dfs call and call dfs to count the number of bombs that can be detaonated from each bomb.
    return the max
    
    
    */

    public int maximumDetonation(int[][] bombs) {

        int n = bombs.length;

        List<Integer>[] graph = new ArrayList[n]; // array of size n to store which index can be detonated from which index

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                boolean isRelated = isWithinBlastRadius(bombs[i], bombs[j]); // if centre of b is within blast radius of a

                if (isRelated) { // form a directed edge and vice versa
                    graph[i].add(j);
                }

                isRelated = isWithinBlastRadius(bombs[j], bombs[i]);

                if (isRelated) {
                    graph[j].add(i);
                }
            }
        }

        int maxDetonation = 0;

        for (int i = 0; i < n; i++) {

            boolean isVisited[] = new boolean[n]; // new visited array for each iteration
            int count = dfs(graph, i, isVisited);
            maxDetonation = Integer.max(maxDetonation, count);

        }

        return maxDetonation;

    }
    // basic dfs block to count the number of vertices
    private int dfs(List<Integer> graph[], int index, boolean isVisited[]) {

        isVisited[index] = true;
        int count = 1; 

        List<Integer> children = graph[index];

        if (children.isEmpty()) {
            return count;
        }

        for (int child : children) {
            if (!isVisited[child]) {
                count += dfs(graph, child, isVisited);
            }
        }

        return count;
    }
    // method to check if the center of b2 is within blast radius of b1
    private boolean isWithinBlastRadius(int b1[], int b2[]) {

        long r1 = b1[2];

        long x1 = Math.abs(b1[0] - b2[0]);
        long y1 = Math.abs(b1[1] - b2[1]);

        long dist = (x1 * x1) + (y1 * y1);

        return dist <= (r1 * r1);

    }
    
}
