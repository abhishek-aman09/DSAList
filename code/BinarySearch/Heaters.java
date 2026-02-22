package BinarySearch;

import java.util.Arrays;

public class Heaters {

    // https://leetcode.com/problems/heaters/description/
    /*
    Winter is coming! During the contest, 
    your first job is to design a standard heater with a fixed warm radius to warm all the houses.
    
    Every house can be warmed, as long as the house is within the heater's warm radius range. 
    
    Given the positions of houses and heaters on a horizontal line, 
    return the minimum radius standard of heaters so that those heaters could cover all houses.
    
    Notice that all the heaters follow your radius standard, 
    and the warm radius will be the same.
    
    Input: houses = [1,2,3,4], heaters = [1,4]
    Output: 1
    Explanation: The two heaters were placed at positions 1 and 4. 
    We need to use a radius 1 standard, then all the houses can be warmed.
    
    Approach : Perform binary search from 0 to farthest house/heater.
    For given radius check distance of nearest heater to left and right of 
    each house, if the radius is greater than equal to dist of heater on 
    either side, it is covered. If covered, decrease the radius, else increase it.
    
    */

    public int findRadius(int[] houses, int[] heaters) {

        Arrays.sort(houses);
        Arrays.sort(heaters);
        int n = houses.length;
        int m = heaters.length;
        int l = 0;
        int r = Integer.max(houses[n - 1], heaters[m - 1]);

        // Arrays to store the nearest heater for each house. 0 -> left, 1 -> right
        int distOfNearestHeaterFromHouses[][] = new int[n][2];

        for (int i = 0; i < n; i++) {
            distOfNearestHeaterFromHouses[i][0] = getNearestHeaterToLeft(houses[i], heaters);
            distOfNearestHeaterFromHouses[i][1] = getNearestHeaterToRight(houses[i], heaters);
        }

        int ans = Integer.MAX_VALUE;

        while (l <= r) {
            int radius = l + (r - l) / 2;

            boolean isCovered = true;

            // iterate through each house to check coverage
            for (int i = 0; i < n; i++) {
                // left dist of ith house is ind 0 and right is 1.
                int leftDist = distOfNearestHeaterFromHouses[i][0];
                int rightDist = distOfNearestHeaterFromHouses[i][1];

                // if dist of heaters on both sides is greater than radius, house is uncovered.
                if (radius < leftDist && radius < rightDist) {
                    isCovered = false;
                    break;
                }
            }

            if (isCovered) {
                ans = Integer.min(ans, radius);
                r = radius - 1;
            } else {
                l = radius + 1;
            }
        }
        
        return ans;

    }
    

    private int getNearestHeaterToLeft(int house, int heaters[]) {

        int l = 0;
        int r = heaters.length - 1;

        int nearestHeaterToLeft = Integer.MAX_VALUE;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (heaters[mid] > house) {
                r = mid - 1;
            } else {
                nearestHeaterToLeft = Integer.min(nearestHeaterToLeft, house - heaters[mid]);
                l = mid + 1;
            }
        }

        return nearestHeaterToLeft;
    }

    private int getNearestHeaterToRight(int house, int heaters[]) {

        int l = 0;
        int r = heaters.length - 1;

        int nearestHeaterToRight = Integer.MAX_VALUE;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (heaters[mid] >= house) {
                nearestHeaterToRight = Integer.min(nearestHeaterToRight, heaters[mid] - house);
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return nearestHeaterToRight;
    }

    public static void main(String[] args) {
        int houses[] = {1,5};
        int heaters[] = {10};

        Heaters obj = new Heaters();

        System.out.println(obj.findRadius(houses, heaters));
    }
    
}
