package StackAndQueue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class TaskScheduler {
    
    /*
    You are given an array of CPU tasks, each labeled with a letter from A to Z, and a number n. 
    Each CPU interval can be idle or allow the completion of one task. 
    Tasks can be completed in any order, but there's a constraint: 
    there has to be a gap of at least n intervals between two tasks with the same label.
    
    Return the minimum number of CPU intervals required to complete all tasks.
    
    Example 1:
    
    Input: tasks = ["A","A","A","B","B","B"], n = 2
    
    Output: 8
    
    Explanation: A possible sequence is: A -> B -> idle -> A -> B -> idle -> A -> B.
    
    After completing task A, you must wait two intervals before doing A again. 
    The same applies to task B. In the 3rd interval, neither A nor B can be done, so you idle. 
    By the 4th interval, you can do A again as 2 intervals have passed.
    
    Approach : count freq of each task and sord then in desc order. Put them
    into the queue with its freq and timer. poll the front, check timer of the 
    current. If it is less than curr timer, decrease freq and update counter. If
    it is more, adjust current timer and do the following.
    
    */

    public int leastInterval(char[] tasks, int interval) {


        int n = tasks.length;

        int[][] taskFreq = new int[26][2];

        for(int i = 0; i < n; i++) {
            int ind = (int)(tasks[i] - 'A');

            taskFreq[ind][0] = (int)tasks[i];
            taskFreq[ind][1]++;
        }


        Arrays.sort(taskFreq, (a, b) -> Integer.compare(b[1], a[1]));

        Queue<Task> taskQueue = new LinkedList<>();

        for(int i = 0; i < 26; i++) {
            if(taskFreq[i][1] > 0){
                taskQueue.add(new Task((char)taskFreq[i][0], taskFreq[i][1], 0));
            } 
        }

        int timer = 0;

        while(!taskQueue.isEmpty()) {
            Task curr = taskQueue.poll();

            if(curr.timer > timer) {
                timer = curr.timer;
            }

            curr.freq--;
            curr.timer = curr.timer + interval + 1;
            if(curr.freq > 0) {
                taskQueue.add(curr);
            }
            timer++;
        }

        return timer;
        
    }

    private static class Task {
        char ch;
        int freq;
        int timer;

        Task(char ch, int freq, int timer) {
            this.ch = ch;
            this.freq = freq;
            this.timer = timer;
        }
    }

}
