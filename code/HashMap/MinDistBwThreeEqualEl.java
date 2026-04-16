package HashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinDistBwThreeEqualEl {

    /*
    https://leetcode.com/problems/minimum-distance-between-three-equal-elements-ii
    
    You are given an integer array nums.
    
    A tuple (i, j, k) of 3 distinct indices is good if nums[i] == nums[j] == nums[k].\   
    The distance of a good tuple is abs(i - j) + abs(j - k) + abs(k - i), 
    where abs(x) denotes the absolute value of x.
    Return an integer denoting the minimum possible distance of a good tuple. If no good tuples exist, return -1.
    
    Input: nums = [1,1,2,3,2,1,2]    
    Output: 8
    
    Explanation: 
    The minimum distance is achieved by the good tuple (2, 4, 6).    
    (2, 4, 6) is a good tuple because nums[2] == nums[4] == nums[6] == 2. 
    Its distance is abs(2 - 4) + abs(4 - 6) + abs(6 - 2) = 2 + 2 + 4 = 8.
    
    Approach : Map a map to store the dist bw adjacent items and calculate the distace
    by traversing the value set
    
    */

    public int minimumDistance(int[] nums) {

        int n = nums.length;

        int lastPos[] = new int[n + 1];
        Map<Integer, List<Integer>> elWithDiff = new HashMap<>();

        Arrays.fill(lastPos, -1);

        for(int i = 0; i < n; i++) {
            int el = nums[i];

            if(lastPos[el] == -1) {
                lastPos[el] = i;
            } else {

                // System.out.println( el + "  " + i + "  " + lastPos[el]);
                int diff = i - lastPos[el];
                lastPos[el] = i;
                List<Integer> list = elWithDiff.getOrDefault(el, new ArrayList<>());
                list.add(diff);

                elWithDiff.put(el, list);
            }
        }


        int ans = Integer.MAX_VALUE;

        for(List<Integer> list : elWithDiff.values()) {
            if(list.size() < 2) {
                continue;
            }

            // System.out.println(list);

            for(int i = 0; i < list.size() - 1; i++) {
                int l = list.get(i);
                int r = list.get(i + 1);

                int curr = 2 * (l + r);

                if(ans > curr) {
                    ans = curr;
                }
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
        
    }
    
}
