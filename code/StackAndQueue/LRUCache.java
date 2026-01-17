package StackAndQueue;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    private int capacity;
    private DLL head;
    private DLL tail;
    private Map<Integer, DLL> nodeMap;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.head = new DLL(-1, -1);
        this.tail = new DLL(-1, -1);
        head.right = tail;
        tail.left = head;
        this.nodeMap = new HashMap<>();
    }
    
    public int get(int key) {

        if (nodeMap.get(key) == null) {
            return -1;
        }

        int val = nodeMap.get(key).val;
        
        deleteNode(key, val);
        insertAtHead(key, val);

        return nodeMap.get(key).val;
        
    }
    
    public void put(int key, int value) {

        if (nodeMap.get(key) != null) {
            deleteNode(key, nodeMap.get(key).val);
            insertAtHead(key, value);
            return;
        }

        if (capacity == 0) {
            deleteNode(tail.left.key, tail.left.val);
        }
        insertAtHead(key, value);
        capacity = capacity > 0 ? capacity - 1 : capacity;

    }

    private void deleteNode(int key, int val) {
        DLL curr = head;

        nodeMap.remove(key);

        while (curr != tail) {
            if (curr.val == val && curr.key == key) {
                curr.left.right = curr.right;
                curr.right.left = curr.left;
                break;
            }
            curr = curr.right;
        }
    }

    private void insertAtHead(int key, int val) {
        DLL temp = new DLL(key, val);

        nodeMap.put(key, temp);

        temp.left = head;
        temp.right = head.right;
        head.right = temp;
        temp.right.left = temp;
    }
    
    private class DLL {
        private int key;
        private int val;
        private DLL left;
        private DLL right;

        public DLL(int key, int val) {
            this.key = key;
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        // ["LRUCache","put","put","put","put","get","get"]
        // [[2],[2,1],[1,1],[2,3],[4,1],[1],[2]]
        cache.put(2, 1);
        cache.put(1, 1);
        cache.put(2, 3);
        cache.put(4, 1);
        System.out.println(cache.get(1));
        System.out.println(cache.get(2));
        
    }
}
