#include<bits/stdc++.h>

using namespace std;


struct ListNode {
    int val;
    ListNode *next;
    ListNode() : val(0), next(nullptr) {}
    ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
};

bool hasCycle(ListNode *head) {

    if(!head or !head->next)
        return false;

    ListNode *slow = head->next, *fast = head->next->next;

    while(slow and fast and fast->next) {
        if(slow == fast)
            break;
        slow = slow->next;
        fast = fast->next->next;
    }

    if(fast and slow == fast)
        return true;

    return false;
}