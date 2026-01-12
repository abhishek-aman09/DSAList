package String;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartitionLabels {
    /* https://leetcode.com/problems/partition-labels/description/
    
    You are given a string s. We want to partition the string into as many parts as possible so that each letter appears in at most one part. For example, the string "ababcc" can be partitioned into ["abab", "cc"], but partitions such as ["aba", "bcc"] or ["ab", "ab", "cc"] are invalid.
    
    Note that the partition is done so that after concatenating all the parts in order, the resultant string should be s.
    
    Input: s = "ababcbacadefegdehijhklij"
    Output: [9,7,8]
    Explanation:
    The partition is "ababcbaca", "defegde", "hijhklij".
    This is a partition so that each letter appears in at most one part.
    A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits s into less parts.
    
    Approach - 
    1. Make a map contains first and last pos of each char
    2. Create a 2D array of size numOfChars*2.
    3. Insert first and last pos of each char into array in order
    4. Perform merge intervals concept
    */
   
    public List<Integer> partitionLabels(String s) {

        int n = s.length();

        List<Integer> ans = new ArrayList<>();

        Map<Character, Pair> mapWithFirstAndLastPos = new HashMap<>();
        // block to insert first and last pos of each char
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);

            if (mapWithFirstAndLastPos.containsKey(ch)) {
                Pair temp = mapWithFirstAndLastPos.get(ch);
                temp.last = i;
                mapWithFirstAndLastPos.put(ch, temp);
            } else {
                mapWithFirstAndLastPos.put(ch, new Pair(i, i));
            }
        }

        int numOfUniqueEl = mapWithFirstAndLastPos.size();
        int nums[][] = new int[numOfUniqueEl][2];
        int j = 0;
        // block to insert first and last pos of each char into array in order
        for (int i = 0; i < n && j < numOfUniqueEl; i++) {
            char ch = s.charAt(i);

            if (mapWithFirstAndLastPos.containsKey(ch)) {
                Pair pair = mapWithFirstAndLastPos.get(ch);
                nums[j][0] = pair.first + 1;
                nums[j][1] = pair.last + 1;
                j++;
                mapWithFirstAndLastPos.remove(ch);
            }
        }

        // block to perform merge intervals concept
        for (int i = 0; i < numOfUniqueEl;) {
            int currEnd = nums[i][1];
            j = i + 1;

            while (j < numOfUniqueEl && nums[j][0] < currEnd) {
                currEnd = Math.max(nums[j][1], currEnd);
                j++;
            }
            ans.add(currEnd - nums[i][0] + 1);
            i = j;
        }

        return ans;

    }
    
    private static class Pair {

        int first;
        int last;

        Pair(int first, int last) {
            this.first = last;
            this.last = last;
        }

    }
    

    public static void main(String[] args) {
        String s = "ababcbacadefegdehijhklij";

        PartitionLabels obj = new PartitionLabels();

        obj.partitionLabels(s).stream().forEach(a -> System.out.print(a + " "));
    }
}
