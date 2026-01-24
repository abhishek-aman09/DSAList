package Heap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.List;

public class TopKFrequentWords {

    // https://leetcode.com/problems/top-k-frequent-words/description

    /*
    Given an array of strings words and an integer k, return the k most frequent strings.
    
    Return the answer sorted by the frequency from highest to lowest. Sort the words with the same frequency by their lexicographical order.
    
    Input: words = ["i","love","leetcode","i","love","coding"], k = 2
    Output: ["i","love"]
    Explanation: "i" and "love" are the two most frequent words.
    Note that "i" comes before "love" due to a lower alphabetical order.
    
    Map + Heap approach : count freq of each word using map. Put it into 
    heap sorted on freq(and name if freq match). Return the top K.
    */
    

    public List<String> topKFrequent(String[] words, int k) {

        Map<String, Integer> freqMap = new HashMap<>();

        for (String word : words) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>(
                (a, b) -> {
                if (a.freq != b.freq) {
                    return  Integer.compare(b.freq, a.freq);
                } else {
                    return a.word.compareTo(b.word);
                }
            }     
        );

        for (Map.Entry<String, Integer> pair : freqMap.entrySet()) {
            pq.add(new Pair(pair.getKey(), pair.getValue()));
        }

        List<String> ans = new ArrayList<>();

        while (k > 0) {
            ans.add(pq.poll().word);
            k--;
        }

        return ans;

    }
    
    private static class Pair {
        
        String word;
        int freq;

        Pair(String word, int freq) {
            this.word = word;
            this.freq = freq;
        }  
    } 
    

}
