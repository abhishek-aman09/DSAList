package Graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class WordLadder {

    /*
    https://leetcode.com/problems/word-ladder/description/
    
    A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:
    
    Every adjacent pair of words differs by a single letter.
    Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
    sk == endWord
    Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.
    
    
    
    Example 1:
    
    Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
    Output: 5
    Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.
    
    Approach : bfs by changing each char to string and trying out
    */

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        int n = beginWord.length();

        if(n != endWord.length() || beginWord.equals(endWord)) {
            return 0;
        }

        // set to store all unique dict words
        Set<String> set = new HashSet<>();

        set.addAll(wordList);

        // if dict does not contain end word, you cannot reach it
        if(!set.contains(endWord)) {
            return 0;
        }

        // Queue for bfs
        Queue<Pair> q = new LinkedList<>();
        q.add(new Pair(beginWord, 1));

        while(!q.isEmpty()) {
            Pair curr = q.poll();
            String word = curr.word;
            int dist = curr.dist;

            // convert curr word to char array
            char[] str = word.toCharArray();

            // traverse over length of curr word
            for(int i = 0 ; i < n; i++) {
                // store the original char before changing
                char orChar = str[i];
                // for each index try replacing with all the characters
                for(char ch = 'a'; ch <= 'z'; ch++) {

                    str[i] = ch;
                    // for new word for each replacement
                    String newWord = new String(str);

                    // if new word is end word, return dist + 1
                    if(newWord.equals(endWord)) {
                        return dist + 1;
                    }

                    // else if it exist in set, put it in the queue and remove from set
                    if(set.contains(newWord)) {
                        q.add(new Pair(newWord, dist + 1));
                        set.remove(newWord);
                    }

                }
                // reinstate the original char back to array
                str[i] = orChar;

            }
        }

        // return 0 if q gets empty
        return 0;
        
    }

    private static class Pair {
        String word;
        int dist;

        Pair(String word, int dist) {
            this.word = word;
            this.dist = dist;
        }
    }
    
}
