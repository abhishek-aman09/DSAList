package DynamicProgramming.PartitionDP;

import java.util.Arrays;

public class EvaluateBooleanTrue {
    
    // https://www.geeksforgeeks.org/problems/boolean-parenthesization5610/1

    /*
    You are given a boolean expression s containing
    'T' ---> true
    'F' ---> false 
    and following operators between symbols
    &   ---> boolean AND
    |   ---> boolean OR
    ^   ---> boolean XOR
    Count the number of ways we can parenthesize the expression so that the value of expression evaluates to true.
    
    Input: s = "T|T&F^T"
    Output: 4
    Explaination: The expression evaluates to true in 4 ways: ((T|T)&(F^T)), (T|(T&(F^T))), (((T|T)&F)^T) and (T|((T&F)^T)).
    
    Approach : This is a classic problem of partition dp. k only iterates over
    the binary operator i.e. only odd indexes. 
    Base cases : if i == j i.e only char is there, we check what we need to count num of ways we find true or false and then return accordingly.
    
    Q. why do we need to find number of ways we can obtain false.
    A. because total num of ways depend on it as F|T is true and F^F is true.
    
    
    */

    static int countWays(String s) {
        
        int n = s.length();

        int dp[][][] = new int[n + 1][n + 1][2];

        for (int grid[][] : dp) {
            for (int row[] : grid) {
                Arrays.fill(row, -1);
            }
        }

        return helper(s, 0, n - 1, 1, n, dp);


    }
    
    static int helper(String s, int i, int j, int isTrue, int n, int dp[][][]) {

        // BASE CASE 1: Invalid / Empty range
        // If indices cross, there is no expression to evaluate -> 0 ways.
        if (i > j) {
            return 0;
        }

        // BASE CASE 2: Single Operand (e.g., "T" or "F")
        // When i == j, no operators exist. We check whether this single literal
        // satisfies the requested boolean target (isTrue == 1 vs isTrue == 0).
        if (i == j) {
            if (isTrue == 1) {
                return s.charAt(i) == 'T' ? 1 : 0;
            } else {
                return s.charAt(i) == 'F' ? 1 : 0;
            }
        }

        // MEMOIZATION CHECK:
        // dp[i][j][isTrue] caches the total valid parenthesizations
        // of substring s[i...j] that evaluate to 'isTrue'.
        if (dp[i][j][isTrue] != -1) {
            return dp[i][j][isTrue];
        }

        int numOfWays = 0;

        // SPLIT LOOP:
        // Operators are strictly located at odd steps between operands.
        // 'k' represents the LAST operator evaluated in the range [i, j].
        // This splits the expression into:
        //   Left operand:  s[i ... k - 1]
        //   Operator:      s[k]
        //   Right operand: s[k + 1 ... j]
        for (int k = i + 1; k < j; k += 2) {

            // Solve all 4 sub-possibilities independently:
            // How many ways can the left sub-expression evaluate to True / False?
            // How many ways can the right sub-expression evaluate to True / False?
            int leftTrue   = helper(s, i, k - 1, 1, n, dp);
            int leftFalse  = helper(s, i, k - 1, 0, n, dp);
            int rightTrue  = helper(s, k + 1, j, 1, n, dp);
            int rightFalse = helper(s, k + 1, j, 0, n, dp);

            // COMBINATORICS:
            // Use the product rule (LeftWays * RightWays) matching truth tables:

            if (s.charAt(k) == '|') {
                // OR (|):
                // True cases:  (F | T), (T | F), (T | T)
                // False cases: (F | F)
                if (isTrue == 1) {
                    numOfWays += (leftFalse * rightTrue) + (leftTrue * rightFalse) + (leftTrue * rightTrue);
                } else {
                    numOfWays += (leftFalse * rightFalse);
                }  
            } else if (s.charAt(k) == '&') {
                // AND (&):
                // True cases:  (T & T)
                // False cases: (F & T), (T & F), (F & F)
                if (isTrue == 1) {
                    numOfWays += (leftTrue * rightTrue);
                } else {
                    numOfWays += (leftFalse * rightTrue) + (leftTrue * rightFalse) + (leftFalse * rightFalse);
                }
            } else {
                // XOR (^):
                // True cases:  opposite operands -> (T ^ F), (F ^ T)
                // False cases: matching operands -> (F ^ F), (T ^ T)
                if (isTrue == 1) {
                    numOfWays += (leftTrue * rightFalse) + (leftFalse * rightTrue);
                } else {
                    numOfWays += (leftFalse * rightFalse) + (leftTrue * rightTrue);
                }
            }
        }

        return dp[i][j][isTrue] = numOfWays;
    }
    
    public static void main(String[] args) {
        System.out.println(countWays("T^F|F"));
    }

}
