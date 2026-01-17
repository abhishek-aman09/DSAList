package BitManipulation;

import java.util.ArrayList;
import java.util.List;

public class GrayCode {
    /*
     * An n-bit gray code sequence is a sequence of 2n integers where:
    
    Every integer is in the inclusive range [0, 2n - 1],
    The first integer is 0,
    An integer appears no more than once in the sequence,
    The binary representation of every pair of adjacent integers differs by exactly one bit, and
    The binary representation of the first and last integers differs by exactly one bit.
    Given an integer n, return any valid n-bit gray code sequence.
    
    https://leetcode.com/problems/gray-code/description/
    
    Approach : pattern is 0 1 , 2+1(3), 2+0(2), 4+2(6), 4+3(7), 4+1(5), 4+0(4), 8+4, 8+5, 8+7,...
    
     */
    static public List<Integer> grayCode(int n) {

        List<Integer> ans = new ArrayList<>();
        ans.add(0);
        if (n == 0) {
            return ans;
        }

        ans.add(1);

        for (int i = 2; i <= n; i++) {

            int basePowerOfTwo = 1 << (i - 1);
            int j = ans.size() - 1;

            while (j >= 0) {
                int el = ans.get(j);
                el += basePowerOfTwo;
                ans.add(el);
                j--;
            }
        }

        return ans;

    }

    
    public static void main(String[] args) {
        List<Integer> ans = grayCode(5);

        for (int el : ans) {
            System.out.print(el + "  ");
        }
    }
}
