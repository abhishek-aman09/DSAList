package Google.Trie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordSearchII {

    /*
    https://leetcode.com/problems/word-search-ii/description/
    
    Given an m x n board of characters and a list of strings words, return all words on the board.
    
    Each word must be constructed from letters of sequentially adjacent cells, 
    where adjacent cells are horizontally or vertically neighboring. 
    The same letter cell may not be used more than once in a word.
    
    Input: board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]], 
            words = ["oath","pea","eat","rain"]
    Output: ["eat","oath"]
    
    Approach : Insert all the words into the trie, iterate each cell of the board
    If the current cell is starting of any word, search all the words that can be formed with that
    */

    private static boolean isVisited[][];

    public List<String> findWords(char[][] board, String[] words) {

        int n = board.length;
        int m = board[0].length;

        List<String> result = new ArrayList<>();

        if (n == 0 || m == 0 || words.length == 0) {
            return result;
        }

        isVisited = new boolean[n][m];

        TrieNode root = new TrieNode();

        for (String word : words) { // push all the words into the trie
            insertIntoTrie(word, root);
        }

        Set<String> resultSet = new HashSet<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                char ch = board[i][j];
                int charIndx = ch - 'a';
                // if current char is starting of a word, push it into curr string and check for all the words that can be formed
                if (root.child[charIndx] != null) {
                    StringBuilder currWord = new StringBuilder();
                    currWord.append(ch);
                    Set<String> currSet = new HashSet<>();
                    searchInAllDirection(i, j, n, m, root.child[charIndx], board, currWord, currSet);
                    resultSet.addAll(currSet);
                }
            }
        }
        result.addAll(resultSet);
        return result;

    }

    private void searchInAllDirection(int i, int j, int n, int m, TrieNode root, char[][] board, StringBuilder currWord,
            Set<String> wordSet) {

        isVisited[i][j] = true;

        if (root.isEnd) {
            wordSet.add(currWord.toString());
        }

        // check for down path
        if (isSafe(i + 1, j, n, m)) {
            char nextChar = board[i + 1][j];
            int nextIndex = nextChar - 'a';

            if (root.child[nextIndex] != null) { // check if there exist a word in trie with given path
                currWord.append(nextChar); // append the char
                searchInAllDirection(i + 1, j, n, m, root.child[nextIndex], board, currWord, wordSet);
                currWord.setLength(currWord.length() - 1); // remove the char
            }
        }

        // check for upward path
        if (isSafe(i - 1, j, n, m)) {
            char nextChar = board[i - 1][j];
            int nextIndex = nextChar - 'a';

            if (root.child[nextIndex] != null) { // check if there exist a word in trie with given path
                currWord.append(nextChar); // append the char
                searchInAllDirection(i - 1, j, n, m, root.child[nextIndex], board, currWord, wordSet);
                currWord.setLength(currWord.length() - 1); // remove the char
            }
        }

        // check for right path
        if (isSafe(i, j + 1, n, m)) {
            char nextChar = board[i][j + 1];
            int nextIndex = nextChar - 'a';

            if (root.child[nextIndex] != null) { // check if there exist a word in trie with given path
                currWord.append(nextChar); // append the char
                searchInAllDirection(i, j + 1, n, m, root.child[nextIndex], board, currWord, wordSet);
                currWord.setLength(currWord.length() - 1); // remove the char
            }
        }

        // check for left path
        if (isSafe(i, j - 1, n, m)) {
            char nextChar = board[i][j - 1];
            int nextIndex = nextChar - 'a';

            if (root.child[nextIndex] != null) { // check if there exist a word in trie with given path
                currWord.append(nextChar); // append the char
                searchInAllDirection(i, j - 1, n, m, root.child[nextIndex], board, currWord, wordSet);
                currWord.setLength(currWord.length() - 1); // remove the char
            }
        }

        isVisited[i][j] = false;
        
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

    private boolean isSafe(int i, int j, int n, int m) {
        return (i < n && i >= 0 && j < m && j >= 0 && !isVisited[i][j]);
    }
    
}
