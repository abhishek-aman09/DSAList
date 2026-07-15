package Google.BinarySearch;

public class FindSmallestInSortedRotated {

    private static final int NOT_FOUND = Integer.MAX_VALUE / 100;
    
    public int findMin(int[] nums) {

        int l = 0;
        int  r = nums.length - 1;

        int minEl = findPivotRecursively(l, r, nums);

        if (minEl == NOT_FOUND) {
            return nums[0];
        }

        return minEl;

    }

    private int findPivotRecursively(int l, int r, int[] nums) {

        if(l >= r) {
            return NOT_FOUND;
        }

        int mid = l + (r - l) / 2;

        if (nums[mid] == nums[r]) {
            int left = findPivotRecursively(l, mid, nums);
            int right = findPivotRecursively(mid + 1, r, nums);

            if (left != NOT_FOUND) {
                return left;
            }
            return right;
        } else {
            return findPivot(l, r, nums);
        }
    }
    

    private int findPivot(int l, int r, int[] nums) {

        int minEl = NOT_FOUND;

        while (l <= r) {

            int mid = l + (r - l) / 2;

            if (nums[mid] >= nums[r]) {
                l = mid + 1;
            } else {
                minEl = Integer.min(minEl, nums[mid]);
                r = mid - 1;
            }
        }

        return minEl;

    }
    
    public static void main(String[] args) {
        FindSmallestInSortedRotated obj = new FindSmallestInSortedRotated();

        int nums[] = new int[] { 1, 2, 0, 0, 1 };

        System.out.println(obj.findMin(nums));
    }
}
