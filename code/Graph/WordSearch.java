package Graph;

public class WordSearch {

    // https://leetcode.com/problems/word-search/description/

    /*
    Given an m x n grid of characters board and a string word, return true if word exists in the grid.
    
    The word can be constructed from letters of sequentially adjacent cells, 
    where adjacent cells are horizontally or vertically neighboring. 
    The same letter cell may not be used more than once.
    
    Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
    Output: true
    
    Approach : check if first char of word matches with current character
    of the grid, run dfs to check.
    */
    
    public boolean exist(char[][] board, String word) {

        int n = board.length;
        int m = board[0].length;

        

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                boolean isVisited[][] = new boolean[n][m];
                if (board[i][j] == word.charAt(0)) {
                    boolean result = dfs(i, j, board, 0, word, isVisited);
                    if (result) {
                       return true; 
                    }
                    
                }
            }
        }
        
        return false;

    }
    
    private boolean dfs(int i, int j, char[][] board, int ind, String str, boolean[][] isvisited) {

        int n = board.length;
        int m = board[0].length;

        // if string has been iterated, return true
        if (ind >= str.length()) {
            return true;
        }

        // validation check
        if (i >= n || j >= m || i < 0 || j < 0 || isvisited[i][j]) {
            return false;
        }

        // character check
        if (board[i][j] != str.charAt(ind)) {
            return false;
        }

        isvisited[i][j] = true;

        boolean left = dfs(i, j - 1, board, ind + 1, str, isvisited);
        boolean right = dfs(i, j + 1, board, ind + 1, str, isvisited);
        boolean top = dfs(i - 1, j, board, ind + 1, str, isvisited);
        boolean down = dfs(i + 1, j, board, ind + 1, str, isvisited);

        // make it false for recursive call for another subtree.
        isvisited[i][j] = true;

        return left || right || top || down;
    }
    
    public static void main(String[] args) {
        char [][]board = {
            {'A','B','C','E'},
            {'S','F','E','S'},
            {'A','D','E','E'}
        };

        WordSearch obj = new WordSearch();

        System.out.println(obj.exist(board, "ABCESEEEFS"));
    }
    

}
