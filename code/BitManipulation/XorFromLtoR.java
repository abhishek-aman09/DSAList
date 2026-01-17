package BitManipulation;

public class XorFromLtoR {
    /*

    Catch - If you want to find xor for number from 1 to n. then,
    If n % 4 == 0, the XOR result is n itself.

    If n % 4 == 1, the XOR result is 1.

    If n % 4 == 2, the XOR result is n + 1.

    If n % 4 == 3, the XOR result is 0.

     */

    /*
    Input:
    L = 4, R = 8
    Output:
    8
    Explanation:
    4 ^ 5 ^ 6 ^ 7 ^ 8 = 8
    https://www.geeksforgeeks.org/problems/find-xor-of-numbers-from-l-to-r/1
     */

    int findXOR(int l, int r) {
        return getXorTillN(l - 1) ^ getXorTillN(r);
    }

    int getXorTillN(int n) {
        if(n % 4 == 0) {
            return n;
        }
        if(n % 4 == 1) {
            return 1;
        }
        if(n % 4 == 2) {
            return n + 1;
        }
        return 0;
    }
}
