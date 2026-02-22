package BinarySearch;

import java.util.Arrays;

public class MinCapacityToShipPackage {
    
    public int shipWithinDays(int[] weights, int days) {

        Arrays.sort(weights);

        int n = weights.length;

        int l = 1, r = n * weights[n - 1];
        int minCap = Integer.MAX_VALUE;

        while(l <= r) {
            int cap = l + (r - l) / 2;

            boolean isPossible = isPossibleWithCapacity(weights, days, cap);
            if(isPossible) {
                minCap = Integer.min(minCap, cap);
                r = cap - 1;
            } else {
                l = cap + 1;
            }
             
        }

        return minCap;
        
    }

    private boolean isPossibleWithCapacity(int weights[], int days, int capacity) {

        
        int currDays = 0;
        int sum = 0;
        for(int i = 0; i < weights.length;) {
            if(weights[i] > capacity) {
                return false;
            }
            
            if (sum + weights[i] <= capacity) {
                sum += weights[i];
                i++;
            } else {
                sum = 0;
                currDays++;
            }  
        }

        return currDays + 1 <= days;
    }
    

    public static void main(String[] args) {
        MinCapacityToShipPackage obj = new MinCapacityToShipPackage();

        int weights[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        
        System.out.println(obj.shipWithinDays(weights, 5));
    }
}
