package String;

import java.util.ArrayList;
import java.util.List;

public class LeetCode1404 {
    
    public int numSteps(String s) {

        List<Character> str = new ArrayList<>(s.chars().mapToObj(c -> (char)c).toList());

        int ans = 0;

        while (str.size() > 1) {
            int len = str.size();
            if (str.get(len - 1) == '0') {     
                str.remove(len - 1);
            } else {
                str = addOneToCharArray(str);
            }
            ans++;
        }

        return ans;

    }
    
    private List<Character> addOneToCharArray(List<Character> str) {

        int balance = 1;

        StringBuilder temp = new StringBuilder();

        for (int i = str.size() - 1; i >= 0; i--) {
            int curr = (str.get(i) - '0') + balance;
            temp.append((char) ((curr % 2) + '0'));
            balance = curr / 2;
        }

        if (balance == 1) {
            temp.append('1');
        }

        return new ArrayList<>(temp.reverse().toString().chars().mapToObj(c -> (char)c).toList());
    }
    
    public static void main(String[] args) {
        LeetCode1404 obj = new LeetCode1404();

        System.out.println(obj.numSteps("1"));
    }

}
