package DynamicProgramming.TwoDimentional;

import java.util.Arrays;

/*
 * https://www.naukri.com/code360/problems/ninja-s-training_3621003?leftPanelTabValue=PROBLEM
 * 
 * Ninja is planing this ‘N’ days-long training schedule.
 * Each day, he can perform any one of these three activities.
 * (Running, Fighting Practice or Learning New Moves).
 * Each activity has some merit points on each day.
 * As Ninja has to improve all his skills, he can’t do the same activity in two consecutive days.
 * Can you help Ninja find out the maximum merit points Ninja can earn?
 * 
 * Input 
 * 3
    10 40 70
    20 50 80
    30 60 90

    Output

    210
    On the first day, Ninja will learn new moves and earn 70 merit points. 
    On the second day, Ninja will do fighting and earn 50 merit points. 
    On the third day, Ninja will learn new moves and earn 90 merit points. 
    The total merit point is 210 which is the maximum. 
    Hence, the answer is 210.
 */

public class NinjasTask {
    public static int ninjaTraining(int n, int points[][]) {

        int[][] dp = new int[n + 1][4];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], -1);
        }

        for (int i = 0; i <= 4; i++) {
            dp[0][i] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 5; j++) {
                
            }
        }

        return solve(n, points, 3, dp);
    }
    
    private static int solve(int n, int[][] points, int lastTask, int[][] dp) {
        if (n <= 0) {
            return 0;
        }

        if (dp[n][lastTask] != -1) {
            return dp[n][lastTask];
        }

        if (lastTask == 3) {
            return dp[n][lastTask] = Integer.max(
                    points[n - 1][0] + solve(n - 1, points, 0, dp),
                    Integer.max(
                            points[n - 1][1] + solve(n - 1, points, 1, dp),
                            points[n - 1][2] + solve(n - 1, points, 2, dp)));
        }

        if (lastTask == 2) {
            return dp[n][lastTask] = Integer.max(
                    points[n - 1][0] + solve(n - 1, points, 0, dp),
                    points[n - 1][1] + solve(n - 1, points, 1, dp));
        }

        if (lastTask == 1) {
            return dp[n][lastTask] = Integer.max(
                    points[n - 1][0] + solve(n - 1, points, 0, dp),
                    points[n - 1][2] + solve(n - 1, points, 2, dp));
        }

        return dp[n][lastTask] = Integer.max(points[n - 1][1] + solve(n - 1, points, 1, dp),
                points[n - 1][2] + solve(n - 1, points, 2, dp));

    }
    

    public static void main(String[] args) {
        int[][] points = {
            {94, 74, 84},
            {71,  4, 68},
            {70, 12, 17},
            { 7, 84, 58},
            {59, 69,  2},
            {57, 21, 62},
            {74, 54, 15},
            {15, 83, 49},
            {97, 70, 90},
            { 8, 71, 42}
        };

        System.out.println("Ninja's points are : " + ninjaTraining(10, points));
    }
}
