package RecursionAndBacktracking;

public class MergeSort {

    public static void merge(int[] arr, int l, int mid, int r) {

        int lSize = mid - l + 1;
        int rSize = r - mid;

        int[] left = new int[lSize];
        int[] right = new int[rSize];


        for (int i = 0; i < lSize; i++) {
            left[i] = arr[i + l];
        }
            
        for (int i = 0; i < rSize; i++) {
            right[i] = arr[i + mid + 1];
        }

        int leftIndex = 0, rightIndex = 0;

        int i = l;
        while (leftIndex < lSize && rightIndex < rSize) {
            if (left[leftIndex] <= right[rightIndex]) {
                arr[i++] = left[leftIndex++];
            } else {
                arr[i++] = right[rightIndex++];
            }
        }

        while (leftIndex < lSize) {
                arr[i++] = left[leftIndex++];
        }

        while (rightIndex < rSize) {
                arr[i++] = right[rightIndex++];
        }

    }

    public static void sort(int[] arr, int l, int r) {
        if (l >= r)
            return;

        int mid = l + (r - l) / 2;

        sort(arr, l, mid);
        sort(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }
    
    public static void main(String[] args) {
        int[] arr = { 2, 5, 2, 1, 5, 6, 3, 1, -43, 56, 1, 2 };
        int s = arr.length;

        sort(arr, 0, s - 1);

        for (int el : arr)
            System.out.print(el + "  ");
        System.out.println();
    }
}
