package Array;

public class NextPermutation {
    /*
     * https://www.geeksforgeeks.org/dsa/next-permutation/
    
    Given an array of integers arr[] representing a permutation, implement the next permutation that rearranges the numbers into the lexicographically smallest greater (or next) permutation.
    
    If no next permutation exists, rearrange the numbers into the lowest possible order (i.e., sorted in ascending order).
    
    Input: arr[] = [2, 4, 1, 7, 5, 0]
    Output: [2, 4, 5, 0, 1, 7]
    Explanation: The next permutation of the given array is [2, 4, 5, 0, 1, 7].
    
    approach : from the right, find the first index i, which is less than i + 1, if there is no such element i.e array sorted in decreasing order, reverse the array for answer
    in above example that element would be 1. Next, from the right, find the first element greater than the previously found index. in this case, that would be 5.
    swap 1 and 5. from new index to 5 till last index, swap the elements.
    
     */
    
    void nextPermutation(int[] arr) {

        int n = arr.length;

        int firstElSmallerThanItsRight = -1;

        int i = n - 2;

        // The first number to be moved is the rightmost number smaller than its next.
        while (i >= 0) {
            if (arr[i] < arr[i + 1]) {
                firstElSmallerThanItsRight = i;
                break;
            }
            i--;
        }

        if (firstElSmallerThanItsRight == -1) {
            reverese(arr, 0, n - 1);
            return;
        }

        i = n - 1;

        // The number to come in-place is the first number from right, greater than pivot(firstElSmallerThanItsRight).
        while (i > firstElSmallerThanItsRight) {
            if (arr[i] > arr[firstElSmallerThanItsRight]) {
                // if found, swap them and sort the right side
                swap(arr, firstElSmallerThanItsRight, i);
                break;
            }
            i--;
        }
        // from firstElSmallerThanItsRight + 1 till n - 1, swap the elements
        reverese(arr, firstElSmallerThanItsRight + 1, n - 1);

    }
    
    private void reverese(int arr[], int left, int right) {
        while (left < right) {
            swap(arr, left, right);
            left++;
            right--;
        }
    }

    private void swap(int arr[], int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
    
}
