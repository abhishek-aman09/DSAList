#include<bits/stdc++.h>

using namespace std;

bool isSameTopParanthesis(char ch, stack<char> stk) {
    if(stk.empty())
        return false;

    if (ch == ']' and stk.top() != '[')
        return false;
    if (ch == '}' and stk.top() != '{')
        return false;
    if (ch == ')' and stk.top() != '(')
        return false;

    return true;
}

bool isValid(string s) {
    int length = s.size();

    stack<char> stk;

    for(char ch : s) {
        if(ch == '}' or ch == ']' or ch == ')') {
            if(!isSameTopParanthesis(ch, stk))
                return false;
            else
                stk.pop();
        } else
            stk.push(ch);
    }

    return stk.empty();
}