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
    if(!curr)
        return prev;

    ListNode *next = curr->next;
    curr->next = prev;
    return reverseList(next, curr);
}

bool isSameList(ListNode* listABegin, ListNode* listAEnd, ListNode* listBBegin, ListNode* listBEnd) {
    while(listABegin!=listAEnd and listBBegin != listBEnd) {
        if(listABegin->val != listBBegin->val)
            return false;

        listABegin = listABegin->next;
        listBBegin = listBBegin->next;
    }

    if(listABegin != listAEnd)
        return false;
    
    if(listBBegin != listBEnd)
        return false;

    return true;
}

bool isPalindrome(ListNode* head) {

    if(!head or !head->next)
        return true;

    ListNode *slow = head, *fast = head->next;

    //Finding the midpoint of the list
    while(fast and fast->next) {
        slow = slow->next;
        fast = fast->next->next;
    }

    //Breaking the list into two halves
    ListNode* next = slow->next;
    slow->next = NULL;
    next = reverseList(next, NULL);

    if(fast) {
        // Even case 1 2 3 3 2 1 
        return isSameList(head, NULL, next, NULL);
    }
    else
    {
        //Odd Case: 1 2 3 2 1
        return isSameList(head, slow, next, NULL);
    }
}