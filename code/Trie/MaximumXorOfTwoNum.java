package Trie;

public class MaximumXorOfTwoNum {

    // https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/description/

    /*
    Given an integer array nums, return the maximum result of 
    nums[i] XOR nums[j], where 0 <= i <= j < n.
    
    Input: nums = [3,10,5,25,2,8]
    Output: 28
    Explanation: The maximum result is 5 XOR 25 = 28.
    
    Approach : Create a Trie with children array of size 2 (0 and 1).
    Insert all the numbers into the trie in their 32 bit format, starting
    with 31st bit.
    
    Iterate the loop, for each num, check if correspoding xor bit exist in
    trie, if yes, take that path, if no take the current bit path.
    
    Save that path index in an array. The array will give you the num which 
    will have the max xor with current number. 

    Run a loop to form the xor and check maxXor.
    */
    public int findMaximumXOR(int[] nums) {

        int n = nums.length;

        if (n == 1) {
            return 0;
        }

        TrieNode root = new TrieNode();

        // Insert all the numbers into the trie in their 32 bit format
        for (int i = 0; i < n; i++) {
            int curr = nums[i];

            int k = 31;
            TrieNode node = root;

            while (k >= 0) {
                int bit = (curr >> k) & 1;

                if (node.root[bit] == null) {
                    node.root[bit] = new TrieNode();
                }
                node = node.root[bit];
                k--;
            }

            node.isEnd = true;
        }

        int maxXor = 0;

        // For each num, try to follow the path that will give you max xor
        for (int i = 0; i < n; i++) {
            int curr = nums[i];

            TrieNode node = root;
            int k = 31;
            int arr[] = new int[32];
            while (k >= 0) {
                int bit = (curr >> k) & 1;
                if (node.root[bit ^ 1] != null) {
                    arr[k] = bit ^ 1;
                    node = node.root[bit ^ 1];
                } else {
                    arr[k] = bit;
                    node = node.root[bit];
                }
                k--;
            }

            // arr will give you the num. calculate the xor it result with 
            // current number.
            int temp = 0;
            int mul = 1;
            for (k = 0; k < 32; k++) {
                temp += ((arr[k] ^ ((curr >> k) & 1)) * mul);
                mul *= 2;
            }

            if (temp > maxXor) {
                maxXor = temp;
            }
        }
        
        return maxXor;

    }
    

    private static class TrieNode {
        boolean isEnd;
        TrieNode root[];

        TrieNode() {
            isEnd = false;

            root = new TrieNode[2];
        }
    }


    public static void main(String[] args) {
        MaximumXorOfTwoNum obj = new MaximumXorOfTwoNum();

        int arr[] = {3,10,5,25,2,8};
        System.out.println(obj.findMaximumXOR(arr));
    }
}
