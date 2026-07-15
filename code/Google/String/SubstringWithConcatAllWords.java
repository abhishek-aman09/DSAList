package Google.String;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubstringWithConcatAllWords {

    /*
    https://leetcode.com/problems/substring-with-concatenation-of-all-words/description/
    
    You are given a string s and an array of strings words. All the strings of words are of the same length.
    
    A concatenated string is a string that exactly contains all the strings of any permutation of words concatenated.
    
    For example, if words = ["ab","cd","ef"], then "abcdef", "abefcd", "cdabef", "cdefab", "efabcd", and "efcdab" 
    are all concatenated strings. "acdbef" is not a concatenated string because it is not the concatenation of any 
    permutation of words.
    Return an array of the starting indices of all the concatenated substrings in s. You can return the answer in any order.
    
    Example 1:
    
    Input: s = "barfoothefoobarman", words = ["foo","bar"]    
    Output: [0,9]
    
    Explanation:   
    The substring starting at 0 is "barfoo". It is the concatenation of ["bar","foo"] which is a permutation of words.
    The substring starting at 9 is "foobar". It is the concatenation of ["foo","bar"] which is a permutation of words.
    
    Approach : Sliding window
    create a hashmap with frequency of the words int the array.
    let s = "barfoofoobarthefoobarman" , word = ["bar","foo","the"] 
    
    wordLen = 3. We run the outer loop from 0 to wordlen - 1 and innert loop from i to end to generate all words of len wordLen
    int the window. 
    for i = 0, right start from 0 and generate all the words - [bar, foo, foo, bar, the, foo, bar, man]
    cond 1 : now for each word, we check if it exist in map, if it does, we increase the count
        subCond 1 :next we check the frequency of the current word is more than what we have available, if yes, the window becomes invalid
        we will start removing the words of worLen from left of window till the freq comes under permittable value.
        after getting permitted value, we will check if number of words used is equal to total number of words, if yes,
        the start of current window(marked by left pointer) has valid string, push it into ans array.
    
    cond 2 : if current word is not in our dictionary, the whole window starting from left till current right + wordLen has
    become invalid, we should start new iteration from right + wordLen as new window
    
    why outer loops run only from 0 to wordLen - 1? 
    As inner loop genertes words of size wordLen, running outer loop will generate all the possible combinations, eg :
    for i = 0 inner loop will generate - [bar, foo, foo, bar, the, foo, bar, man]
    for i = 1 inner loop will generate - [arf, oof, oob, art, hef, oob, arm]
    for i = 2 inner loop will generate - [rfo, ofo, oba, rth, efo, oba, rma]
    for i = 3 again the inner loop will have - [foo, foo, bar, the ,foo, bar, man] which is the initial array only skipping 
    the first word.
    */
    
    public List<Integer> findSubstring(String s, String[] words) {

        int n = words.length;
        int wordLen = words[0].length();

        int sLen = s.length();

        int minWindowLen = n * wordLen;

        if (minWindowLen > sLen) {
            return new ArrayList<>();
        }

        Map<String, Integer> availableFreq = new HashMap<>();
        // count all the available frequency
        for (String word : words) {
            availableFreq.put(word, availableFreq.getOrDefault(word, 0) + 1);
        }


        List<Integer> ans = new ArrayList<>();

        // run outer loop from 0 to wordLen - 1 times
        for (int i = 0; i < wordLen; i++) {
            int left = i; // current window will initially start at i
            Map<String, Integer> freqInWindow = new HashMap<>(); // map to store freq of words inside window
            int countUsed = 0; // counter

            for (int right = i; right + wordLen <= sLen; right += wordLen) { // inner loop resposible to generate all words from i to end
                String word = s.substring(right, right + wordLen); // form a word

                if (availableFreq.containsKey(word)) { // if it is a dictionary word
                    
                    freqInWindow.put(word, freqInWindow.getOrDefault(word, 0) + 1); // increase its freq in window map
                    countUsed++; // increment count

                    while (freqInWindow.get(word) > availableFreq.get(word)) { // this checks if the freq of current word
                        // is more than allowed, like we have two "foo" in window but max freq of "foo" is 1
                        // in that case, the window from, left to right + wordLen becomes invalid, we will remove them
                        String wordRemovedFromLeft = s.substring(left, left + wordLen); // start forming words from left of len wordLen
                        freqInWindow.put(wordRemovedFromLeft, freqInWindow.get(wordRemovedFromLeft) - 1); // reduce its freq
                        // no need to check if it exit in tha map, as we already doing an existence check at line 87
                        countUsed--; // decrease the total count
                        left += wordLen; // increment left
                    }

                    if (countUsed == n) { // if total words in map matches n, we have a valid window starting at left
                        ans.add(left);
                    }
                } else { // if current word does not exist in the dict, the window from left to right + wordLen is invalid
                    // hence we reset everything.
                    freqInWindow.clear();
                    left = right + wordLen;
                    countUsed = 0;
                }
            }
        }

        return ans;

    }
    
    public static void main(String[] args) {
        SubstringWithConcatAllWords obj = new SubstringWithConcatAllWords();

        String words[] = new String[] { "aa", "aa" };
        String s = "aaaaaaaaaaaaaa";

        System.out.println(obj.findSubstring(s, words));
    }
    
}
