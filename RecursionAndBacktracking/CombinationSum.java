package RecursionAndBacktracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CombinationSum {

    /*
     * Input: candidates = [10,1,2,7,6,1,5], target = 8
        Output: 
        [
        [1,1,6],
        [1,2,5],
        [1,7],
        [2,6]
        ]
     */
    // Combination Sum 2

    public static void solve(int[] candidates, int target, int i, int sum, int n, List<Integer> curr, HashSet<List<Integer>> ans) {
        if (sum == target) {
            List<Integer> temp = new ArrayList<>();

            for (int el : curr) {
                temp.add(el);
            }
            
            temp.sort((a, b) -> {
                return a < b ? -1 : 1;});

            ans.add(temp);
            return;
        }

        if (i >= n || sum > target)
            return;

        
        sum += candidates[i];
        curr.add(candidates[i]);

        solve(candidates, target, i + 1, sum, n, curr, ans);

        sum -= candidates[i];
        curr.remove(curr.size() - 1);

        solve(candidates, target, i + 1, sum, n, curr, ans);
    }

    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        HashSet<List<Integer>> uniqueList = new HashSet<>();

        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> curr = new ArrayList<>();

        solve(candidates, target, 0, 0, candidates.length, curr, uniqueList);

        for (List<Integer> list : uniqueList)
            ans.add(list);

        return ans;
    }

    /**
     * Example 2:

       Input: candidates = [2,3,5], target = 8
       Output: [[2,2,2,2],[2,3,3],[3,5]]
     */

    // Combination Sum 
    
    public static void solve(int[] candidates, int target, int i, int sum, int n, List<Integer> curr, List<List<Integer>> ans) {
        if (sum == target) {
            List<Integer> temp = new ArrayList<>();

            for (int el : curr) {
             temp.add(el);
            }

            ans.add(temp);
            return;
        }

        if (i >= n || sum > target)
            return;

        
        sum += candidates[i];
        curr.add(candidates[i]);

        solve(candidates, target, i, sum, n, curr, ans);

        sum -= candidates[i];
        curr.remove(curr.size() - 1);

        solve(candidates, target, i + 1, sum, n, curr, ans);
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> curr = new ArrayList<>();

        solve(candidates, target, 0, 0, candidates.length, curr, ans);

        return ans;
    }
    
    public static void main(String[] args) {
        List<List<Integer>> l = new ArrayList<>();

        int[] can = {2, 3, 5};

        l = combinationSum(can, 8);

        for(List<Integer> el: l) {
            for (Integer x : el) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }
}
