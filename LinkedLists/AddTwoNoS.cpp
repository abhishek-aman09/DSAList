#include<bits/stdc++.h>

using namespace std;


struct ListNode {
    int val;
    ListNode *next;
    ListNode() : val(0), next(nullptr) {}
    ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
};

ListNode* reverseList(ListNode* curr, ListNode* prev) {
    if(!curr) return prev;

    ListNode *next = curr->next;
    curr->next = prev;
    return reverseList(next, curr);
}

ListNode* addTwoNumbers(ListNode* l1, ListNode* l2) {

    // Do this if numbers are represented in list in straight order
    l1 = reverseList(l1, NULL);
    l2 = reverseList(l2, NULL);

    ListNode *result = NULL, *curr = NULL;

    int carry = 0, sum = 0;

    while(l1 and l2) {

        sum = l1->val + l2->val + carry;
        carry = sum / 10;
        sum = sum % 10;

        if(result == NULL) {
            result = new ListNode(sum);
            curr = result;
        } else {
            curr->next = new ListNode(sum);
            curr = curr->next;
        }
        l1 = l1->next;
        l2 = l2->next;
    }

    while(l1) {
        sum = l1->val + carry;
        carry = sum / 10;
        sum = sum % 10;
        curr->next = new ListNode(sum);
        curr = curr->next;
        l1 = l1->next;
    }

    while(l2) {
        sum = l2->val + carry;
        carry = sum / 10;
        sum = sum % 10;
        curr->next = new ListNode(sum);
        curr = curr->next;
        l2 = l2->next;
    }

    if(carry)
        curr->next = new ListNode(carry);

    return result;
}