package Google.Trie;

public class LongestWordInDict {

    /*
    https://leetcode.com/problems/longest-word-in-dictionary/description
    
    Given an array of strings words representing an English Dictionary, 
    return the longest word in words that can be built one character at a time by other words in words.
    
    If there is more than one possible answer, return the longest word with the smallest lexicographical order. 
    If there is no answer, return the empty string.
    
    Note that the word should be built from left to right with each additional character 
    being added to the end of a previous word. 
    
    Example 1:
    
    Input: words = ["w","wo","wor","worl","world"]
    Output: "world"
    Explanation: The word "world" can be built one character at a time by "w", "wo", "wor", and "worl".
    
    Approach : Basic trie structure and insertion. start seach from a to z to ensure lexical order.
    always check if the current node is end of a word, if it is call its child recursively
    maintain two variable, one to store currWord and one to store longest.
    once you find the curr > longest, copy it to the longest
    
    
    */

    public String longestWord(String[] words) {

        TrieNode root = new TrieNode();

        for (String word : words) {
            insertIntoTrie(word, root);
        }

        StringBuilder currWord = new StringBuilder();
        StringBuilder longestWord = new StringBuilder();

        for (int i = 0; i < 26; i++) {

            if (root.child[i] != null && root.child[i].isEnd == true) { // start iteration only if curr node is a end of a word
                char currChar = (char) (i + 'a');
                currWord.append(currChar); // append the current char to current word
                getLongestWord(root.child[i], currWord, longestWord);
                currWord.setLength(0); // clear the string for next iteration
            }
        }

        return longestWord.toString();

    }
    
    private void getLongestWord(TrieNode root, StringBuilder currWord, StringBuilder longestWord) {

        for (int i = 0; i < 26; i++) {

            if (root.child[i] != null && root.child[i].isEnd) { // if the child node is end of a word
                char currChar = (char) (i + 'a'); // append its char to currLen
                currWord.append(currChar);
                getLongestWord(root.child[i], currWord, longestWord); // call its child recursively
                currWord.setLength(currWord.length() - 1); // remove it from the current word
            }

            if (currWord.length() > longestWord.length()) { // check if we have a longer word, if yes, copy it to longest
                copyStringBuilder(currWord, longestWord);
            }

        }
    }

    private void insertIntoTrie(String word, TrieNode root) { // basic insertion

        for (int i = 0; i < word.length(); i++) {

            int currCharPos = word.charAt(i) - 'a';

            if (root.child[currCharPos] == null) {
                TrieNode childNode = new TrieNode();
                root.child[currCharPos] = childNode;
            }

            root = root.child[currCharPos];
        }

        root.isEnd = true;
    }

    private static class TrieNode {
        TrieNode[] child;
        boolean isEnd;

        TrieNode() {
            this.child = new TrieNode[26];
            this.isEnd = false;
        }
    }

    public static void copyStringBuilder(StringBuilder src, StringBuilder dest) { // method to copy one stringbuilder to another
        // 1. Reset the destination's length to 0 (retains the internal char buffer)
        dest.setLength(0);
        
        // 2. Append the source data directly into the destination buffer
        // This copies characters at the internal array level without creating new instances
        dest.append(src, 0, src.length());
    }

    public static void main(String[] args) {
        LongestWordInDict obj = new LongestWordInDict();

        String[] words = new String[] { "w", "wo", "wor", "worl", "world" };
        System.out.println(obj.longestWord(words));
    }
    
}
