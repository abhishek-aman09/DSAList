package BinarySearch;

public class CheckSorted {

    public boolean check(int[] nums) {

        int n = nums.length;

        int l = 0, r = n - 1;
        
        int pivot = -1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (nums[mid] <= nums[n - 1]) {
                if (pivot == -1 || (nums[pivot] > nums[mid])) {
                    pivot = mid;
                }
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }

        if (pivot == -1) {
            return checkSorted(nums, 0, n - 1);
        }

        if (nums[pivot] < nums[0] && nums[0] < nums[n - 1]) {
            return false;
        }
        
        return checkSorted(nums, 0, pivot - 1) && checkSorted(nums, pivot, n - 1);
        
    }

    private boolean checkSorted(int nums[], int l, int r) {
        if (l >= r) {
            return true;
        }

        int mid = l + (r - l) / 2;

        if ((nums[mid] >= nums[l]) && (nums[mid] <= nums[r])) {
            return checkSorted(nums, l, mid) && checkSorted(nums, mid + 1, r);
        }

        return false;

    }
    

    public static void main(String[] args) {
        
        CheckSorted obj = new CheckSorted();

        System.out.println(obj.check(new int[]{10,1,1,10}));
    }
    
}
