package Google.String;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimilarStringGroup {

    /*
    https://leetcode.com/problems/similar-string-groups/description/
    
    Two strings, X and Y, are considered similar if either they are identical or we can make them equivalent 
    by swapping at most two letters (in distinct positions) within the string X.
    
    For example, "tars" and "rats" are similar (swapping at positions 0 and 2), and "rats" and "arts" are similar, 
    but "star" is not similar to "tars", "rats", or "arts".
    
    Together, these form two connected groups by similarity: {"tars", "rats", "arts"} and {"star"}.  
    Notice that "tars" and "arts" are in the same group even though they are not similar.  
    Formally, each group is such that a word is in the group if and only if it is similar to at least one other word in the group.
    
    We are given a list strs of strings where every string in strs is an anagram of every other string in strs. 
    How many groups are there?
    
    Example 1:
    
    Input: strs = ["tars","rats","arts","star"]
    Output: 2
    
    Approach : Standard DSU
    
    make a parent and rank array for all the strings. for each pair in the array we check how many places they differ,
    if the difference is 0 or 2, they are identical/can be transformed, in that case, we merge them

    at the end we can return total unique parents in out parents array.
    
    
    
    */
    public int numSimilarGroups(String[] strs) {

        int n = strs.length;

        // parent and rank array for union and find
        int[] parent = new int[n];
        int[] rank = new int[n];

        // initially every string is its parent and rank is 0
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        for (int i = 0; i < strs.length; i++) {
            for (int j = i + 1; j < strs.length; j++) {
                int diff = countDiffInPlaces(strs[i], strs[j]); // check all difference in letters

                if (diff == 0 || diff == 2) { // if it is 0 or 2, perform merge
                    unionByRank(i, j, rank, parent);
                }
            }
        }

        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < n; i++) { // get all the unique parent in the array
            set.add(findByPathCompression(i, parent));
        }

        return set.size();

    }
    
    private int countDiffInPlaces(String a, String b) { 
        if (a.equals(b)) {
            return 0;
        }

        int diff = 0;

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                diff++;
                if(diff > 2) {
                    return diff;
                }
            }
        }

        return diff;
    }
    
    private int findByPathCompression(int i, int[] parent) { // standard find by path compression algo
        
        if (parent[i] == i) {
            return i;
        }
        
        return parent[i] = findByPathCompression(parent[i], parent);
    }

    private void unionByRank(int a, int b, int[] rank, int[] parent) { // standard union by rank algo
        int parA = findByPathCompression(a, parent);
        int parB = findByPathCompression(b, parent);

        if (parA ==parB) {
            return;
        }

        int rankA = rank[parA];
        int rankB = rank[parB];

        if (rankA > rankB) {
            parent[parB] = parA;
        } else if (rankB > rankA) {
            parent[parA] = parB;
        } else {
            parent[parB] = parA;
            rank[parA]++;
        }
    }


    public static void main(String[] args) {
        SimilarStringGroup obj = new SimilarStringGroup();

        String[] strs = new String[] { "ajdidocuyh", "djdyaohuic", "ddjyhuicoa", "djdhaoyuic", "ddjoiuycha",
                "ddhoiuycja", "ajdydocuih", "ddjiouycha", "ajdydohuic", "ddjyouicha" };

        System.out.println(obj.numSimilarGroups(strs));
    } 
}
