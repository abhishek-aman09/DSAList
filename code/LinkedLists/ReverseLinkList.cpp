#include<bits/stdc++.h>

using namespace std;


struct ListNode {
    int val;
    ListNode *next;
    ListNode() : val(0), next(nullptr) {}
    ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
};

ListNode* reverseListRecursively(ListNode* curr, ListNode* prev) {
    
    if(!curr)
        return prev;

    ListNode *next = curr->next;
    curr->next = prev;
    return reverseListRecursively(next, curr);
}

ListNode* reverseList(ListNode* head) {

    ListNode *curr = head, *prev = NULL;

    // Reversing the list using recursion
    head = reverseListRecursively(head, prev);

    
    // Reverse a linked list using iteration
        while(curr) {
        ListNode *next = curr->next;
        curr->next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
 