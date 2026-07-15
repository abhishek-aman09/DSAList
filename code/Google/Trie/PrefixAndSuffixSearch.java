package Google.Trie;

import java.util.ArrayList;
import java.util.List;

public class PrefixAndSuffixSearch {

    /*
    https://leetcode.com/problems/prefix-and-suffix-search/description
    
    Design a special dictionary that searches the words in it by a prefix and a suffix.
    
    Implement the WordFilter class:
    
    WordFilter(string[] words) Initializes the object with the words in the dictionary.
    f(string pref, string suff) Returns the index of the word in the dictionary, 
    which has the prefix pref and the suffix suff. If there is more than one valid index, return the largest of them.
    If there is no such word in the dictionary, return -1.
    
    Input
    ["WordFilter", "f"]
    [[["apple"]], ["a", "e"]]
    Output
    [null, 0]
    Explanation
    WordFilter wordFilter = new WordFilter(["apple"])
    wordFilter.f("a", "e"); // return 0, because the word at index 0 has prefix = "a" and suffix = "e".
    
    Approach : create two trie nodes, one to store the words linearly, other to store them in reverse order
    during insertion, insert indexes of all the words which has current prefix into its list to access
    
    first, traverse the perfix until end, if you cannot reach end return null else return the list of indexex it store
    second, reverse the suffix to make it prefix for reverse and do the same as prefix search 
    
    check if both lists are valid, now start comparing the indicex of both list till you find the first common index
    
    */

    public static void main(String[] args) {
        String[] words = new String[] { "abbba", "abba" };

        WordFilter obj = new WordFilter(words);

        System.out.println(obj.f("ab","ba"));

    }
    
}

class WordFilter {

    TrieNode linear;
    TrieNode reverse;
    public WordFilter(String[] words) {

        this.linear = new TrieNode();
        this.reverse = new TrieNode();

        int i = 0;
        for (String word : words) {
            insertIntoTrie(word, linear, i); // insert word linearly
            insertIntoTrie(new StringBuilder(word).reverse().toString(), reverse, i); // insert word in reverse
            i++;
        }
        
    }
    
    public int f(String pref, String suff) {

        List<Integer> linearIndex = searchPrefix(pref, linear); // get the list of indices of words which match current prefix
        // reverse the suffix to make it prfix and do the same
        List<Integer> reverseIndex = searchPrefix(new StringBuilder(suff).reverse().toString(), reverse);

        if (linearIndex == null || reverseIndex == null) { // if any list is invalid, no word exist
            return -1;
        }

        int l = linearIndex.size() - 1; 
        int r = reverseIndex.size() - 1;
        
        while (l >= 0 && r >= 0) { // start traversing both lists from end to find first common index
            int valL = linearIndex.get(l); // lists are sorted in ascending order 
            int valR = reverseIndex.get(r);

            if(valL == valR) {
                return valL;
            } else if (valL > valR) {
                l--;
            } else {
                r--;
            }
        }

        return -1;

    }

    private List<Integer> searchPrefix(String prefix, TrieNode root) {

        for (int i = 0; i < prefix.length(); i++) {
            int charIndx = prefix.charAt(i) - 'a';

            if (root.child[charIndx] == null) { // return null if prefix char is not present in trie
                return null;
            }
            root = root.child[charIndx];
        }
        
        return root.wordsWithCurrPrefix; // return the index list
    }

    private void insertIntoTrie(String word, TrieNode root, int wordIndex) {

        for (int i = 0; i < word.length(); i++) {

            int currCharPos = word.charAt(i) - 'a';

            if (root.child[currCharPos] == null) {
                TrieNode childNode = new TrieNode();
                root.child[currCharPos] = childNode;
            }

            root.child[currCharPos].wordsWithCurrPrefix.add(wordIndex); // add the current word index to list of words
            //with curr prefix
            root = root.child[currCharPos];
        }

    }
    
    private static class TrieNode {
        TrieNode[] child;
        List<Integer> wordsWithCurrPrefix; // list to store the indices of words with curernt prefix

        TrieNode() {
            this.child = new TrieNode[26];
            wordsWithCurrPrefix = new ArrayList<>();
        }
    }
}
