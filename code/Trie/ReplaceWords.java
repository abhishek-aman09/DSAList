package Trie;

import java.util.Arrays;
import java.util.List;

public class ReplaceWords {

    // https://leetcode.com/problems/replace-words/description

    /*
    In English, we have a concept called root, which can be followed by some other word to form another longer word - let's call this word derivative. For example, when the root "help" is followed by the word "ful", we can form a derivative "helpful".
    
    Given a dictionary consisting of many roots and a sentence consisting of words separated by spaces, replace all the derivatives in the sentence with the root forming it. If a derivative can be replaced by more than one root, replace it with the root that has the shortest length.
    
    Return the sentence after the replacement.
    
    Input: dictionary = ["cat","bat","rat"], sentence = "the cattle was rattled by the battery"
    Output: "the cat was rat by the bat"
    
    Approach : 
    Make a trie using dictionary words. Make a list of words from sentence
    using space as a delimiter. For each word, move into trie ->
    1. if child node is null : prefix does not exist, return the word itself.
    2. as soon as isEnd of root hit true, we found our shortest prefix, return it.
    3. if loop executes compeletely, the curr word is a part of long word in trie
    return the word itself.
    
    */
    
    public String replaceWords(List<String> dictionary, String sentence) {
        int n = dictionary.size();

        Trie root = new Trie();

        for (int i = 0; i < n; i++) {
            insert(root, dictionary.get(i));
        }

        List<String> words = Arrays.stream(sentence.split(" ")).filter(
                word -> !word.isEmpty()).toList();

        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < words.size(); i++) {
            String curr = words.get(i);

            String rootWord = getRoot(curr, root);

            ans.append(rootWord);

            if (i < words.size() - 1) {
                ans.append(' ');
            }
        }

        return ans.toString();
    }
    
    private String getRoot(String str, Trie root) {

        int i = 0;

        while (i < str.length()) {
            int ind = str.charAt(i) - 'a';
            // return string itself if prefix does not exist in trie
            if (root.child[ind] == null) {
                return str;
            }

            root = root.child[ind];

            // if is end is true, the current root marks end of root for 
            // current string
            if (root.isEnd) {
                return str.substring(0, i + 1);
            }
            i++;
        }

        return str;
    }

    public void insert(Trie root, String str) {

        for (int i = 0; i < str.length(); i++) {
            int ind = str.charAt(i) - 'a';

            if (root.child[ind] == null) {
                Trie temp = new Trie();
                root.child[ind] = temp;
            }

            root = root.child[ind];
        }

        root.isEnd = true;
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
        
        String sentence = "the cattle was rattled by the battery";

        List<String> words = List.of("cat", "bat", "rat");

        ReplaceWords obj = new ReplaceWords();
        
        System.out.println(obj.replaceWords(words, sentence));
    }

}
