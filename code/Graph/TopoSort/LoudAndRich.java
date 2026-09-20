package Graph.TopoSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LoudAndRich {

    /*
    * ============================================================================
    * LEETCODE 851: Loud and Rich
    * ============================================================================
    * Link: https://leetcode.com/problems/loud-and-rich/
    *
    * DESCRIPTION:
    * There is a group of n people labeled from 0 to n - 1 where each person has a 
    * different amount of money and a different level of quietness.
    *
    * You are given an array richer where richer[i] = [ai, bi] indicates that ai has 
    * more money than bi and an integer array quiet where quiet[i] is the quietness of 
    * the i-th person. All the values of quiet are unique.
    *
    * Return an integer array answer where answer[x] = y if y is the least quiet person 
    * (that is, the person y with the smallest value of quiet[y]) among all people who 
    * definitely have equal to or more money than person x.
    *
    * ----------------------------------------------------------------------------
    * EXAMPLE:
    * Input: richer = [[1,0],[2,1],[3,1],[3,7],[4,3],[5,3],[6,3]], 
    *        quiet = [3,2,5,4,6,1,7,0]
    * Output: [5,5,2,5,4,5,6,7]
    * Explanation: 
    * answer[0] = 5: Person 5 has more money than 3, who has more than 1, who has more than 0.
    * Person 5's quietness is 1, which is quieter than anyone else richer than person 0.
    *
    * ----------------------------------------------------------------------------
    * APPROACH: Kahn's Algorithm (Topological Sort / DP on DAG)
    *
    * 1. DAG Property:
    *    - The "richer than" relationship cannot contain cycles (a person cannot be 
    *      strictly richer than themselves). Hence, the graph is a DAG.
    *    - Direct edge: u -> v means "u is richer than v".
    *
    * 2. Downstream Value Propagation:
    *    - Since edges flow from "richer" to "poorer", a node v should inherit 
    *      the quietest person among:
    *        a) Itself (quiet[v])
    *        b) The quietest candidate found among all nodes that are richer than v (its parents)
    *    - By traversing in topological order (starting from nodes with indegree == 0, 
    *      the richest people who have no one richer than them), every parent `u` has 
    *      its optimal quietest ancestor finalized before `v` processes it.
    *
    * 3. Dynamic Programming State:
    *    - ans[i]: the index of the quietest person among all people richer than or equal to i.
    *    - quitestOfNode[i]: the actual minimum quietness value among those people.
    *    - Transition: when processing edge u -> v:
    *        if (quitestOfNode[u] < quitestOfNode[v]) {
    *            quitestOfNode[v] = quitestOfNode[u];
    *            ans[v] = ans[u];
    *        }
    *
    * 4. Complexity:
    *    - Time Complexity:  O(V + E) where V = quiet.length and E = richer.length.
    *                        Every node enters the queue once, and each directed edge 
    *                        is traversed once. Far superior to Floyd-Warshall's O(V^3).
    *    - Space Complexity: O(V + E) for the adjacency list, indegree array, and queue.
    * ============================================================================
    */


    public int[] loudAndRich(int[][] richer, int[] quiet) {
        int n = quiet.length;

        // graph[u] stores all people poorer than u (edge: richer -> poorer)
        List<Integer>[] graph = new ArrayList[n];
        int[] indegree = new int[n];
        
        // quitestOfNode[i] tracks the minimum quietness value found for person i so far
        int[] quitestOfNode = new int[n];

        // ans[i] tracks the person ID who achieved that minimum quietness value
        int[] ans = new int[n];

        // Step 1: Initialize base states: each person starts as their own candidate
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
            ans[i] = i;
            quitestOfNode[i] = quiet[i];
        }

        // Step 2: Build graph (u -> v: u is richer than v) and compute in-degrees
        for (int[] edge : richer) {
            int u = edge[0]; // Richer person
            int v = edge[1]; // Poorer person

            graph[u].add(v);
            indegree[v]++;
        }

        // Step 3: Kahn's Algorithm - seed queue with richest people (indegree == 0)
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        // Step 4: Process DAG in topological order, pushing minimum quietness downstream
        while (!q.isEmpty()) {
            int node = q.poll();
            int parQuietVal = quitestOfNode[node];

            for (int child : graph[node]) {
                int childQuietVal = quitestOfNode[child];

                // If the parent (or its ancestors) has someone quieter, propagate to child
                if (parQuietVal < childQuietVal) {
                    quitestOfNode[child] = parQuietVal;
                    ans[child] = ans[node];
                }

                indegree[child]--;

                // Once all richer predecessors of child are processed, child is ready
                if (indegree[child] == 0) {
                    q.add(child);
                }
            }
        }

        return ans;
    }
    
}
