package Trie;

import java.util.List;

public class WordBreakII {
    
    public List<String> findWords(char[][] board, String[] words) {

        int n = board.length;
        int m = board[0].length;

        Trie root = new Trie();

        for (int i = 0; i < words.length; i++) {
            insert(words[i], root);
        }

    }
    
    private void dfs(int i, int j, char[][] board, String word, int ind, Trie root) {
        int n = board.length;
        int m = board[0].length;

        if (i >= n || j >= m || i < 0 || j < 0) {
            return;
        }

        if (ind >= word.length()) {
            return;
        }

        

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
}
