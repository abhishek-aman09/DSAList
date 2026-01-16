package Array;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MeetingRoom {
    
    public int mostBooked(int numOfMeetingRooms, int[][] meetings) {

        int n = meetings.length;

        PriorityQueue<Pair<Integer,Integer>> meetRoomQueue = new PriorityQueue<>(
            (a,b) -> {
                if(b.endTime != a.endTime) {
                    return a.endTime - b.endTime;
                }

                return a.roomNumber - b.roomNumber;
            }
        );

        int numOfMeetInRoom[] = new int[numOfMeetingRooms];

        Arrays.sort(meetings, (a, b) -> a[0] - b[0]);

        for (int i = 0; i < numOfMeetingRooms; i++) {
            meetRoomQueue.add(new Pair<>(i, 0));
        }
        
        for (int i = 0; i < n; i++) {

            int currStart = meetings[i][0];
            int currEnd = meetings[i][1];

            int k = numOfMeetingRooms;
            while (k > 0 && meetRoomQueue.peek().endTime <= currStart) {
                Pair<Integer, Integer> top = meetRoomQueue.poll();
                top.endTime = 0;
                meetRoomQueue.add(top);
                k--;
            }

            Pair<Integer, Integer> topMeetRoom = meetRoomQueue.poll();

            numOfMeetInRoom[topMeetRoom.roomNumber]++;
            if (currStart < topMeetRoom.endTime) {
                currEnd += (topMeetRoom.endTime - currStart);
                currStart = topMeetRoom.endTime;
            }
            topMeetRoom.endTime = currEnd;

            meetRoomQueue.add(new Pair<>(topMeetRoom.roomNumber, topMeetRoom.endTime));

        }
        
        int ans = 0;
        int maxMeet = 0;
        for (int i = 0; i < numOfMeetingRooms; i++) {
            if (numOfMeetInRoom[i] > maxMeet) {
                maxMeet = numOfMeetInRoom[i];
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
        
        MeetingRoom obj = new MeetingRoom();

        int arr[][] = { { 18, 19 }, { 3, 12 }, { 17, 19 }, { 2, 13 }, {7, 10} };

        System.out.println(obj.mostBooked(4, arr));
    }
    
}
