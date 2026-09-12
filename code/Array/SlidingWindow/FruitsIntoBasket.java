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

    Input: fruits = [1,2,3,2,2]
    Output: 4
    Explanation: We can pick from trees [2,3,2,2].
    If we had started at the first tree, we would only pick from trees [1,2].

    approach : you can pick maximum 2 types of fruit (can be extended to K types).
    For each index i, put it into the map, chech the size of the map.
    maintain a left pointer starting from zero, continue removing fruits from left till the map size becomes 2(k) again.

    current fruit you can take will be (i - left + 1), keep a max pointer


 */
public class FruitsIntoBasket {
    
    public int totalFruit(int[] fruits) {
        
        int n = fruits.length;

        // map to store freq of fruits
        Map<Integer, Integer> freq = new HashMap<>();

        int left = 0, ans = 0;

        for (int i = 0; i < n; i++) {
            freq.put(fruits[i], freq.getOrDefault(fruits[i], 0) + 1); // put the fruit into the map

            while (freq.size() > 2) { // bring back the size of map to 2 by removing fruits from left
                freq.put(fruits[left], freq.get(fruits[left]) - 1);
                if (freq.get(fruits[left]) == 0) {
                    freq.remove(fruits[left]);
                }
                left++;
            }

            ans = Integer.max(ans, i - left + 1); // store the max
        }

        return ans;


    }

}
