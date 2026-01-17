package Array;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FirstNegativeInEveryWindow {
    
    static List<Integer> firstNegInt(int arr[], int k) {

        int n = arr.length;

        Queue<Pair<Integer, Integer>> queue = new LinkedList<>();

        int i = 0;

        List<Integer> ans = new ArrayList<>();
        if (n < k) {
            return ans;
        }

        while (i < k) {
            if (arr[i] < 0) {
                queue.add(new Pair<Integer, Integer>(arr[i], i));
            }
            i++;
        }

        if (queue.isEmpty()) {
            ans.add(0);
        } else {
            ans.add(queue.peek().first);
        }

        int left = 0;

        for (; i < n; i++) {

            while (!queue.isEmpty() && queue.peek().second <= left) {
                queue.poll();
            }

            left++;
            if (arr[i] < 0) {
                queue.add(new Pair<Integer, Integer>(arr[i], i));
            }

            if (queue.isEmpty()) {
                ans.add(0);
            } else {
                ans.add(queue.peek().first);
            }
        }

        return ans;

    }
    
    static class Pair<K,V> {
        
        K first;
        V second;

        Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }
        
    }
    
}
