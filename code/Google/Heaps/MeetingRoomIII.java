package Google.Heaps;

import java.util.Arrays;
import java.util.PriorityQueue;


public class MeetingRoomIII {

     // https://leetcode.com/problems/meeting-rooms-iii/description/
    /*
    You are given an integer n. There are n rooms numbered from 0 to n - 1.
    
    You are given a 2D integer array meetings where meetings[i] = [starti, endi] 
    means that a meeting will be held during the half-closed time interval [starti, endi). 
    All the values of starti are unique.
    
    Meetings are allocated to rooms in the following manner:
    
    Each meeting will take place in the unused room with the lowest number.
    If there are no available rooms, the meeting will be delayed until a room becomes free. 
    The delayed meeting should have the same duration as the original meeting.
    When a room becomes unused, meetings that have an earlier original start time should be given the room.
    Return the number of the room that held the most meetings. If there are multiple rooms, 
    return the room with the lowest number.
    
    A half-closed interval [a, b) is the interval 
    between a and b including a and not including b.
    
    n = 4
    meetings = [[18,19],[3,12],[17,19],[2,13],[7,10]]
    Output = 0
    
    Approach : Have two pq, used and unused rooms.
    used sorted on basis of lowest end time.
    sort the array on basis of start time.
    iterate the meetings, at begining of each iteration, push all the rooms
    in the used heap with end time less than equal to curr start.
    
    first check if we have any unused room, if yes, assign it.
    If no, the top of used queue will serve the meet but it has to wait.
    So update the end time of current meet as per the waiting time and insert
    in the used queue.
    increase freq of room used in both cases.
    
    At last check the most freq.
    
    */
    
    public int mostBooked(int numOfMeetingRooms, int[][] meetings) {

        int n = meetings.length;

        // heap to store all unused rooms sorted by room number
        PriorityQueue<Pair<Integer,Long>> unusedRooms = new PriorityQueue<>(
            (a,b) -> a.roomNumber - b.roomNumber
        );

        // heap to store all used rooms, sorted by end time
        PriorityQueue<Pair<Integer,Long>> usedRooms = new PriorityQueue<>(
            (a,b) -> {
                if(!b.endTime.equals(a.endTime)) {
                    return Long.compare(a.endTime, b.endTime);
                }

                return a.roomNumber - b.roomNumber;
            }
        );

        // array to count frequency of meetings in each room
        int roomFreq[] = new int[numOfMeetingRooms];

        // sort the array on basis of their start time
        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);

        // push all rooms to unused heap
        for (int i = 0; i < numOfMeetingRooms; i++) {
            unusedRooms.add(new Pair<>(i, 0l));
        }
        
        // iterate through all the rooms
        for (int i = 0; i < n; i++) {

            int currStart = meetings[i][0];
            long currEnd = meetings[i][1];

            // before each itration, check for all room in used queue
            // which can be freed and push them into unused queue
            while (!usedRooms.isEmpty() && usedRooms.peek().endTime <= currStart) {
                Pair<Integer, Long> top = usedRooms.poll();
                unusedRooms.add(new Pair<>(top.roomNumber, 0l));
            }

            if (!unusedRooms.isEmpty()) { // Next check for any unused room.
                Pair<Integer, Long> top = unusedRooms.poll();
                top.endTime = currEnd;
                roomFreq[top.roomNumber]++;
                usedRooms.add(new Pair<>(top.roomNumber, top.endTime));
            } else {
                Pair<Integer, Long> top = usedRooms.poll();
                currEnd += (top.endTime - currStart);
                roomFreq[top.roomNumber]++;
                top.endTime = currEnd;
                usedRooms.add(new Pair<>(top.roomNumber, top.endTime));
            } // If all rooms are occupied, The curr meet will wait and will be
              // help in the room having least end time. But, the end time of the 
              // meeting will be pushed. So, update the end time of curr meet 
              // accordingly and push it back into the used rooms heap.
            
        }
        
        int ans = 0;
        int maxMeet = 0;
        for (int i = 0; i < numOfMeetingRooms; i++) {
            if (roomFreq[i] > maxMeet) {
                maxMeet = roomFreq[i];
                ans = i;
            }
        }

        return ans;
    }

    private static class Pair<K, V> {
        K roomNumber;
        V endTime;

        Pair(K roomNumber, V endTime) {
            this.roomNumber = roomNumber;
            this.endTime = endTime;
        }
    }

    public static void main(String[] args) {
        
        MeetingRoomIII obj = new MeetingRoomIII();

        int arr[][] = { { 18, 19 }, { 3, 12 }, { 17, 19 }, { 2, 13 }, {7, 10} };

        System.out.println(obj.mostBooked(4, arr));
    }
}
