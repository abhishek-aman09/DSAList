package BitManipulation;

import java.util.ArrayList;
import java.util.Arrays;

import Graph.isPathPossible;

public class CanSortArray {
    public boolean canSortArray(int[] nums) {

        int n = nums.length;
        if (n == 0 || n == 1) {
            return true;
        }

        boolean isSorted = true;

        for (int i = 0; i < n - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                isSorted = false;
                break;
            }
        }

        if (isSorted) {
            return true;
        }

       

        return true;

    }

    private int getSetBits(int el) {
        int count = 0;
        while (el > 0) {
            count++;
            el = el & (el - 1);
        }

        return count;
    }

    public static void main(String[] args) {
        int arr[] = {75,34,30};

        CanSortArray obj = new CanSortArray();

        System.out.println(obj.canSortArray(arr));
        
    }
}
