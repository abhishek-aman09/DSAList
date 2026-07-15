package String;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class WordLadderII {

    /*
    https://leetcode.com/problems/word-ladder-ii/
    
    A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:
    
    Every adjacent pair of words differs by a single letter.
    Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
    sk == endWord
    Given two words, beginWord and endWord, and a dictionary wordList, return all the shortest transformation sequences from beginWord to endWord, or an empty list if no such sequence exists. Each sequence should be returned as a list of the words [beginWord, s1, s2, ..., sk].
    
    Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
    Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
    Explanation: There are 2 shortest transformation sequences:
    "hit" -> "hot" -> "dot" -> "dog" -> "cog"
    "hit" -> "hot" -> "lot" -> "log" -> "cog"
    
    Approach 1 : create a Queue<List<String>>, push the begin word first, generate all the combinations and check in
    the dictionary, if found, create a new List with the new word at the last and put the list into the queue.
    On each iteration, fetch the last string in the list and generate all combinations and check its presence in map,
    if found, create new list and push into the queue. We remove a word from the dictionary, once it is used in all
    the list in previous iteration. For this we can do a level order traversal and keep a set of words used on a
    particular level.
    The queue will look something like : 
    Queue -> [["hit"], ["hit, hot"], ["hit", "hot", "lot"], ["hit", "hot", "pot"]]...
    
    when we hit the end word, we mark the length of the list, post that we only allow the list of strings whose length is equal
    to the min list lenght.
    
    
    Approach 2 : (slightly better) 
    Works on words at each level, beginWord level is always 0. We make a map to map the words in dictionary to its
    lowest possible level. e.g
    hit -> 0, hot -> 1, pot -> 2, lot -> 2, log -> 3, cog -> 4 ...
    
    the levels are assigned exactly like level order traversal (using a null).
    We keep a set of words to keep track of words used on a particular level and once we have encountered the end words,
    we stop generating any more combinations.
    
    once everything is mapped, we start backtracking the path from end word to begin word.
    Why? its less work, as we will only track those path using which we can reach the begin word and leave out those paths
    which may lead to an open end.
    
    To backtrack, we start will a word, check its level, generate all combinations, check if any of those exist in one level
    lower than current and recursively call that word until we reach to the begin word
    

    */
    
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {

        // Initial check, if both are of different lengths, graph formation is  not possible
        int srcLen = beginWord.length();
        int endLen = endWord.length();

        if (srcLen != endLen) {
            return new ArrayList<>();
        }

        // set to store all the dictionary words, del is log(n), hence use set
        Set<String> dictionarySet = new HashSet<>(wordList);

        // map to map each word to its level
        Map<String, Integer> wordWithLevel = new HashMap<>();
        // set to keep track of words used on each level
        Set<String> usedOnLevel = new HashSet<>();

        // var for curr level
        int currLevel = 0;

        // main queue
        Queue<String> bfsQueue = new LinkedList<>();

        wordWithLevel.put(beginWord, 0);// begin word is always at level 0

        bfsQueue.offer(beginWord);
        bfsQueue.offer(null);// using null for level order traversal

        boolean isEndWordFound = false; // flag to check if we have reached end word

        while (!bfsQueue.isEmpty()) {
            String currWord = bfsQueue.poll(); // for from queue

            if (currWord == null) {
                if (!bfsQueue.isEmpty()) { // if queue is not empty, push another null to mark level
                    bfsQueue.offer(null);
                }

                for (String el : usedOnLevel) { // last level was traversed, we can delete all the used words in that level
                    dictionarySet.remove(el);
                }
                usedOnLevel.clear();

                currLevel++; // increment the level
                continue;
            }

            if (!wordWithLevel.containsKey(currWord)) { // if current word is not mapped to a level, map it
                wordWithLevel.put(currWord, currLevel);
            }
            
            if (currWord.equals(endWord)) { // if we have reached the end word, flag it and dont generate more combinations
                isEndWordFound = true;
                continue;
            }

            char[] currWordArray = currWord.toCharArray(); // convert current word to char array

            for (int i = 0; i < srcLen; i++) { // iterate each character
                char origialChar = currWordArray[i]; 
                // generate all possible string combiantions
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    currWordArray[i] = ch;

                    String newWord = new String(currWordArray);

                    if (dictionarySet.contains(newWord) && !usedOnLevel.contains(newWord)) { // only push the word to queue if it is not used in current level
                        usedOnLevel.add(newWord); // this way we can keep each word unique in queue
                        bfsQueue.offer(newWord);
                    }
                }
                currWordArray[i] = origialChar;
            }

        }

        if (!isEndWordFound) { // if we cannot reach the word, return empty list
            return new ArrayList<>();
        }

        Deque<String> currPath = new LinkedList<>(); // deque to store path from end to begin word
        List<List<String>> ans = new ArrayList<>();

        backtrackListFromEnd(beginWord, endWord, wordWithLevel, ans, currPath);

        return ans;

    }
    
    private void backtrackListFromEnd(String beginWord, String currentWord, Map<String, Integer> wordsAtEachLevel,
            List<List<String>> ans, Deque<String> currPath) {

        if (currentWord.equals(beginWord)) { // if we have recursively reached the begin word, we push it into deque, make it a list
            // and push it into ans and return
            currPath.addFirst(currentWord);
            List<String> temp = new ArrayList<>(currPath);
            currPath.removeFirst();
            ans.add(temp);
            return;
        }
        int endLevel = wordsAtEachLevel.get(currentWord); // get level of current word

        char[] endCharArray = currentWord.toCharArray(); // convert to char array

        currPath.addFirst(currentWord);// push it in front of deque. As we are backtracking, always push in front for correct path

        // same as above, generate all possible combinations
        for (int i = 0; i < endCharArray.length; i++) {
            char originalChar = endCharArray[i];

            for (char ch = 'a'; ch <= 'z'; ch++) {
                endCharArray[i] = ch;
                String newString = new String(endCharArray);

                // if the current combination exist in map and it is one level lower than current, we recursively call it
                if (wordsAtEachLevel.containsKey(newString) && wordsAtEachLevel.get(newString) == endLevel - 1) {
                    backtrackListFromEnd(beginWord, newString, wordsAtEachLevel, ans, currPath);
                }
            }
            endCharArray[i] = originalChar;
        }
        // remove the current word from path before going back to the parent
        currPath.removeFirst();

    }
    

    public static void main(String[] args) {
        WordLadderII obj = new WordLadderII();

        List<List<String>> ans = obj.findLadders("hit", "cog", List.of("hot","dot","dog","lot","log"));

        ans.stream().forEach((list) ->
            {
            list.stream().forEach(el -> System.out.print(el + " "));
            System.out.println();
            }   
        );
    }
    
}
