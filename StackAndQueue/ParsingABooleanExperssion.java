package StackAndQueue;

import java.util.Stack;

public class ParsingABooleanExperssion {

    // https://leetcode.com/problems/parsing-a-boolean-expression/description/

    /*
    A boolean expression is an expression that evaluates to either true or false. It can be in one of the following shapes:
    
    't' that evaluates to true.
    'f' that evaluates to false.
    '!(subExpr)' that evaluates to the logical NOT of the inner expression subExpr.
    '&(subExpr1, subExpr2, ..., subExprn)' that evaluates to the logical AND of the inner expressions subExpr1, subExpr2, ..., subExprn where n >= 1.
    '|(subExpr1, subExpr2, ..., subExprn)' that evaluates to the logical OR of the inner expressions subExpr1, subExpr2, ..., subExprn where n >= 1.
    Given a string expression that represents a boolean expression, return the evaluation of that expression.
    
    Approach : 
    push everything in stack except ')' and ','
    whenever we encounter ')', pop stack till you find '('
    store the popped string in a variable
    the char below '(' in the stack will be out operand 
    because it is given expression will be in operand(subExp) format only.
    
    create a helper method to evaluate the expressions each time and push 
    the corresponding char back into the stack.
    */
    public boolean parseBoolExpr(String expression) {

        int n = expression.length();

        Stack<Character> stk = new Stack<>();

        for (int i = 0; i < n; i++) {
            char ch = expression.charAt(i);

            if (ch == ',') {
                continue;
            }

            if (ch != ')') {
                stk.push(ch);
            } else {
                StringBuilder str = new StringBuilder();

                while(stk.peek() != '(') {
                    str.append(stk.pop());
                }
                stk.pop(); // remove the '('
                char operand = stk.pop(); // get the operand

                boolean result = helper(str.reverse().toString(), operand);
                stk.push(result ? 't' : 'f'); // push result back into stack
            }
        }

        return stk.pop() == 't';

    }
    
    private boolean helper(String exp, char operand) {
        int n = exp.length();

        if (operand == '!') {
            return (exp.charAt(0) != 't');
        }

        boolean result = exp.charAt(0) == 't';

        for (int i = 1; i < n; i++) {

            boolean curr = exp.charAt(i) == 't';

            switch (operand) {
                case '|':
                    result = result || curr;
                    break;
                case '&':
                    result = result && curr;
                    break;
                default:
                    break;
            }
        }

        return result;
    }
    
    public static void main(String[] args) {
        ParsingABooleanExperssion obj = new ParsingABooleanExperssion();

        System.out.println(obj.parseBoolExpr("|(f,f,f,t)"));
    }
    
}
