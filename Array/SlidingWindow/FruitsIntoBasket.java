package Array.SlidingWindow;

import java.util.HashMap;
import java.util.Map;

/*
 * https://leetcode.com/problems/fruit-into-baskets/description/
 * You are visiting a farm that has a single row of fruit trees arranged from left to right. The trees are represented by an integer array fruits where fruits[i] is the type of fruit the ith tree produces.

    You want to collect as much fruit as possible. However, the owner has some strict rules that you must follow:

    You only have two baskets, and each basket can only hold a single type of fruit. There is no limit on the amount of fruit each basket can hold.
    Starting from any tree of your choice, you must pick exactly one fruit from every tree (including the start tree) while moving to the right. The picked fruits must fit in one of your baskets.
    Once you reach a tree with fruit that cannot fit in your baskets, you must stop.
    Given the integer array fruits, return the maximum number of fruits you can pick.

 */
public class FruitsIntoBasket {
    
    public int totalFruit(int[] fruits) {
        
        int n = fruits.length;

        Map<Integer, Integer> freq = new HashMap<>();

        int left = 0, ans = 0;

        for (int i = 0; i < n; i++) {
            freq.put(fruits[i], freq.getOrDefault(fruits[i], 0) + 1);

            while (freq.size() > 2) {
                freq.put(fruits[left], freq.get(fruits[left]) - 1);
                if (freq.get(fruits[left]) == 0) {
                    freq.remove(fruits[left]);
                }
                left++;
            }

            ans = Integer.max(ans, i - left + 1);
        }

        return ans;


    }

}
