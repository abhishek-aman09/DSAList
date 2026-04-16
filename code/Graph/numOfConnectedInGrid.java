package Graph;

public class numOfConnectedInGrid {


    public int findCircleNum(int[][] isConnected) {

        int n = isConnected.length;

        boolean isVisited[][] = new boolean[n][n];

        int ans = 0;

        for(int i = 0; i < n; i++) {
            boolean sameRow = false;
            for(int j = 0; j < n; j++) {
                if(!isVisited[i][j] && isConnected[i][j] == 1) {

                    traverseGraph(i, j, n, isConnected, isVisited);
                    if(!sameRow) {
                        ans++;
                    }
                    sameRow = true;
                }
            }
        }

        return ans;
        
    }


    private void traverseGraph(int i, int j, int n, int graph[][], boolean isVisited[][]) {


        isVisited[i][j] = true;

        for(int k = 0; k < n; k++) {

            if (!isVisited[i][k] && graph[i][k] == 1) {
                isVisited[i][k] = true;
                traverseGraph(k, i, n, graph, isVisited);
            }
        }


    }


    public static void main(String[] args) {
        numOfConnectedInGrid obj = new numOfConnectedInGrid();

        int grid[][] = { { 1, 0, 0, 1 }, { 0, 1, 1, 0 }, { 0, 1, 1, 1 }, { 1, 0, 1, 1 } };

        System.out.println(obj.findCircleNum(grid));
    }
    
}
