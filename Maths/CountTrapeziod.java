package Maths;

import java.util.HashMap;
import java.util.Map;

public class CountTrapeziod {

    /*
     * Number of lines that can be formed using X points are
     *   X!
     * _______
     * 2! * (X - 2)!
     */

    public int countTrapezoids(int[][] points) {

        int n = points.length;

        int ans = 0;
        int MOD = 1000000007;

        Map<Integer, Integer> freq = new HashMap<>();

        for (int arr[] : points) {
            int y = arr[1];
            freq.put(y, freq.getOrDefault(y, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> pair : freq.entrySet()) {
            int y = pair.getKey();
            int val = pair.getValue();

            if (val < 2) {
                freq
            }

            val = ((val % MOD * (val - 1) % MOD) / 2) % MOD;
            pair.setValue(val);
        }

    }
    
}
