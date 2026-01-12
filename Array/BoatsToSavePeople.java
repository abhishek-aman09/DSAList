package Array;

import java.util.Arrays;

public class BoatsToSavePeople {

    // https://leetcode.com/problems/boats-to-save-people/description/

    /*
    You are given an array people where people[i] is the weight of the ith person, and an infinite number of boats where each boat can carry a maximum weight of limit. Each boat carries at most two people at the same time, provided the sum of the weight of those people is at most limit.

    Return the minimum number of boats to carry every given person.
    */

    public int numRescueBoats(int[] people, int limit) {

        int n = people.length;

        int numOfBoats = 0;

        Arrays.sort(people);

        int left = 0, right = n - 1;

        while (left <= right) {
            if (left == right || people[left] + people[right] <= limit) {
                left++;
                right--;
            } else {
                right--;
            }

            numOfBoats++;
        }

        return numOfBoats;

    }
    
    /*
    Variation of problem : remove the condition of maximum two people at a time
    */
    
    public int numRescueBoatsWithMoreThanTwoPeopleAtATIme(int[] people, int limit) {

        int n = people.length;

        int numOfBoats = 0;

        Arrays.sort(people);

        int left = 0, right = n - 1;

        while (left <= right) {

            if (left == right || people[left] + people[right] == limit) { // if there is one element or left of right point to same index anytime of left + right = limit
                left++;
                right--;
            } else if (people[left] + people[right] < limit) { // if left + right < limit we will keep adding people from left till limit reaches
                int sum = people[left] + people[right];
                left++;
                while (left < right && sum + people[left] <= limit) {
                    sum += people[left];
                    left++;
                }
                right--;
            } else { // if sum is greater than limit, right cannot be accomodated with anyone from left, send it.
                right--;
            }

            numOfBoats++;
        }

        return numOfBoats;
    }
    
    public static void main(String[] args) {
        BoatsToSavePeople obj = new BoatsToSavePeople();

        int people[] = {2,49,10,7,11,41,47,2,22,6,13,12,33,18,10,26,2,6,50,10};

        System.out.println(obj.numRescueBoats(people, 50));
    }

}
