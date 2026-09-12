package BinarySearch;

import java.util.Arrays;

public class SuccessfulPairsAndSpells {
    // https://leetcode.com/problems/successful-pairs-of-spells-and-potions/?envType=problem-list-v2&envId=binary-search
    /*
    You are given two positive integer arrays spells and potions, of length n and m respectively, where spells[i] represents the strength of the ith spell and potions[j] represents the strength of the jth potion.
    
    You are also given an integer success. A spell and potion pair is considered successful if the product of their strengths is at least success.
    
    Return an integer array pairs of length n where pairs[i] is the number of potions that will form a successful pair with the ith spell.
    
    Input: spells = [5,1,3], potions = [1,2,3,4,5], success = 7
    Output: [4,0,3]
    Explanation:
    - 0th spell: 5 * [1,2,3,4,5] = [5,10,15,20,25]. 4 pairs are successful.
    - 1st spell: 1 * [1,2,3,4,5] = [1,2,3,4,5]. 0 pairs are successful.
    - 2nd spell: 3 * [1,2,3,4,5] = [3,6,9,12,15]. 3 pairs are successful.
    Thus, [4,0,3] is returned.
    
    */
    
    
    public int[] successfulPairs(int[] spells, int[] potions, long success) {

        int n = spells.length;
        int m = potions.length;

        int ans[] = new int[n];

        // EDGE CASE: If no spells are provided, return the empty answer array.
        if (n == 0) {
            return ans;
        }

        // 1. SORT POTIONS:
        // Sorting potions in ascending order allows us to use binary search.
        // Time complexity for sorting: O(m log m).
        Arrays.sort(potions);

        // 2. PROCESS EACH SPELL INDEPENDENTLY:
        for (int i = 0; i < n; i++) {

            int curr = spells[i];
            int left = 0, right = m - 1;

            // 'rightLim' stores the first index in potions[] where:
            // (curr * potions[index]) >= success.
            // Default initialized to 'm' (meaning no valid potion was found).
            int rightLim = m;

            // BINARY SEARCH (LOWER BOUND):
            // Search for the smallest valid potion that satisfies the condition.
            while (left <= right) {
                // Note: To avoid integer overflow for large indices, prefer:
                // int mid = left + (right - left) / 2;
                int mid = (left + right) / 2;

                // CAST TO LONG:
                // Cast before multiplying to avoid 32-bit signed integer overflow.
                long currProduct = (long) curr * (long) potions[mid];

                if (currProduct >= success) {
                    // Potential first valid index found at 'mid'.
                    // Record it and search leftwards to find an even smaller valid potion.
                    rightLim = mid;
                    right = mid - 1;
                } else {
                    // Product is strictly less than 'success'.
                    // All elements at or to the left of 'mid' are too small; search rightwards.
                    left = mid + 1;
                }
            }

            // 3. COUNT SUCCESSFUL PAIRS:
            // Since potions is sorted, every element from index 'rightLim' to 'm - 1'
            // will produce a product >= success.
            // Total valid potions count = m - rightLim.
            // (If rightLim remained m, this evaluates to m - m = 0).
            ans[i] = m - rightLim;
        }

        return ans;
    }

    public static void main(String[] args) {
        int spell[] = {3,1,2};
        int potion[] = {8,5,8};
        long success = 16L;

        SuccessfulPairsAndSpells obj = new SuccessfulPairsAndSpells();

        int ans[] = obj.successfulPairs(spell, potion, success);

        for (int x : ans) {
            System.out.print(x + "  ");
        }
        System.out.println();
    }
    
}
