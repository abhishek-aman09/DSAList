import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EasyProbs {
    

    // two sum
    // **For the general Two Sum problem (unsorted array, any integers):

    // 👉 O(n) time and O(1) space is impossible.**

    // The best you can do is:

    // Approach	                Time	    Space
    // Hash map	                O(n)	    O(n)
    // Sorting + two pointer	O(n log n)	O(1)

    public int[] twoSum(int[] nums, int target) {
        
        int ans[] = new int[2];
        
        Map<Integer, Integer> elWithPos = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (elWithPos.containsKey(target - nums[i])) {
                ans[0] = elWithPos.get(target - nums[i]);
                ans[1] = i;
                break;
            }
            elWithPos.put(nums[i], i);
        }
        
        return ans;
    }
    public static void main(String[] args) {
        EasyProbs obj = new EasyProbs();

    }
}
