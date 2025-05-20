#include<bits/stdc++.h>

using namespace std;


struct ListNode {
    int val;
    ListNode *next;
    ListNode() : val(0), next(nullptr) {}
    ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
};


ListNode *getIntersectionNode(ListNode *headA, ListNode *headB) {

    ListNode *currA = headA, *currB = headB;
    bool swapA = false, swapB = false;

    while(currA != currB) {
        if(currA == NULL){
            if(!swapA) {
                currA = headB;
                swapA = true;
            } else break;
        }
        if(currB == NULL) {
            if(!swapB) {
                currB = headA;
                swapB = true;
            } else break;
        }

        if(currA == currB)
            break;

        currA = currA->next;
        currB = currB->next;

    }


    return currA;
}