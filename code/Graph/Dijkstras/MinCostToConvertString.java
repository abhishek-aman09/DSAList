package Graph.Dijkstras;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class MinCostToConvertString {

    // https://leetcode.com/problems/minimum-cost-to-convert-string-i/description/

    /*
    You are given two 0-indexed strings source and target, 
    both of length n and consisting of lowercase English letters. 
    You are also given two 0-indexed character arrays original and changed, 
    and an integer array cost, where cost[i] represents the cost of changing the character original[i] to the character changed[i].
    
    You start with the string source. In one operation, 
    you can pick a character x from the string and change it to the character y at a cost of z 
    if there exists any index j such that cost[j] == z, original[j] == x, and changed[j] == y.
    
    Return the minimum cost to convert the string source to the string target using any number of operations. 
    If it is impossible to convert source to target, return -1.
    
    Note that there may exist indices i, j such that original[j] == original[i] and changed[j] == changed[i].
    
    Input: source = "abcd", target = "acbe", 
    original = ["a","b","c","c","e","d"], 
    changed = ["b","c","b","e","b","e"], cost = [2,5,5,1,2,20]
    Output: 28
    Explanation: To convert the string "abcd" to string "acbe":
    - Change value at index 1 from 'b' to 'c' at a cost of 5.
    - Change value at index 2 from 'c' to 'e' at a cost of 1.
    - Change value at index 2 from 'e' to 'b' at a cost of 2.
    - Change value at index 3 from 'd' to 'e' at a cost of 20.
    The total cost incurred is 5 + 1 + 2 + 20 = 28.
    It can be shown that this is the minimum possible cost.
    
    
    Approach - 
    1. Build an adjacency list for nodes 'a' to 'z' containing pair of dest and weights.
    2. create a 2d array to distance of every node to every other node.
    3. Fill the array using Dijkstra's algo.
    4. iterate over source and fetch min dist form array. If it is INF, return -1
    
    */

    private static long INF = Long.MAX_VALUE / 4;
    
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {

        int n = original.length;

        // array of list of pairs for adjacency list
        List<Pair<Character, Integer>> adj[] = new ArrayList[26];

        // build the list
        for (int i = 0; i < n; i++) {
            int u = original[i] - 'a';
            char v = changed[i];
            int w = cost[i];

            if (adj[u] == null) {
                adj[u] = new ArrayList<>();
            }

            adj[u].add(new Pair<>(v, w));
        }

        // arrray to store the dist of each node with every other node
        long distFromAllSrc[][] = new long[26][26];

        Arrays.stream(distFromAllSrc).forEach(row -> Arrays.fill(row, INF));

        // fill the array using Dijkstra's algo
        for (int i = 0; i < 26; i++) {
            distFromAllSrc[i] = getMinDistFromSrcToDest((char)(i + 97), adj);
        }

        long ans = 0l;

        // iterate over the source string and add the cost
        for (int i = 0; i < source.length(); i++) {
            int u = source.charAt(i) - 'a';
            int v = target.charAt(i) - 'a';

            long currDist = distFromAllSrc[u][v];
            if (currDist == INF) {
                return -1l;
            }
            ans += currDist;
        }

        return ans;
    }
    

    private long[] getMinDistFromSrcToDest(char src, List<Pair<Character, Integer>> adj[]) {

        // array to store min dist to every node from source initially filled with INF
        long distFromSrc[] = new long[26];

        Arrays.fill(distFromSrc, INF);

        // pq for level order sorted on basis of min weight
        PriorityQueue<Pair<Character, Long>> pq = new PriorityQueue<>(
                (a, b) -> {
                    if (a.weight != b.weight) {
                        return Long.compare(a.weight, b.weight);
                    }

                    return Long.compare(a.node, b.node);
            }
        );

        // add source with 0 distance in pq and mark its dist in array = 0
        pq.add(new Pair<>(src, 0l));
        distFromSrc[src - 'a'] = 0;

        while (!pq.isEmpty()) {
            Pair<Character, Long> curr = pq.poll();

            int ind = curr.node - 'a';
            long dist = curr.weight;

            // if the node does not have any child, continue
            if (adj[ind] == null) {
                continue;
            }

            // for each child on curr node, check if its dist[child] > child weight + currDis
            // if true, update its distance in dist array and add the pair in queue
            for (Pair<Character, Integer> child : adj[ind]) {
                if (distFromSrc[(int)(child.node - 'a')] > dist + child.weight) {
                    pq.add(new Pair<>(child.node, dist + child.weight));
                    distFromSrc[(int)(child.node - 'a')] = dist + child.weight;
                }
            }
        }

        // return the dist array
        return distFromSrc;

    }
    
    private static class Pair<K, V> {
        K node;
        V weight;

        Pair(K node, V weight) {
            this.node = node;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        String source = "abcd";
        String target = "acbe";
        char original[] = { 'a', 'b', 'c', 'c', 'e', 'd' };
        char changed[] = { 'b', 'c', 'b', 'e', 'b', 'e' };
        int cost[] = { 2, 5, 5, 1, 2, 20 };

        MinCostToConvertString obj = new MinCostToConvertString();

        System.out.println(obj.minimumCost(source, target, original, changed, cost));
    }
    
}
