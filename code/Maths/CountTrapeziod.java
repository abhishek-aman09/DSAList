package Maths;

import java.util.HashMap;
import java.util.Map;

public class CountTrapeziod {

    /*
     * Number of lines that can be formed using X points are
     *   X!
     * _______
     * 2! * (X - 2)!
     * 
     * which is equal to (x * (x - 1)) / 2
     */

     public int countTrapezoids(int[][] points) {

        long ans = 0l;
        final long MOD = 1000000007l;
        long runningPairsOflines = 0l;

        Map<Integer, Integer> freq = new HashMap<>();

        for (int[] arr : points) {
            int y = arr[1];
            freq.put(y, freq.getOrDefault(y, 0) + 1);
        }

        for (Integer val : freq.values()) {
            if (val >= 2) {
                long currPairOfLines = (long) val * (val - 1) / 2;

                ans = (ans + ((currPairOfLines % MOD) * (runningPairsOflines % MOD))) % MOD;

                runningPairsOflines = (runningPairsOflines + currPairOfLines) % MOD;
            }
        }

        return (int)ans;

    }
}
