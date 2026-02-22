package Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloneGraph {
    // https://leetcode.com/problems/clone-graph/description/
    /*
    Given a reference of a node in a connected undirected graph.
    
    Return a deep copy (clone) of the graph.
    
    Each node in the graph contains a value (int) and a list (List[Node]) of its neighbors.
    
    class Node {
    public int val;
    public List<Node> neighbors;
    }
    
    
    Test case format:
    
    For simplicity, each node's value is the same as the node's index (1-indexed). 
    For example, the first node with val == 1, the second node with val == 2, and so on. 
    The graph is represented in the test case using an adjacency list.
    
    An adjacency list is a collection of unordered lists used to represent a finite graph. 
    Each list describes the set of neighbors of a node in the graph.
    
    The given node will always be the first node with val = 1. 
    You must return the copy of the given node as a reference to the cloned graph.
    
    approach : instead of boolean isVisited, we maintain a map<int,node> isVisited.
    if child if current is visited, we will be able to fetch it using map.
    
    */

    public Node cloneGraph(Node node) {
        Map<Integer, Node> map = new HashMap<>();
        return helper(node, map);
    }

    private Node helper( Node node, Map<Integer, Node> map) {
        if(node == null || map.containsKey(node.val)) {
            return null;
        }

        Node root = new Node(node.val);
        map.put(root.val, root); 


        for(Node child : node.neighbors) {
            // if child is visited, add it to copy root node child
            if(map.containsKey(child.val)) {
                root.neighbors.add(map.get(child.val));
            } else { // else, call recursively of the child, and add the return value in list
                Node result = helper(child, map);
                root.neighbors.add(result);
            }
            
        }

        return root;
    }


    private static class Node {
        public int val;
        public List<Node> neighbors;
        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }
        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }
    
}
