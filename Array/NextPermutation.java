package Array;

public class NextPermutation {
    /*
     * https://www.geeksforgeeks.org/dsa/next-permutation/
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
