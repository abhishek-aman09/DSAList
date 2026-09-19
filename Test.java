import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Test {

    public static class Parent {
        public static void greet() {
            System.out.println("greet from parent");
        }
    }
    

    public static class Child extends Parent {
        public static void greet() {
            System.out.println("greet from child");
        }
    }

    public boolean carPooling(int[][] trips, int capacity) {

        int n = trips.length;

        Arrays.sort(trips, ((a, b) -> Integer.compare(a[1], b[1])));

        int currPassengers = 0;

        Queue<int[]> leastOutTime = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));

        for (int i = 0; i < n; i++) {
            int[] curr = trips[i];

            leastOutTime.offer(curr);
            currPassengers += curr[0];

            while (!leastOutTime.isEmpty() && leastOutTime.peek()[2] <= curr[1]) {
                currPassengers -= leastOutTime.poll()[0];
            }

            if (currPassengers > capacity) {
                return false;
            }
        }

        return true;
        
    }

    public static void main(String[] args) {
        
        Test obj = new Test();

        Parent ob = new Child();
        
        ob.greet();

        System.out.println(38.2 + 34.9);

    }
    
}
