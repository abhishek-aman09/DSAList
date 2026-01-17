package RecursionAndBacktracking;

import java.util.ArrayList;

public class RatInAMaze {

    public boolean isValidPath(ArrayList<ArrayList<Integer>> mat, int n, int x, int y, boolean[][] isVisited) {

        if (x < 0 || x >= n || y < 0 || y >= n) {
            return false;
        }

        if (isVisited[x][y] || mat.get(x).get(y) == 0) {
            return false;
        }

        return true;
    }
    
    public static String addChar(String str, char ch) {
        StringBuffer sb = new StringBuffer(str);
        sb.append(ch);

        return sb.toString();
    }

    public static String removeChar(String str) {
        StringBuffer sb = new StringBuffer(str);
        sb.setLength(sb.length() - 1);

        return sb.toString();
    }

    public void maze(ArrayList<ArrayList<Integer>> mat, int n, int x, int y, String currPath,
            ArrayList<String> ans, boolean[][] isVisited) {
        if (x < 0 || x >= n || y < 0 || y >= n) {
            return;
        }

        isVisited[x][y] = true;

        // reached the target. save path to ans
        if(x == n - 1 && y == n - 1) {
            String temp = "";
            for (int i = 0; i < currPath.length(); i++) temp = addChar(temp, currPath.charAt(i));
            ans.add(temp);
        }
    
        // check if right is valid path
        if (isValidPath(mat, n, x, y + 1, isVisited)) {
            currPath = addChar(currPath, 'R');
            maze(mat, n, x, y + 1, currPath, ans, isVisited);
            currPath = removeChar(currPath);
        }

        // check if left is valid path
        if (isValidPath(mat, n, x, y - 1, isVisited)) {
            currPath = addChar(currPath, 'L');
            maze(mat, n, x, y - 1, currPath, ans, isVisited);
            currPath = removeChar(currPath);
        }

        // check if down is valid path
        if (isValidPath(mat, n, x + 1, y, isVisited)) {
            currPath = addChar(currPath, 'D');
            maze(mat, n, x + 1, y, currPath, ans, isVisited);
            currPath = removeChar(currPath);
        }

        // check if up is valid path
        if (isValidPath(mat, n, x - 1, y, isVisited)) {
            currPath = addChar(currPath, 'U');
            maze(mat, n, x - 1, y, currPath, ans, isVisited);
            currPath = removeChar(currPath);
        }

        // mark the current as unvisited
        isVisited[x][y] = false;
    }
    
    public ArrayList<String> findPath(ArrayList<ArrayList<Integer>> mat) {
        ArrayList<String> ans = new ArrayList<>();
        String currPath = "";
        int n = mat.size();
        boolean[][] isVisited = new boolean[n][n];

        maze(mat, n, 0, 0, currPath, ans, isVisited);

        ans.sort((a, b) -> {
            return (a.compareTo(b));
        });

        return ans;
    }
    
    public static void main(String[] args) {
        
    }


}
