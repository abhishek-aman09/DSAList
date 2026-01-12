package BinarySearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindKClosestElement {

    // https://leetcode.com/problems/find-k-closest-elements/description/

    /*
    Given a sorted integer array arr, two integers k and x, return the k closest integers to x in the array. The result should also be sorted in ascending order.
    
    An integer a is closer to x than an integer b if:
    
    |a - x| < |b - x|, or
    |a - x| == |b - x| and a < b
    
        Approach : 
        get index of nearest element to x using binary search.
        make a window of size k from ind - k or 0.
        continue checking till r < n - 1 and diff(r + 1 - x) >= diff(l - x)
    */


    public List<Integer> findClosestElements(int[] arr, int k, int x) {

        int n = arr.length;

        int nearestElToX = getClosestNumberToNum(arr, x);

        int l = 0, r = 0;

        if (k - nearestElToX > 0) {
            l = 0;
            r = k - 1;
        } else {
            l = nearestElToX - k + 1;
            r = nearestElToX;
        }

        while (r < n - 1) {
            if (Math.abs(arr[r + 1] - x) >= Math.abs(arr[l] - x)) {
                break;
            }
            r++;
            l++;
        }

        List<Integer> ans = new ArrayList<>();

        for (int i = l; i <= r; i++) {
            ans.add(arr[i]);
        }

        return ans;

    }
    
    private int getClosestNumberToNum(int arr[], int num) {

        int n = arr.length;

        int l = 0, r = n - 1;

        int nearestNumIndex = n - 1;
        int minimumDiff = Integer.MAX_VALUE;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (Math.abs(num - arr[mid]) <= minimumDiff) {
                if (Math.abs(num - arr[mid]) == minimumDiff) {
                    nearestNumIndex = Integer.min(nearestNumIndex, mid);
                } else {
                    nearestNumIndex = mid;
                }
                minimumDiff = Math.abs(num - arr[mid]);
            }

            if (arr[mid] > num) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        return nearestNumIndex;
    }

    public static void main(String[] args) {
        FindKClosestElement obj = new FindKClosestElement();

        int arr[] = { 1, 1, 2, 3, 4, 5 };

        obj.findClosestElements(arr, 4, -1).stream().forEach(el -> System.out.print(el + " "));
    }
    
}
