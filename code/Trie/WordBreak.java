package Trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordBreak {
    

    // https://leetcode.com/problems/word-break/description/

    /*
    Given a string s and a dictionary of strings wordDict, 
    return true if s can be segmented into a space-separated sequence of one or more dictionary words.
    
    Note that the same word in the dictionary may be reused multiple times in the segmentation.
    
    Input: s = "leetcode", wordDict = ["leet","code"]
    Output: true
    
    Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
    Output: false
    
    Approach
    
    Construct a trie and put all dictionary words into it.
    start with index 0, check if the child at curr ind is null. If yes, return false
    
    if is end of current child node is true, we recursively call for next word search
    from the top of trie, if that return true, return true
    
    At the end, return the isEnd of current node. 
    
    -> Application of dp
    
    the problem may have overlapping subproblem
    
    for ex. str = 'aaaab' and dict = 'a', 'aaa', 'aa'
    while searching we will recursively call 'a' four times to check 
    possibility of aaab, aab, ab and b
    if it return false, it will again try for 'aa' and call for
    aab, ab and b. the subproblem is recalculate.
    Hence we will store the result in dp[i] where i the result will be stored
    for string starting from index i to n - 1.

    */
    

    public boolean wordBreak(String s, List<String> wordDict) {

        int n = s.length();

        Trie root = new Trie();

        // insert all the words in dict into trie
        for (int i = 0; i < wordDict.size(); i++) {
            insert(wordDict.get(i), root);
        }
        
        // array to store the result of precomputed substring from i to n
        int dp[] = new int[n];
        Arrays.fill(dp, -1);

        return helper(s, 0, n, root, dp);

    }

    private boolean helper(String str, int left, int n, Trie headNode, int dp[]) {

        // base condition : if index is out of bound return true
        // why true? : recursive call will be made only if isEnd of ind - 1 is
        // true. Hence return true.
        if (left >= n) {
            return true;
        }

        if (dp[left] != -1) {
            return dp[left] == 1;
        }

        Trie root = headNode;

        // iterate over the loop. 
        for (int i = left; i < n; i++) {
            int ind = str.charAt(i) - 'a';

            // if child is null, return false
            if (root.child[ind] == null) {
                dp[i] = 0;
                return false;
            }

            root = root.child[ind];

            // if current node is end of a word, we try rest of the
            // string with start of node
            if (root.isEnd) {
                boolean isPossible = helper(str, i + 1, n, headNode, dp);
                if (isPossible) {
                    dp[i] = 1;
                    return true;
                }
            }
        }

        // else at end dp of curr ind will be the isEnd of the current root
        dp[left] = root.isEnd ? 1 : 0;
        return root.isEnd;

            
    }

    private void insert(String str, Trie root) {
        int n = str.length();

        Trie curr = root;

        for (int i = 0; i < n; i++) {
            int ind = str.charAt(i) - 'a'; 
            if (curr.child[ind] == null) {
                Trie node = new Trie();
                curr.child[ind] = node;
            }

            curr = curr.child[ind];
        }

        curr.isEnd = true;
    }
    
    private static class Trie {
        Trie child[];
        boolean isEnd;

        Trie() {
            this.child = new Trie[26];
            this.isEnd = false;
        }
    }

    public static void main(String[] args) {
        String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        List<String> dict = new ArrayList<>();

        dict.add("aaaaaaaaaa");
        dict.add("aaaaaaaaa");
        dict.add("aaaaaaaa");
        dict.add("aaaaaaa");
        dict.add("aaaaaa");
        dict.add("aaaaa");
        dict.add("aaaa");
        dict.add("aaa");
        dict.add("aa");
        dict.add("a");

        WordBreak obj = new WordBreak();

        System.out.println( obj.wordBreak(str, dict));
    }
}
