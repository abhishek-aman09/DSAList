package StackAndQueue;

import java.util.Stack;

public class RemoveKDigits {
    /*
     * Given a string s consisting of digits and an integer k, remove k digits from the string to form the smallest possible number.
    Input: s = "780278921", k = 4
    Output: "221"
    */

    public String removeKdigits(String num, int k) {
        StringBuilder ans = new StringBuilder();

        int n = num.length();
        Stack<Character> stk = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (k > 0 && !stk.empty() && stk.peek() > num.charAt(i)) {
                k--;
                stk.pop();
            }
            stk.add(num.charAt(i));
        }

        while (k > 0 && !stk.empty()) {
            k--;
            stk.pop();
        }

        
        while (!stk.empty()) {
            ans.append(stk.peek());
            stk.pop();
        }

        while (ans.length() > 0 && ans.charAt(ans.length() - 1) == '0') {
            ans.deleteCharAt(ans.length() - 1);
        }

        return ans.length() > 0 ? ans.reverse().toString() : "0";
    }
}
