package String;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ReorganiseString {

    /* https://leetcode.com/problems/reorganize-string/description/
    
    Given a string s, rearrange the characters of s so that any two adjacent characters are not the same.
    
    Return any possible rearrangement of s or return "" if not possible.
    
    Input s : aaabbbcc
    Output : ababcabc
    
    Approach : Use a map/array to keep count of freq.
    if freq of any char is > (n + 1)/2. Solution is not possible.
    create a priority queue sorted on freq.
    have a prevChar to keep track of previous character.
    loop through the queue, keep adding top char if does not matches the
    preChar else second top char.
    */
    

    public String reorganizeString(String s) {

        int n = s.length();

        if (n == 1) {
            return s;
        }
        
        Map<Character, Integer> freqOfChars = new HashMap<>();
        PriorityQueue<Pair> maxFreqFirst = new PriorityQueue<>(
            (a, b) ->  b.second - a.second
        );

        for (int i = 0; i < n; i++) {
            freqOfChars.put(s.charAt(i), freqOfChars.getOrDefault(s.charAt(i), 0) + 1);

            double diff = (n + 1) / 2.0 - (double) (freqOfChars.get(s.charAt(i)));

            if (diff < 0) {
                return "";
            }
        }

        for (Map.Entry<Character, Integer> pair : freqOfChars.entrySet()) {
            Pair temp = new Pair(pair.getKey(), pair.getValue());

            maxFreqFirst.add(temp);
        }

        StringBuilder str = new StringBuilder();
        char prevChar = '1';

        while (!maxFreqFirst.isEmpty()) {

            Pair top = maxFreqFirst.poll();

            if (top.first != prevChar) {

                str.append(top.first);
                top.second--;
                if (top.second > 0) {
                    maxFreqFirst.add(top);
                }
                prevChar = top.first;
            } else {
                Pair nextTop = maxFreqFirst.poll();

                str.append(nextTop.first);
                nextTop.second--;
                if (nextTop.second > 0) {
                    maxFreqFirst.add(nextTop);
                }
                prevChar = nextTop.first;
                maxFreqFirst.add(top);
            }
        }

        return str.toString();     
    }

    private static class Pair {

        char first;
        int second;

        Pair(char first, int second) {
            this.first = first;
            this.second = second;
        }
    }
    
    public static void main(String[] args) {
        ReorganiseString str = new ReorganiseString();

        System.out.println(str.reorganizeString("aaabbbcc"));
    }
}
