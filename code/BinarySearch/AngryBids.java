package BinarySearch;

import java.util.Arrays;
import java.util.Scanner;

public class AngryBids {
    
    private int[] getAngryPeople(int p, int c, int producers[], int consumers[]) {

        int solution[] = new int[2];

        Arrays.sort(producers);
        Arrays.sort(consumers);

        // There will be 3 best case scenario.
        // 1. if there are no producers, we can set price to zero i.e min
        if (p == 0) {
            solution[0] = 0;
            solution[1] = 0;
            return solution;
        }

        // 2. If there are no consumers, we can set bid to max of producers price.
        if (c == 0) {
            solution[0] = producers[p - 1];
            solution[1] = 0;
            return solution;
        }

        // 3. If max price asked by producers is less than min price asked by consumer
        // then max price by producers will be the answer.
        if (producers[p - 1] <= consumers[0]) {
            solution[0] = producers[p - 1];
            solution[1] = 0;
            return solution;
        }

        // For all the other cases, the answer will lie between 1 and 10^8

        final int MAXLIMIT = 100000000;
        int left = 1, right = MAXLIMIT;

        int minAngryPeople = Integer.MAX_VALUE;
        int minPrice = Integer.MAX_VALUE;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int angryConsumers = getNumberOfAngryConsumersForCurrentPrice(mid, consumers);
            int angryProducers = getNumberOfAngryProducersForCurrentPrice(mid, producers);

            int curAngryPeople = angryConsumers + angryProducers;

            if (curAngryPeople < minAngryPeople ||(curAngryPeople == minAngryPeople && minPrice > mid)) {
                minPrice = mid;
                minAngryPeople = curAngryPeople;
            }

            // if we have more angry customers, try reducing the price.
            if (angryConsumers > angryProducers) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }

        }
        
        solution[0] = minPrice;
        solution[1] = minAngryPeople;

        return solution;
    }
    


    private int getNumberOfAngryConsumersForCurrentPrice(int price, int consumers[]) {
        int angryConsumers = 0;

        int left = 0, right = consumers.length - 1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (consumers[mid] < price) {
                angryConsumers = mid + 1;
                left = mid + 1;
            } else {
                right = mid - 1;
            }

        }

        return angryConsumers;
    }

    private int getNumberOfAngryProducersForCurrentPrice(int price, int producers[]) {
        int angryProducers = 0;

        int n = producers.length;

        int left = 0, right = producers.length - 1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (producers[mid] > price) {
                angryProducers = n - mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }

        }

        return angryProducers;
    }

    public static void main(String[] args) {
        AngryBids obj = new AngryBids();

        int tc, p, c;

        Scanner sc = new Scanner(System.in);

        tc = sc.nextInt();

        while (tc-- > 0) {
            p = sc.nextInt();
            c = sc.nextInt();

            int producers[] = new int[p];
            int consumers[] = new int[c];

            for (int i = 0; i < p; i++) {
                producers[i] = sc.nextInt();
            }

            for (int i = 0; i < c; i++) {
                consumers[i] = sc.nextInt();
            }

            int ans[] = obj.getAngryPeople(p, c, producers, consumers);

            System.out.println(ans[0] + "    " + ans[1]);

        }
        
        sc.close();

    }

}
