package Array;

public class RemoveDuplicateFromSortedArray {
    
    /* https://leetcode.com/problems/remove-duplicates-from-sorted-array/description/
    */
    public int removeDuplicates(int[] nums) {

        int n = nums.length;

        if (n == 1) {
            return 0;
        }

        int l = 0, r = 1, ans = 0;

        while (r < n) {
            while (r < n && nums[r] == nums[l]) {
                r++;
                ans++;
            }

            if(r >= n) {
                break;
            }

            nums[l + 1] = nums[r];
            r++;
            l++;
        }
        return ans;
    }
    

    public static void main(String[] args) {
        RemoveDuplicateFromSortedArray obj = new RemoveDuplicateFromSortedArray();

        int arr[] = { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 };

        System.out.println(obj.removeDuplicates(arr));
    }
}
