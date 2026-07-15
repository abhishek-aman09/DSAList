package Google.Trie;

import java.util.ArrayList;
import java.util.List;

public class SearchSuggestionSystem {

    /*
    https://leetcode.com/problems/search-suggestions-system/
    
    You are given an array of strings products and a string searchWord.
    
    Design a system that suggests at most three product names from products after each character of searchWord is typed. 
    Suggested products should have common prefix with searchWord. 
    If there are more than three products with a common prefix return the three lexicographically minimums products.
    
    Return a list of lists of the suggested products after each character of searchWord is typed.
    
    Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
    Output: [
    ["mobile","moneypot","monitor"],
    ["mobile","moneypot","monitor"],
    ["mouse","mousepad"],
    ["mouse","mousepad"],
    ["mouse","mousepad"]]
    Explanation: products sorted lexicographically = ["mobile","moneypot","monitor","mouse","mousepad"].
    After typing m and mo all products match and we show user ["mobile","moneypot","monitor"].
    After typing mou, mous and mouse the system suggests ["mouse","mousepad"].
    
    Approach : one this missed int the description - If no words matches the current prefix, return empty list
    
    1. Do basic insertion in trie
    2. create a prefix string and start traversing the search word
    3. for each char in search word, check if the child is not null and we have prefix length one char short of current
    index as we haven't appended any in prefix
    If yes, append the char to prefix and call gerenateAllWordsWithCurrPrefix to get all the matching word
    If no, then there will exit no words matching the current prefix, push empty list to result
    
    4. gerenateAllWordsWithCurrPrefix implementation : continue calling recusively till you have three words or
    you have reached at end of last word in trie
    
    check of the current word is end of a word, if yes, push it into the list
    
    iteratively check all child from a to z (lexical order assurance)
    if child is not null, append it to currWord, make recursive call and then remove from the currWord
    
    
    */

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {

        TrieNode root = new TrieNode();

        for (String word : products) {
            insertIntoTrie(word, root);
        }

        List<List<String>> result = new ArrayList<>();
        StringBuilder prefix = new StringBuilder(); // prefix to store the max matching prefix of searchWord

        for (int i = 0; i < searchWord.length(); i++) {

            List<String> wordList = new ArrayList<>(); // list to store current wordList

            int currCharIndx = (int) (searchWord.charAt(i) - 'a'); // get char index

            if (root.child[currCharIndx] != null && prefix.length() == i) { // if child is not null and we have
                prefix.append(searchWord.charAt(i)); // prefix length equal to i as char at i is not yet appended
                root = root.child[currCharIndx];    // change the root pointer to child as for all future call it will be our root
            } else { // if we have no words matching current prefix, just empty list to ans
                result.add(new ArrayList<>());
                continue;
            }
            
            gerenateAllWordsWithCurrPrefix(prefix.toString(), root, wordList); // call method to return 3/all matching words

            result.add(new ArrayList<>(wordList));
            wordList.clear();

        }
        
        return result;

    }
    
    private void gerenateAllWordsWithCurrPrefix(String word, TrieNode root, List<String> wordList) {

        if (wordList.size() >= 3) {
            return;
        }
         
        if (root.isEnd) { // if we have reached to end of a word, store it
            wordList.add(word);
        }

        StringBuilder currWord = new StringBuilder(word); // create a word with curr prefix

        for (int i = 0; i < 26; i++) {

            if (root.child[i] != null) {
                currWord.append((char) ('a' + i)); // append child char

                gerenateAllWordsWithCurrPrefix(currWord.toString(), root.child[i], wordList); // call it recursively
 
                currWord.setLength(currWord.length() - 1); // remove the child char
            }

            if (wordList.size() >= 3) { // return early if we have enough words in the list
                return;
            }
        }

    }
    
    private void insertIntoTrie(String word, TrieNode root) {

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

    public static void main(String[] args) {
        String products[] = new String[] { "mobile", "mouse", "moneypot", "monitor", "mousepad" };
        String searchWord = "mouse";

        SearchSuggestionSystem obj = new SearchSuggestionSystem();

        List<List<String>> ans = obj.suggestedProducts(products, searchWord);

        System.out.println(ans);
    }
    
}
