#include<bits/stdc++.h>

using namespace std;


struct ListNode {
    int val;
    ListNode *next;
    ListNode() : val(0), next(nullptr) {}
    ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
};

ListNode *detectCycle(ListNode *head) {

    if(!head or !head->next)
        return NULL;

    ListNode *slow = head->next, *fast = head->next->next;
    // Code to check cycle in the list
    while(fast and fast->next) {
        if(slow == fast)
            break;

        slow = slow->next;
        fast = fast->next->next;
    }

    if(!fast or slow != fast)
        return NULL;

    slow = head;

    // Code block to find the starting point of the loop
    while (slow != fast)
    {
        slow = slow->next;
        fast = fast->next;
    }

    return slow;
}