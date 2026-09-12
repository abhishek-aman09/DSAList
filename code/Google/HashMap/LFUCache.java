package Google.HashMap;

import java.util.HashMap;
import java.util.Map;

class LFUCache {

    Map<Integer, Dll> map;
    int capacity;
    Dll head;
    Dll tail;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Dll(-1);
        tail = new Dll(-1);
        head.right = tail;
        tail.left = head;
    }
    
    public int get(int key) {

        if (map.containsKey(key)) {
            Dll node = map.get(key);
            
            delete(node);
            addLast(key, node);

            return node.val;
        } else {
            return -1;
        }
        
    }
    
    public void put(int key, int value) {

        if (map.containsKey(key)) {
            Dll node = map.get(key);
            delete(node);
            node.val = value;
            addLast(key, node);
        } else {
            if (capacity == 0) {
                delete(head.right);
            }

            addLast(key, new Dll(value));
        }

    }

    private void delete(Dll node) {
        Dll left = node.left;
        Dll right = node.right;

        left.right = right;
        right.left = left;

        map.remove(node.val);
        capacity++;
    }

    private void addLast(int key, Dll node) {

        Dll last = tail.left;
        tail.left = node;
        last.right = node;
        node.right = tail;
        node.left = last;

        if(capacity > 0) {
            capacity--;
        }
        map.put(key, node);
    }

    private void addFirst(int key, Dll node) {
        Dll first = head.right;
        head.right = node;
        node.left = head;
        first.left = node;
        node.right = first;

        if(capacity > 0) {
            capacity--;
        }
        map.put(key, node);
    }
    
    private static class Dll {
        int val;
        Dll left;
        Dll right;

        Dll(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }
}
