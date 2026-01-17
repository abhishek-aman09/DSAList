package BinaryTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class BottomViewOfTree {

    static class Node
    {
        int data; //data of the node
        int hd; //horizontal distance of the node
        Node left, right; //left and right references

        // Constructor of tree node
        public Node(int key)
        {
            data = key;
            hd = Integer.MAX_VALUE;
            left = right = null;
        }
    }
    

    public ArrayList<Integer> bottomView(Node root) {
        
        ArrayList<Integer> ans = new ArrayList<>();

        // Treemap sort by keys, LinkedHashMap maintain insetion order
        // Hashmap is random
        Map<Integer, Integer> levelWithNode = new TreeMap<>();

        // Queue is an interface, LinkedList is concrete
        Queue<NodeWithLevel> queue = new LinkedList<>();

        queue.add(new NodeWithLevel(root, 0));

        while (!queue.isEmpty()) {
            NodeWithLevel curr = queue.poll();

            // add if(!levelWithNode.containsKey(curr.level)) for top view
            levelWithNode.put(curr.level, curr.node.data);

            if (curr.node.left != null) {
                queue.add(new NodeWithLevel(curr.node.left, curr.level - 1));
            }
            if (curr.node.right != null) {
                queue.add(new NodeWithLevel(curr.node.right, curr.level + 1));
            }
        }

        // int keys : map.keySet() to iterate over keys.
        for (int value : levelWithNode.values()) {
            ans.add(value);
        }

        return ans;

    }

    static class NodeWithLevel {
        Node node;
        int level;

        NodeWithLevel(Node node, int level) {
            this.node = node;
            this.level = level;
        }
    }
    
}
