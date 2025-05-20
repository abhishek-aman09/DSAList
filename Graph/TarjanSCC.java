package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Logic is same as Articulation point
 * 
 * If inTime of a node is equal to lowestAnsector time post traversing its
 * subtree, then there exist no back edge from any of its child node to any
 * of its ansectoe, hence the subtree with current node forms a strongly 
 * connected component
 * 
 * isActiveInCurrentSubtree keeps a track of all the child nodes that 
 * has a back edge, either to current node or its ansector.
 * 
 * stk stores the dfs call stack. If we find a SCC. We pop the entire subtree
 * of the current node.
 */

public class TarjanSCC {
    
    public int kosaraju(ArrayList<ArrayList<Integer>> adj) {
        IntegerWrapper count = new IntegerWrapper(0);
        IntegerWrapper timer = new IntegerWrapper(0);
        int V = adj.size();

        int[] inTime = new int[V];
        int[] lowestAnsector = new int[V];
        boolean[] isActiveInCurrentSubtree = new boolean[V];

        Arrays.fill(inTime, -1);
        Arrays.fill(lowestAnsector, -1);
        Arrays.fill(isActiveInCurrentSubtree, false);

        Stack<Integer> stk = new Stack<>();

        for (int i = 0; i < V; i++) {
            if (inTime[i] == -1) {
                dfs(adj, i, inTime, lowestAnsector, isActiveInCurrentSubtree, stk, timer, count);
            }
        }

        return count.getCount();
    }
    
    private void dfs(ArrayList<ArrayList<Integer>> adj, int curr, int[] inTime, int[] lowestAnsector, boolean[] isInActiveStack,
            Stack<Integer> stk, IntegerWrapper timer, IntegerWrapper count) {

        inTime[curr] = lowestAnsector[curr] = timer.getCount();
        timer.increaseCount();

        isInActiveStack[curr] = true;
        stk.push(curr);

        for (int child : adj.get(curr)) {
            if (inTime[child] == -1) {
                dfs(adj, child, inTime, lowestAnsector, isInActiveStack, stk,timer, count);

                lowestAnsector[curr] = Math.min(lowestAnsector[curr], lowestAnsector[child]);
            } else if (isInActiveStack[child] == true) {

                lowestAnsector[curr] = Math.min(lowestAnsector[curr], inTime[child]);
            }
        }

        if (lowestAnsector[curr] == inTime[curr]) {
            count.increaseCount();
            while (true) {
                int top = stk.pop();
                isInActiveStack[top] = false;
                if (top == curr)
                    break;
            }
        }
    }
    
    private static class IntegerWrapper {
        private int x;

        public IntegerWrapper(int x) {
            this.x = x;
        }

        int getCount() {
            return this.x;
        }

        void increaseCount() {
            this.x++;
        }
    }
}
