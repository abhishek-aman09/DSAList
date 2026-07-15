package Google.Heaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class EmployeeFreeTime {

    /*
    https://www.naukri.com/code360/problems/employee-free-time_1171181?leftPanelTabValue=SUBMISSION
    
    There are ‘N’ Problem Setters in Coding Ninjas, Each of them has a unique id between 0 to N-1. A Problem Setter works in multiple non-overlapping time intervals during a day.
    Formally, A Problem Setter having id ‘i’ works in ‘Ki’ non-overlapping intervals of the form [T1, T2], [T3, T4] ... [T(2ki-2), T(2ki-1)], where Ti is in between [0, 10^8] and Ti <= T(i+1). A day in Coding ninjas start from time 0 and end at time 10^8 (both inclusive).
    You are given ‘N’ sorted lists of non-overlapping intervals, where the ith list gives a schedule (list of intervals in which the problem setter works) of a Problem Setter having id ‘i’. 
    Your task is to find a sorted list of non-overlapping intervals in which all problem setters are free. 
    If there are multiple possible such lists, output the list which is minimum in length.
    Note :
    1. In sorted order interval [T1, T2] comes before [T3, T4] if either T1 < T3, or (T1 == T3  and T2 < T4).
    2. An interval [T1, T2] represents, time T1, T1+1, T1+2, ... T2, i.e all integers between T1, T2 both T1 and T2 inclusive.  
    3. For simplicity, we represent the list of intervals in a 1D array where every two numbers show an interval, i.e list [1, 3, 5, 7, 9, 11] represent intervals [1, 3], [5, 7] and [9, 11] 
    4. It is guaranteed that there will be at least one interval where all problem setters are free.
    Example :
    Let suppose there are 3 problem setters, their working intervals are represented by the following list of lists, ‘Schedules’: [[1, 2, 5, 6], [1,2], [5, 10]], where the ith interval of a setter is represented by 2*i and (2*i+1)th integer in their respective list,  I.e. Problem Setter with an id 0, works in the intervals [1, 2], [5, 6]. Problem Setter with an id 1, work in the interval  [1,2]. Problem Setter with id 2, works in the interval [5, 10], 
    In this example, the time intervals where setter 0 is free are [0, 0], [3, 4], [7, 10^8]
    And the time intervals where setter 1 is free are [0, 0], [3, 10^8].
    And the time intervals where setter 2 is free are [0, 4], [11, 10^8].
    We can clearly observe that time intervals, where all 3 setters are free are, [0, 0], [3, 4], and [11, 10^8].  
    Thus we should output a list [0, 0, 3, 4, 11, 10^8] that represents these lists in 1D array form as described in notes. It can be shown easily, that this is the minimum possible length list of intervals representing common free time.
    
    Sample Input 1 :
    2
    1
    1
    10 20
    3
    2
    1 2 5 6
    1
    1 2
    1
    5 10
    Sample Output 1 :
    0 9 21 100000000
    0 0 3 4 11 100000000
    Explanation Of Sample Input 1 :
    Test case 1:
    There is only one problem setter, who is busy during an interval [10, 20].  A day in coding ninjas is given by interval [0, 10^8], thus he will be free between [0, 9] and [21, 10^8]
    
    approach :
    1. put all interval in heap and perform merge overlapping intervals, and look for a free slot
    
    2. fix heap size to k, put only one interval from each list, process it and then enter next.
    
    
    
    */

    private static int maxLength = (int)1e8;

    public static ArrayList<Integer> findFreeIntervals(ArrayList<ArrayList<Integer>> schedules) {

        ArrayList<Integer> freeSlots = new ArrayList<>();

        // create a priority queue sorted on start time and end time in preference
        Queue<int[]> occupiedSlots = new PriorityQueue<>(
                (a, b) -> {
                    if (a[0] != b[0]) {
                        return Integer.compare(a[0], b[0]);
                    }

                    return Integer.compare(a[1], b[1]);
                });

        // put each pair in the list
        for (ArrayList<Integer> list : schedules) {
            for (int i = 0; i < list.size() - 1; i += 2) {
                occupiedSlots.offer(new int[] { list.get(i), list.get(i + 1) });
            }
        }

        // initialize freeStart and end 
        int freeStart = 0;
        int freeEnd = occupiedSlots.isEmpty() ? maxLength : occupiedSlots.peek()[0] - 1;

        while (!occupiedSlots.isEmpty()) {

            int currEnd = occupiedSlots.poll()[1];

            // check if we have a free slot, if yes, add it
            if (freeEnd >= freeStart) {
                freeSlots.add(freeStart);
                freeSlots.add(freeEnd);
            }

            // run a loop for overlapping interavals
            while (!occupiedSlots.isEmpty() && occupiedSlots.peek()[0] < currEnd) {
                currEnd = Integer.max(occupiedSlots.peek()[1], currEnd);
                occupiedSlots.poll();
            }

            // compute next freestart and end
            freeStart = currEnd + 1;
            freeEnd = occupiedSlots.isEmpty() ? maxLength : occupiedSlots.peek()[0] - 1;

        }

        // if last end time was less than maxLength, we insert the right half remaining

        if (freeStart <= maxLength) {
            freeSlots.add(freeStart);
            freeSlots.add(maxLength);
        }

        return freeSlots;

    }

    public static ArrayList<Integer> findFreeIntervalsI(ArrayList<ArrayList<Integer>> schedules) {

        ArrayList<Integer> freeSlots = new ArrayList<>();

        // create a priority queue sorted on start time and end time in preference
        // structure of items will be array of [startTime, endTime, indexNum, indexPointer]
        Queue<IntervalStructure> occupiedSlots = new PriorityQueue<>(
            (a, b) -> {
                if (a.startTime != b.startTime) {
                    return Integer.compare(a.startTime, b.startTime);
                }

                return Integer.compare(a.endTime, b.endTime);
        });

        // only push one interval from each list with index number and current iterator
        int index = 0;
        for (ArrayList<Integer> list : schedules) {

            if (list.isEmpty()) {
                continue;
            }

            occupiedSlots.offer(new IntervalStructure(list.get(0), list.get(1), index++, 0 ));
        }

        // initialize freeStart and end 
        int freeStart = 0;
        int freeEnd = occupiedSlots.isEmpty() ? maxLength : occupiedSlots.peek().startTime - 1;

        while (!occupiedSlots.isEmpty()) {

            // poll the current interval
            IntervalStructure curr = occupiedSlots.poll();

            // check if we have any more intervals in the list. 
            ArrayList<Integer> currList = schedules.get(curr.indexNum);
            if (curr.it + 3 < currList.size()) {
                occupiedSlots.offer(new IntervalStructure ( currList.get(curr.it + 2), currList.get(curr.it + 3),
                        curr.indexNum, curr.it + 2 ));
            }

            // check for a free slot
            if (freeEnd >= freeStart) {
                freeSlots.add(freeStart);
                freeSlots.add(freeEnd);
            }

            // run a loop for overlapping interavals
            while (!occupiedSlots.isEmpty() && occupiedSlots.peek().startTime < curr.endTime) {

                curr.endTime = Integer.max(occupiedSlots.peek().endTime, curr.endTime);
                IntervalStructure top = occupiedSlots.poll();

                // check if we have any more intervals in the list. 
                ArrayList<Integer> topList = schedules.get(top.indexNum);
                if (top.it + 3 < topList.size()) {
                    occupiedSlots.offer(new IntervalStructure ( topList.get(top.it + 2),
                            topList.get(top.it + 3), top.indexNum, top.it + 2 ));
                }
            }

            freeStart = curr.endTime + 1;
            freeEnd = occupiedSlots.isEmpty() ? maxLength : occupiedSlots.peek().startTime - 1;

        }

        // if last end time was less than maxLength, we insert the right half remaining

        if (freeStart <= maxLength) {
            freeSlots.add(freeStart);
            freeSlots.add(maxLength);
        }

        return freeSlots;
    }
    
    private static class IntervalStructure {
        int startTime;
        int endTime;
        int indexNum;
        int it;

        public IntervalStructure(int startTime, int endTime, int indexNum, int it) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.indexNum = indexNum;
            this.it = it;
        }
    }
    
    public static void main(String[] args) {
        // --- Test Case 1 ---
        // Emp 1: [1, 2], [5, 6] | Emp 2: [1, 3] | Emp 3: [4, 10]
        ArrayList<ArrayList<Integer>> schedule1 = new ArrayList<>();
        schedule1.add(new ArrayList<>(Arrays.asList(1, 2, 5, 6)));
        schedule1.add(new ArrayList<>(Arrays.asList(1, 2)));
        schedule1.add(new ArrayList<>(Arrays.asList(5, 10)));

        ArrayList<Integer> res1 = findFreeIntervalsI(schedule1);
        System.out.println("Test Case 1: " + res1); 
        // Expected: [3, 4]

        // --- Test Case 2 ---
        // Emp 1: [1, 3], [6, 7] | Emp 2: [2, 4] | Emp 3: [2, 5, 9, 12]
        ArrayList<ArrayList<Integer>> schedule2 = new ArrayList<>();
        schedule2.add(new ArrayList<>(Arrays.asList(1, 3, 6, 7)));
        schedule2.add(new ArrayList<>(Arrays.asList(2, 4)));
        schedule2.add(new ArrayList<>(Arrays.asList(2, 5, 9, 12)));

        ArrayList<Integer> res2 = findFreeIntervalsI(schedule2);
        System.out.println("Test Case 2: " + res2); 
        // Expected: [5, 6, 7, 9]
    }
    
    
}
