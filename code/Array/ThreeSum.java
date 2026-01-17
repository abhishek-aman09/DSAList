package Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThreeSum {
    
    public List<List<Integer>> threeSum(int[] nums) {

        Arrays.sort(nums);

        int n = nums.length;

        List<List<Integer>> ans = new ArrayList<>();


        for (int i = 0; i < n;) {

            int left = i + 1;
            int right = n - 1;

            while (left < right) {

                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[left]);
                    list.add(nums[right]);

                    ans.add(list);
                    // conditon to tackle repition inside nested loop
                    int currL = nums[left];
                    while (left < right && currL == nums[left]) {
                        left++;
                    }
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }

            // condition to tackle repition outside nested loop
            int currI = nums[i];
            while (i < n && currI == nums[i]) {
                i++;
            }
        }

        return ans;
    }
    
    public static void main(String[] args) {
        ThreeSum obj = new ThreeSum();

        int arr[] = {0,0,0,0};

        List<List<Integer>> ans = obj.threeSum(arr);

        for (List<Integer> list : ans) {
            for (Integer el : list) {
                System.out.print(el + "  ");
            }
            System.out.println();
        }
    }
    
}
