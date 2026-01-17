package Trie;

import java.util.HashMap;
import java.util.Map;

public class TrieStructure {

    private TrieNode root;

    public TrieStructure() {
        this.root = new TrieNode();
    }
    

    public void insert(final String word) {

        TrieNode currNode = root;

        for (int i = 0; i < word.length(); i++) {
            int index = (int) (word.charAt(i) - 'a');

            if (currNode.children[index] == null) {
                TrieNode child = new TrieNode();
                currNode.children[index] = child;
            }

            currNode = currNode.children[index];
        }

        currNode.isEndOfWord = true;

    }


    public boolean search(String word) {
        TrieNode currNode = root;

        for (int i = 0; i < word.length(); i++) {
            int index = (int) (word.charAt(i) - 'a');

            if (currNode.children[index] == null) {
                return false;
            }

            currNode = currNode.children[index];
        }

        return currNode.isEndOfWord;

    }

    private String appendChar(String str, char ch) {

        StringBuilder s = new StringBuilder(str);
        s.append(ch);

        return s.toString();
    }

    private void check(String arr[], int n) {

        Map<String, Integer> freq = new HashMap<>();


        for (int i = 0; i < n; i++) {
            String str = arr[i];
            TrieNode currNode = root;

            String code = "";

            //block to generate the code for each city.
            for (int j = 0; j < str.length(); j++) {
                int index = (int) (str.charAt(j) - 'a');

                code = appendChar(code, str.charAt(j));

                if (currNode.children[index] == null) {
                    TrieNode child = new TrieNode();
                    currNode.children[index] = child;
                    break;
                }

                currNode = currNode.children[index];
            }
            
            // insert the city into the trie to check longest prefix.
            insert(str);

            if (freq.containsKey(str) == false) {
                freq.put(str, 0);
            }

            int value = freq.get(str);
            value++;
            freq.put(str, value);



            if (code.equals(str)) {
                if (freq.get(code) > 1) {
                    char ch = (char) (value + 48);
                    code = appendChar(code, ch);
                }
            }
            
            System.out.println(code);

        }
        
    }




    // Trie Node Class
    public static class TrieNode {

        private boolean isEndOfWord;
        private TrieNode[] children;
        private int size = 26;

        public TrieNode() {
            this.isEndOfWord = false;
            children = new TrieNode[size];

            for (TrieNode node : children) {
                node = null;
            }
        }

    }

    public static void main(String[] args) {

    }
    
}


