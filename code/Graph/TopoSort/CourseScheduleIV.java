package Graph.TopoSort;

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
    * numCourses - 1. You are given an array prerequisites where prerequisites[i] = [a, b] 
    * indicates that you must take course a first if you want to take course b.
    *
    * Prerequisites can be indirect. If course a is a prerequisite of course b, 
    * and course b is a prerequisite of course c, then course a is a prerequisite of course c.
    *
    * You are also given an array queries where queries[j] = [uj, vj]. For the j-th query, 
    * answer whether course uj is a prerequisite of course vj.
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
    * APPROACH: Kahn's Algorithm (Topological Sort) + Dependency Propagation
    *
    * 1. Problem Essence:
    *    We need to compute the transitive closure (reachability) of a Directed 
    *    Acyclic Graph (DAG) and answer offline reachability queries in O(1).
    *
    * 2. Why Topological Sort?
    *    - The prerequisites relation contains no cycles (it forms a valid DAG).
    *    - In topological order (indegree-based BFS), when edge `node -> child` is processed:
    *        `node` and ALL ancestors/prerequisites of `node` are also prerequisites of `child`.
    *    - Propagating downstream:
    *        listOfDep[child].addAll(listOfDep[node]);
    *        listOfDep[child].add(node);
    *      By the time `inDegree[child] == 0`, `listOfDep[child]` contains ALL direct 
    *      and indirect prerequisites of `child`.
    *
    * 3. Query Handling:
    *    - For each query [u, v], check whether `listOfDep[v].contains(u)` in average O(1) time.
    *
    * 4. Complexity:
    *    - Time Complexity:  O(V + E * V + Q)
    *                        - Graph building: O(V + E)
    *                        - Topo sort traversal: O(V + E)
    *                        - Set merging per edge: O(V) per edge -> O(E * V) total
    *                        - Query lookups: O(Q)
    *                        With V <= 100, E <= 5000, Q <= 10^4, operations ~ 5 * 10^5 (well under 1s).
    *    - Space Complexity: O(V^2 + E + Q) for adjacency lists, dependency sets, and the output.
    * ============================================================================
    */

    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] pre, int[][] queries) {

        // graph[u] stores all courses that directly depend on u (u -> v)
        List<Integer>[] graph = new ArrayList[numCourses];
        int[] inDegree = new int[numCourses];

        // listOfDep[v] stores all direct and indirect prerequisites of course v
        Set<Integer>[] listOfDep = new HashSet[numCourses];

        // Initialize adjacency lists and prerequisite sets
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
            listOfDep[i] = new HashSet<>();
        }

        // Build the directed graph and calculate in-degrees
        for (int[] edge : pre) {
            int u = edge[0];
            int v = edge[1];

            graph[u].add(v);
            inDegree[v]++;
        }

        // Propagate prerequisites downstream using Kahn's topological sort
        getDepViaTopoSort(graph, inDegree, listOfDep);

        // Answer each reachability query in O(1) average time
        List<Boolean> ans = new ArrayList<>(queries.length);

        for (int[] q : queries) {
            int u = q[0];
            int v = q[1];

            // Check if course u is in the prerequisite set of course v
            if (listOfDep[v].contains(u)) {
                ans.add(true);
            } else {
                ans.add(false);
            }
        }

        return ans;
    }

    /**
     * Executes Kahn's algorithm (indegree BFS) to traverse the DAG in topological order,
     * merging prerequisite sets downstream into dependent courses.
     */
    private void getDepViaTopoSort(List<Integer>[] graph, int[] inDegree, Set<Integer>[] listOfDep) {

        Queue<Integer> q = new LinkedList<>();

        // Enqueue all courses with no prerequisites (in-degree == 0)
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                q.offer(i);
            }
        }

        while (!q.isEmpty()) {
            int node = q.poll();
            Set<Integer> depList = listOfDep[node];

            for (int child : graph[node]) {
                inDegree[child]--;

                // Transitive closure: child inherits all prerequisites of node + node itself
                listOfDep[child].addAll(depList);
                listOfDep[child].add(node);

                // Enqueue child once all its direct prerequisites have been processed
                if (inDegree[child] == 0) {
                    q.add(child);
                }
            }
        }
    }
    
}
