package Google.DP;

import java.util.Arrays;

public class StudentAttendanceRecordII {

    /*
    https://leetcode.com/problems/student-attendance-record-ii/description/
    
    An attendance record for a student can be represented as a string where each character signifies whether 
    the student was absent, late, or present on that day. The record only contains the following three characters:
    
    'A': Absent. 'L': Late. 'P': Present.
    Any student is eligible for an attendance award if they meet both of the following criteria:
    
    The student was absent ('A') for strictly fewer than 2 days total.
    The student was never late ('L') for 3 or more consecutive days.
    Given an integer n, return the number of possible attendance records of length n that make a student 
    eligible for an attendance award. The answer may be very large, so return it modulo 109 + 7.
    
    Input: n = 2
    Output: 8
    Explanation: There are 8 records with length 2 that are eligible for an award:
    "PP", "AP", "PA", "LP", "PL", "AL", "LA", "LL"
    Only "AA" is not eligible because there are 2 absences (there need to be fewer than 2).
    
    Approach : standard dp, we store states that for curr len, a student having n leaves and m late arrivals
    how many combinations can be there.
    */

    private static final int MOD = 1000000007;
    private static final int MAX_LATE_LIMIT = 2;
    private static final int MAX_ALLOWED_LEAVES = 1;
    
    public int checkRecord(int n) {

        long[][][] dp = new long[n][MAX_LATE_LIMIT + 1][MAX_ALLOWED_LEAVES + 1];

        Arrays.stream(dp).forEach(grid -> Arrays.stream(grid).forEach(row -> Arrays.fill(row , -1)));

        return (int)(getCount(MAX_ALLOWED_LEAVES, MAX_LATE_LIMIT, 0, n, dp) % MOD);

    }
    
    private long getCount(int leaveLeft, int lateArrivalLeft, int currLen, int n, long dp[][][]) {

        if (currLen == n) { // if we have reached length, return 1
            return 1;
        }

        if (dp[currLen][lateArrivalLeft][leaveLeft] != -1) {
            return dp[currLen][lateArrivalLeft][leaveLeft];
        }

        long present = getCount(leaveLeft, MAX_LATE_LIMIT, currLen + 1, n, dp); // if student is present on ith day
        // reset its late limit and callnext

        long takeLeave = 0l;
        if (leaveLeft > 0) { // if student can take leave, check for that
            takeLeave = getCount(leaveLeft - 1, MAX_LATE_LIMIT, currLen + 1, n, dp);
        }

        long lateArrived = 0l;
        if (lateArrivalLeft > 0) { // if student can arrive late, check for that
            lateArrived = getCount(leaveLeft, lateArrivalLeft - 1, currLen + 1, n, dp);
        }

        return dp[currLen][lateArrivalLeft][leaveLeft] = (takeLeave + present + lateArrived) % MOD; // sum it up and mod it

    }
    
    public static void main(String[] args) {
        StudentAttendanceRecordII obj = new StudentAttendanceRecordII();

        System.out.println(obj.checkRecord(100));
    }
}
