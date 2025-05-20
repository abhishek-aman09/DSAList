package Graph.DisjointSet;

public class DisjointSet {

    private int[] parent;
    private int[] rank;
    

    public DisjointSet(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Simple find method
    public int find(int node) {
        if (parent[node] == node)
            return node;

        return find(parent[node]);
    }

    // Find with path compression, make node direct child
    // of its  repersentative.
    public int findByPathCompression(int node) {
        if (parent[node] == node)
            return node;

        return parent[node] = findByPathCompression(parent[node]);
    }
    
    // Simple union
    public void union(int nodeA, int nodeB) {
        int repA = find(nodeA);
        int repB = find(nodeB);

        if (repA == repB)
            return;

        parent[repA] = repB;
    }

    // Union by rank to minimize height of the tree
    public void unionByRank(int nodeA, int nodeB) {
        int repA = findByPathCompression(nodeA);
        int repB = findByPathCompression(nodeB);

        if (repA == repB)
            return;
        
        if (rank[repA] > rank[repB]) {
            parent[repB] = repA;
        } else if (rank[repB] > rank[repA]) {
            parent[repA] = repB;
        } else {
            // if rank(height) of both tree are same
            // make any node the rep of other and increase
            // its rank by one.
            parent[repA] = repB;
            rank[repB]++;
        }
    }

}
