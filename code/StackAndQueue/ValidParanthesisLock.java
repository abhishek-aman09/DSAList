package StackAndQueue;

import java.util.Stack;

public class ValidParanthesisLock {
    
    /*
    https://leetcode.com/problems/check-if-a-parentheses-string-can-be-valid/description/
    
    A parentheses string is a non-empty string consisting only of '(' and ')'. It is valid if any of the following conditions is true:
    
    It is ().
    It can be written as AB (A concatenated with B), where A and B are valid parentheses strings.
    It can be written as (A), where A is a valid parentheses string.
    You are given a parentheses string s and a string locked, both of length n. locked is a binary string consisting only of '0's and '1's. 
    For each index i of locked,
    
    If locked[i] is '1', you cannot change s[i].
    But if locked[i] is '0', you can change s[i] to either '(' or ')'.
    Return true if you can make s a valid parentheses string. Otherwise, return false.
    
    
    Input: s = "))()))", locked = "010100"
    Output: true
    Explanation: locked[1] == '1' and locked[3] == '1', so we cannot change s[1] or s[3].
    We change s[0] and s[4] to '(' while leaving s[2] and s[5] unchanged to make s valid.
    
    Approach :
    We need to maintai a stack for open braces and a stack for unlocked braces. while traversing the string, if the index is unlocked, put it into 
    unlocked. If it is open, put into open. If the index is closed, check if we can neuteralize it with any open or unlocked bracket. If not, return
    false.

    at the end, check if the remaining opening braces can be neutralized with the available unlocked. At last check if remaining unlocked are in even pair
    
    */

    public boolean canBeValid(String s, String locked) {

        Stack<Integer> open = new Stack<>();
        Stack<Integer> unlocked = new Stack<>();

        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            char lockStatus = locked.charAt(i);

            // if char is unlocked, put index in unlock stack
            if(lockStatus == '0') {
                unlocked.add(i);
            } else if(ch == '(') { // if it is an open bracket, put into open stack
                open.push(i);
            } else { // if it is a locked closed bracket
                if(!open.isEmpty()) { // check if we have any open bracket to deal this one
                    open.pop();
                } else if(!unlocked.isEmpty()) {// if now open brackets are there, check if we have any unlocked one to use
                    unlocked.pop();
                } else { // if both are unavailable, return false
                    return false;
                }
            }
        }

        while(!open.isEmpty()) {
            int lockPos = open.pop();

            if(!unlocked.isEmpty() && unlocked.peek() > lockPos) { // check if we have an unlock to the right of each remaining locked open brackt
                unlocked.pop();
            } else {
                return false;
            }
        }

        return unlocked.size() % 2 == 0; // At last, the remaining unlocked should be in pairs to form ()
    }
}
