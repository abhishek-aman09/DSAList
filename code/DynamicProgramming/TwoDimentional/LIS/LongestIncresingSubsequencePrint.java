package DynamicProgramming.TwoDimentional.LIS;

import java.util.ArrayList;

import Pair.Pair;

public class LongestIncresingSubsequencePrint {

    /*
    https://leetcode.com/problems/longest-increasing-subsequence/description/

    
    */

    public ArrayList<Integer> getLIS(int arr[]) {
        
        int n = arr.length;

        int LISEndingAtCurr[] = new int[n];
        int maxLISEndIndex = -1;
        int maxLISSize = 0;
        int prevIndexOfCurrElInLIS[] = new int[n];

        for (int i = 0; i < n; i++) {
            LISEndingAtCurr[i] = 1;
            prevIndexOfCurrElInLIS[i] = i;

            for (int j = 0; j < i; j++) {

                if ((arr[i] > arr[j]) && ((LISEndingAtCurr[j] + 1) > LISEndingAtCurr[i])) {
                    LISEndingAtCurr[i] = LISEndingAtCurr[j] + 1;
                    prevIndexOfCurrElInLIS[i] = j;
                }
            }

            if (LISEndingAtCurr[i] > maxLISSize) {
                maxLISEndIndex = i;
                maxLISSize = LISEndingAtCurr[i];
            }

        }

        ArrayList<Integer> lis = new ArrayList<>();

        while (prevIndexOfCurrElInLIS[maxLISEndIndex] != maxLISEndIndex) {
            lis.add(arr[maxLISEndIndex]);
            maxLISEndIndex = prevIndexOfCurrElInLIS[maxLISEndIndex];
        }

        lis.add(arr[maxLISEndIndex]);

        return (ArrayList<Integer>)lis.reversed();


        
    }

    
}
 