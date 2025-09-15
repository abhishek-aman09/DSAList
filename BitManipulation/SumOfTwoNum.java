package BitManipulation;

public class SumOfTwoNum {
    // https://leetcode.com/problems/sum-of-two-integers/?envType=problem-list-v2&envId=bit-manipulation

    public int getSum(int a, int b) {

        while (b != 0) {
            // Carry now contains common set bits of a and b
            int carry = (a & b) << 1;

            // Sum of bits of a and b where at least one of the bits is not set
            a = a ^ b;

            // Carry is added to a in next iteration
            b = carry;
        }
        return a;

    }
    
    public static void main(String[] args) {
        int a = 6;
        int b = 13;

        SumOfTwoNum obj = new SumOfTwoNum();

        System.out.println(obj.getSum(a, b));
    }
}
