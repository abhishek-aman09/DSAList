package BinarySearch;

import java.util.Arrays;

public class SuccessfulPairsAndSpells {
    // https://leetcode.com/problems/successful-pairs-of-spells-and-potions/?envType=problem-list-v2&envId=binary-search
    
    
    public int[] successfulPairs(int[] spells, int[] potions, long success) {

        int n = spells.length;
        int m = potions.length;

        int ans[] = new int[n];

        if (n == 0) {
            return ans;
        }

        Arrays.sort(potions);

        for (int i = 0; i < n; i++) {

            int curr = spells[i];
            int left = 0, right = m - 1;
            int rightLim = m;

            while (left <= right) {
                int mid = (left + right) / 2;

                long currProduct = (long)curr * (long)potions[mid];

                if (currProduct >= success) {
                    right = mid - 1;
                    rightLim = mid;
                } else {
                    left = mid + 1;
                }
            }

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
