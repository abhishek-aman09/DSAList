package RecursionAndBacktracking;

//quick sort using lomuto partioning
public class QuickSort {

    public static int partition(int[] arr, int l, int r) {
        if (l >= r)
            return -1;

        int pivot = arr[r];
        int i = l, j = l - 1;

        while (i <= r) {
            if (arr[i] < pivot) {
                j++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
            i++;
        }
        j++;

        arr[r] = arr[j];
        arr[j] = pivot;

        return j;
    }
    
    public static void sort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        int pos = partition(arr, l, r);

        sort(arr, l, pos - 1);
        sort(arr, pos + 1, r);
    }

    public static void main(String[] args) {
        int[] arr = { 2, 5, 2, 1, 5, 6, 3, 1, 43, 56, 1, 2 };
        int s = arr.length;

        sort(arr, 0, s - 1);

        for (int el : arr)
            System.out.print(el + "  ");
        System.out.println();
    }
    
}
