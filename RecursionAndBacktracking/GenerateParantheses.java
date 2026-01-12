package RecursionAndBacktracking;

import java.util.ArrayList;
import java.util.List;

public class GenerateParantheses {
    
    public List<String> generateParenthesis(int n) {

        List<String> ans = new ArrayList<>();

        helper(n, 0, 0, "", ans);

        return ans;

    }
    
    private void helper(int n, int openingBraces, int closingBraces, String curr, List<String> ans) {

        if (openingBraces > n || closingBraces > n) {
            return;
        }

        if (closingBraces == n) {
            ans.add(curr);
            return;
        }

        if (openingBraces == closingBraces) {
            helper(n, openingBraces + 1, closingBraces, addCharToString(curr, '('), ans);
        } else {
            helper(n, openingBraces + 1, closingBraces, addCharToString(curr, '('), ans);
            helper(n, openingBraces, closingBraces + 1, addCharToString(curr, ')'), ans);
        }
    }

    private String addCharToString(String str, char ch) {
        StringBuilder stringBuilder = new StringBuilder(str);

        stringBuilder.append(ch);

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        GenerateParantheses obj = new GenerateParantheses();

        List<String> result = obj.generateParenthesis(3);

        result.stream().forEach(el -> {
            System.out.println(el);
        });
    }
    
}
