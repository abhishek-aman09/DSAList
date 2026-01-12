package String;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupAnagram {

    // https://leetcode.com/problems/group-anagrams/description/
    // Key is unique freq of each char in freq array eg : 12003040010120... all 26 chars.
    
    public List<List<String>> groupAnagrams(String[] strs) {

        int n = strs.length;

        Map<String, List<String>> anagrams = new HashMap<>();

        for (int i = 0; i < n; i++) {
            putAnagramIntoMap(strs[i], anagrams);
        }

        List<List<String>> ans = new ArrayList<>();

        for (Map.Entry<String, List<String>> pair : anagrams.entrySet()) {
            List<String> list = new ArrayList<>();

            for (String str : pair.getValue()) {
                list.add(str);
            }

            ans.add(list);
        }

        return ans;

    }
    
    private void putAnagramIntoMap(String str, Map<String, List<String>> anagrams) {

        int[] freq = new int[26];

        for (int i = 0; i < str.length(); i++) {
            freq[str.charAt(i) - 'a']++;
        }

        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 26; i++) {
            char ch = (char) (freq[i] + '0');
            key.append(ch);
        }

        if (!anagrams.containsKey(key.toString())) {
            anagrams.put(key.toString(), new ArrayList<>());
        }

        List<String> list = anagrams.get(key.toString());
        list.add(str);
        
        anagrams.put(key.toString(), list);
    }
    
}
