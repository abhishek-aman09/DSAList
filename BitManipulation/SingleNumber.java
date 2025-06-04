package BitManipulation;

public class SingleNumber {

    /*
     * iven an integer array nums where every element appears three times except for one,
     which appears exactly once. Find the single element and return it.
    
    You must implement a solution with a linear runtime complexity and use only constant extra space.
    https://leetcode.com/problems/single-number-ii/description/
    
    approach = count the bit at ith place for all elements, if no of bits % 3 is not one,
            number has that bit in it.
            
    
     */

    public static int elementThatAppearsOnce(int[] arr) {
        int ans = 0;

        for (int i = 0; i < 31; i++) {
            int setBit = 1 << i;
            int setBitCount = 0;


            for (int el : arr) {
                if ((setBit & el) > 0) {
                    setBitCount++;
                }
            }
            if (setBitCount % 3 != 0) {
                ans += setBit;
            }
        }

        return ans;
    }

    /*
    Given an integer array nums,
     in which exactly two elements appear only once and all the other elements appear exactly twice.
     Find the two elements that appear only once. You can return the answer in any order.
     https://leetcode.com/problems/single-number-iii/description/
     */

    public int[] singleNumber(int[] nums) {

        int xor = 0;

        for(int el : nums) {
            xor = xor ^ el;
        }

        // now the xor basically contains the xor of the two numbers that appear once.
        // where ever we have a 1 in the xor, that means the number has different set bit there.
        // lets find the first set bit of xor

        int firstSetBit = (xor & (xor - 1)) ^ xor;

        // now lets divide all the nums in two categories, first that have a set bit at firstSetBit position
        // and other one which does not.

        int xorOfSetBit = 0, xorOfBit = 0;

        for(int el : nums) {
            if((firstSetBit & el) > 0) {
                xorOfSetBit = xorOfSetBit ^ el;
            } else {
                xorOfBit = xorOfBit ^ el;
            }
        }

        int ans[] = {xorOfBit, xorOfSetBit};

        return ans;

    }


    public static void main(String[] args) {
        int arr[] = { 1, 3, 3, 3, 1, 1, 4 };

        System.out.println(elementThatAppearsOnce(arr));
    }
    
}
