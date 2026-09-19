package Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class CourseScheduleIV {

    /*
    * ============================================================================
    * LEETCODE 1462: Course Schedule IV
    * ============================================================================
    * Link: https://leetcode.com/problems/course-schedule-iv/
    *
    * DESCRIPTION:
    * There are a total of numCourses courses you have to take, labeled from 0 to 
    * numCourses - 1. You are given an array prerequisites where 
    * prerequisites[i] = [a, b] indicates that you must take course a first if 
    * you want to take course b.
    *
    * Prerequisites can be indirect. If course a is a prerequisite of course b, 
    * and course b is a prerequisite of course c, then course a is a prerequisite 
    * of course c.
    *
    * You are also given an array queries where queries[j] = [uj, vj]. For the 
    * j-th query, you should answer whether course uj is a prerequisite of course vj.
    *
    * Return a boolean array answer, where answer[j] is the answer to the j-th query.
    *
    * ----------------------------------------------------------------------------
    * EXAMPLE:
    * Input: numCourses = 3, prerequisites = [[1,2],[1,0],[2,0]], queries = [[1,0],[1,2]]
    * Output: [true, true]
    * Explanation: Course 1 is a direct prerequisite of both course 0 and course 2.
    *
    * ----------------------------------------------------------------------------
    * APPROACH: Kahn's Algorithm (Topological Sort) + Reachability Propagation
    *
    * 1. Problem Essence:
    *    We need to compute the transitive closure (reachability) of a Directed 
    *    Acyclic Graph (DAG) and answer offline reachability queries in O(1).
    *
    * 2. Why Topological Sort?
    *    - The prerequisites relation contains no cycles (it's a valid DAG).
    *    - If we process nodes in topological order using Kahn's algorithm (indegree-based BFS):
    *        When edge `u -> v` is explored, `u` and all prerequisite ancestors of `u` 
    *        must also be prerequisites of `v`.
    *    - By propagating the set of dependencies downstream:
    *        listOfDep[v].addAll(listOfDep[u])
    *        listOfDep[v].add(u)
    *      we ensure that once indegree[v] reaches 0, `listOfDep[v]` contains ALL 
    *      direct and indirect prerequisites of `v`.
    *
    * 3. Query Phase:
    *    - For each query [u, v], check if `listOfDep[v].contains(u)` in average O(1) time.
    *
    * 4. Complexity:
    *    - Time Complexity:  O(V + E * V + Q)
    *                        - Graph building: O(V + E)
    *                        - Topo-sort traversal: O(V + E)
    *                        - Set merging per edge: O(V) per edge -> O(E * V) overall
    *                        - Query lookups: O(Q * 1) = O(Q)
    *                        With V <= 100, E <= 5000, Q <= 10^4, total ops ~ 5 * 10^5 (fast).
    *    - Space Complexity: O(V^2 + E + Q) for storing adjacency lists, reachability sets, 
    *                        and the result list.
    * ============================================================================
    */

    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] pre, int[][] queries) {

        // graph[u] contains all direct dependents of u (courses that require u)
        List<Integer>[] graph = new ArrayList[numCourses];
        int[] inDegree = new int[numCourses];

        // listOfDep[v] stores all direct and indirect prerequisites of course v
        Set<Integer>[] listOfDep = new HashSet[numCourses];

        // Initialize adjacency lists and dependency sets
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
            listOfDep[i] = new HashSet<>();
        }

        // Build the directed graph and calculate indegrees: edge u -> v means u is prerequisite of v
        for (int[] edge : pre) {
            int u = edge[0];
            int v = edge[1];

            graph[u].add(v);
            inDegree[v]++;
        }

        // Propagate dependencies downstream in topological order
        getDepViaTopoSort(graph, inDegree, listOfDep);

        // Process all queries in O(1) average lookup time per query
        List<Boolean> ans = new ArrayList<>(queries.length);
        for (int[] q : queries) {
            int u = q[0];
            int v = q[1];

            // Check if u is in the prerequisite set of v
            ans.add(listOfDep[v].contains(u));
        }

        return ans;
    }

    /**
     * Executes Kahn's Algorithm to process nodes in topological order, 
     * propagating all prerequisite sets downstream to their dependents.
     */
    private void getDepViaTopoSort(List<Integer>[] graph, int[] inDegree, Set<Integer>[] listOfDep) {

        Queue<Integer> queue = new LinkedList<>();

        // Seed queue with courses that have no prerequisites (indegree == 0)
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            Set<Integer> currentPrereqs = listOfDep[currentNode];

            // For each dependent course, inherit all prerequisites of currentNode
            for (int child : graph[currentNode]) {
                inDegree[child]--;

                // Transitive closure: child inherits all of currentNode's prerequisites + currentNode itself
                listOfDep[child].addAll(currentPrereqs);
                listOfDep[child].add(currentNode);

                // When all incoming dependencies of child are resolved, enqueue it
                if (inDegree[child] == 0) {
                    queue.add(child);
                }
            }
        }
    }
    
}
