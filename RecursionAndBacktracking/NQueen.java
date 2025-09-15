package RecursionAndBacktracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NQueen {

     public static boolean isSafe(Character[][] board, int n, int x, int y) {
        // check row
        for(int i = 0; i < n; i++) if(board[x][i] != null) return false;

        // check column
        for(int i = 0; i < n; i++) if(board[i][y] != null) return false;

        // check left upper diagonal
        for(int i = x, j = y; i >= 0 && y >= 0; i--, j--) if(board[i][j] != null) return false;

        //check right lower diagonal
        for(int i = x, j = y; i < n && y < n; i++, j++) if(board[i][j] != null) return false;

        // check right upper diagonal
        for(int i = x, j = y; i >= 0 && y < n; i--, j++) if(board[i][j] != null) return false;

        //check left lower diagonal
        for(int i = x, j = y; i < n && y >= 0; i++, j--) if(board[i][j] != null) return false;

        return true;
    }

    public static String addChar(String str, char ch) {
        StringBuffer sb = new StringBuffer(str);
        sb.append(ch);

        return sb.toString();
    }

    public static void insertIntoList(Character[][] board, List<List<String>> ans, int n) {

        List<String> temp = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            String str = "";
            for(int j = 0; j < n; j++) {
                if(board[i][j] == null) str = addChar(str, '.');
                else str = addChar(str, 'Q');
            }
            temp.add(str);
        }

        ans.add(temp);
    }

    public static void nQueen(Character[][] board, int n, int x, int y, List<List<String>> ans) {
        if(x < 0 || x >= n || y < 0 || y >= n) return;

        if(isSafe(board, n, x, y)) {
            board[x][y] = 'Q';
            if(y == n - 1) insertIntoList(board, ans, n);

            // calling next column
            nQueen(board, n, 0, y + 1, ans);

            board[x][y] = null;

            //calling next el in the row
            nQueen(board, n, x + 1, y, ans);
        } else {
            //calling next el in the row
            nQueen(board, n, x + 1, y, ans);
        }
    }

    public static List<List<String>> solveNQueens(int n) {

        List<List<String>> ans = new ArrayList<>();
        Character[][] board = new Character[n][n];

        for(int i = 0; i < n; i++) Arrays.fill(board[i], null);

        nQueen(board, n, 0, 0, ans);
        return ans;

    }
    
}
