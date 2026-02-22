package String;

public class MinimumDelToBalanceString {
    // https://leetcode.com/problems/minimum-deletions-to-make-string-balanced/description/

    /*
    You are given a string s consisting only of characters 'a' and 'b'​​​​.
    
    You can delete any number of characters in s to make s balanced. 
    s is balanced if there is no pair of indices (i,j) 
    such that i < j and s[i] = 'b' and s[j]= 'a'. basically sorted.
    
    Return the minimum number of deletions needed to make s balanced.
    
    
    Input: s = "bbaaaaabb"
    Output: 2
    Explanation: The only solution is to delete the first two characters.
    
    approach : go though each char, if char is B increased its frequency. else 
        1. if freq of B is > 0, decrease it and increment the deletionCounter.
    
    Why it works?
    The deletionCounter will run min of 'a' after 'b' or 'b' before 'a' for any set of characters.
    
    
    */
    public int minimumDeletions(String s) {

        int ans = 0;
        int countB = 0;

        for(char ch : s.toCharArray()) {
            if(ch == 'b') countB++;

            else {
                if(countB > 0) {
                    ans++;
                    countB--;
                }
            }
        }

        return ans;
    }
    

    public static void main(String[] args) {
        MinimumDelToBalanceString obj = new MinimumDelToBalanceString();

        System.out.println(obj.minimumDeletions("baababbaabbaaabaabbabbbabaaaaaabaabababaaababbb"));
    }
    
}
