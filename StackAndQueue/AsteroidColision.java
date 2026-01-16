package StackAndQueue;

import java.util.Arrays;
import java.util.Stack;

public class AsteroidColision {

    // https://leetcode.com/problems/asteroid-collision/description/
    /*
    We are given an array asteroids of integers representing asteroids in a row.
    The indices of the asteroid in the array represent their relative position in space.
    
    For each asteroid, the absolute value represents its size, 
    and the sign represents its direction (positive meaning right, 
    negative meaning left). Each asteroid moves at the same speed.
    
    Find out the state of the asteroids after all collisions. 
    If two asteroids meet, the smaller one will explode. 
    If both are the same size, both will explode. 
    Two asteroids moving in the same direction will never meet.
    
    Input: asteroids = [5,10,-5]
    Output: [5,10]
    Explanation: The 10 and -5 collide resulting in 10. The 5 and 10 never collide.
    
    Approach : Simple stack based, keep pushing +ve rocks and use stack
    method for negative ones
    
    
    */

    public int[] asteroidCollision(int[] asteroids) {

        int n = asteroids.length;

        Stack<Integer> stk = new Stack<>();
        int ans[] = new int[n];
        int k = 0;

        for (int i = 0; i < n; i++) {
            int curr = asteroids[i];

            if (curr > 0) {
                stk.push(curr);
                continue;
            }

            curr = curr * -1;

            while (!stk.isEmpty() && curr >= stk.peek()) {
                int top = stk.pop();
                if (top == curr) {
                    curr = 0;
                }
            }

            if (stk.isEmpty() && curr != 0) {
                ans[k] = curr * -1;
                k++;
            }
        }

        int res[] = new int[stk.size() + k];

        for (int i = 0; i < k; i++) {
            res[i] = ans[i];
        }

        k = res.length - 1;

        while (!stk.isEmpty()) {
            res[k] = stk.pop();
            k--;
        }

        return res;
    }

    public static void main(String[] args) {
        AsteroidColision obj = new AsteroidColision();

        int arr[] = { 5, 10, -5 };

        Arrays.stream(obj.asteroidCollision(arr)).forEach(el -> System.out.print(el + " "));
    }
    

    
}
