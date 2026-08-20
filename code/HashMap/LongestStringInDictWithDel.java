package HashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LongestStringInDictWithDel {

    public String findLongestWord(String s, List<String> dictionary) {

        int n = s.length();
        Map<Character, List<Integer>> charToPosList = new HashMap<>();

        for (int i = 0; i < n; i++) {
            charToPosList.computeIfAbsent(s.charAt(i), key -> new ArrayList<>()).add(i);
        }

        String ans = "";

        for (String word : dictionary) {
            boolean isPresent = checkIfPresent(word, charToPosList);

            if (isPresent) {
                if ((word.length() > ans.length()) || (word.length() == ans.length() && word.compareTo(ans) < 0)) {
                    ans = word;
                }
            }
        }

        return ans;

    }
    
    private boolean checkIfPresent(String str, Map<Character, List<Integer>> charToPosList) {

        int minIndex = -1;

        for (char ch : str.toCharArray()) {

            if(!charToPosList.containsKey(ch)) {
                return false;
            }
            int smallestPosGreaterThanMin = getSmallestPosGreaterThanMin(minIndex, charToPosList.get(ch));
            
            if(smallestPosGreaterThanMin == -1) {
                return false;
            }

            minIndex = smallestPosGreaterThanMin;
        }

        return true;
    }

    private int getSmallestPosGreaterThanMin(int minPos, List<Integer> list) {

        int ans = -1;

        int l = 0, r = list.size() - 1;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (list.get(mid) > minPos) {
                ans = list.get(mid);
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        
        return ans;
    }
    
}
