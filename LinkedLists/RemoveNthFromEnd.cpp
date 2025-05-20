#include<bits/stdc++.h>

using namespace std;


struct ListNode {
    int val;
    ListNode *next;
    ListNode() : val(0), next(nullptr) {}
    ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
};



ListNode* removeNthFromEnd(ListNode* head, int n) {

    ListNode *curr = head, *prev = head;
    bool isDeletingLastNode = n == 1;

    while(n--) {
        curr = curr->next;
    }

    // Condition to delete the head node
    if(!curr)
        return head->next;

    while(curr->next) {
        prev = prev->next;
        curr = curr->next;
    }

    if(!isDeletingLastNode)
        prev->next = prev->next->next;
    else
        prev->next = NULL;

    return head;
}