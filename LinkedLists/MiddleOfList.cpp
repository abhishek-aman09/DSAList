#include<bits/stdc++.h>

using namespace std;


struct ListNode {
    int val;
    ListNode *next;
    ListNode() : val(0), next(nullptr) {}
    ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
};


ListNode* middleNode(ListNode* head) {

    ListNode *slow = head, *fast = head;

    if(!slow or !slow->next)
        return head;
    
    while(fast and fast->next) {
        slow = slow->next;
        fast = fast->next->next;
    }

    return slow;
}