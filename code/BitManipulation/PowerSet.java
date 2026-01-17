package BitManipulation;

import java.util.ArrayList;
import java.util.List;

public class PowerSet {
    
    public static List<String> AllPossibleStrings(String s) {

        int n = s.length();

        List<String> ans = new ArrayList<>();

        int poweSize = (int) Math.pow(2, n); // or poweSize = 1 << n;

        for (int i = 1; i < poweSize; i++) {
            int binI = i;

            int j = 0;
            StringBuilder temp = new StringBuilder();

            while (binI > 0) {
                int bit = binI & 1;
                if (bit == 1) {
                    temp.append(s.charAt(j));
                }
                j++;
                binI = binI >> 1;
            }
            ans.add(temp.toString());
        }

        ans.sort((s1, s2)  -> s1.compareTo(s2));

        return ans;
    }
    
    public static void main(String[] args) {
        List<String> res = AllPossibleStrings("abc");

        for (String str : res) {
            System.out.print(str + "  ");
        }
        System.out.println();
    }

}
