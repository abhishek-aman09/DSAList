# Complete Guide to Binary Lifting, $K$-th Ancestor, and LCA

**Press cmd + shift + v for preview**

Binary lifting is a dynamic programming technique on trees that precomputes ancestors at distances that are powers of two ($1, 2, 4, 8, \dots, 2^j$). It reduces linear tree traversals from $O(N)$ down to **$O(\log N)$** per query, with an initial **$O(N \log N)$** preprocessing phase.

---

## 1. Core Intuition: Powers of Two

Every positive integer $K$ has a unique representation as a sum of powers of 2 (its binary representation):

$$13 = 8 + 4 + 1 = 2^3 + 2^2 + 2^0 \quad (\text{binary: } 1101_2)$$

If you need to walk $K$ steps up a tree:
* **Naive pointer chasing:** Take 1 step, $K$ times $\implies O(K)$ time per query.
* **Binary lifting:** Inspect the set bits of $K$. For $K = 13$, jump $2^0 = 1$ step, then $2^2 = 4$ steps, then $2^3 = 8$ steps $\implies$ only **3 jumps instead of 13**.

Because any integer $K \le N$ has at most $\lfloor \log_2 N \rfloor + 1$ set bits, any ancestor can be reached in **at most $O(\log N)$ jumps**.

---

## 2. Table Definition and the Recurrence Relation

### Table Definition
Define a 2D table `up[node][j]`:
> `up[node][j]` stores the **$2^j$-th ancestor** of `node`.

* `node`: vertex identifier ($0 \dots N - 1$).
* `j`: the power-of-two exponent ($0 \le j < \text{LOG}$, where $\text{LOG} = \lceil \log_2 N \rceil + 1$).

### The Recurrence Relation
Every power of two can be split into two equal halves:

$$2^j = 2^{j-1} + 2^{j-1}$$

To jump $2^j$ steps up from `node`:
1. First, take $2^{j-1}$ steps up to reach an intermediate node: `mid = up[node][j - 1]`.
2. From `mid`, take another $2^{j-1}$ steps up: `up[mid][j - 1]`.

$$\mathbf{up[node][j] = up[\,up[node][j - 1]\,][j - 1]}$$

```text
                up[node][j]
                     ^
                     |   2^(j - 1) steps
                     |
               up[node][j - 1]   (= mid)
                     ^
                     |   2^(j - 1) steps
                     |
                   node
```

### Base Case ($j = 0$)
Since $2^0 = 1$, the $2^0$-th ancestor is simply the immediate parent of the node:

$$\mathbf{up[node][0] = parent[node]}$$

If a jump leads past the root node, `up[node][j] = -1` (or a sentinel root node).

---

## 3. Preprocessing the DP Table

To ensure that `up[mid][j - 1]` is already computed before evaluating `up[node][j]`, **the outer loop MUST iterate over powers of two ($j$), and the inner loop over all nodes**:

```java
// Total columns needed: 2^(LOG - 1) >= N
int LOG = 32 - Integer.numberOfLeadingZeros(n);
int[][] up = new int[n][LOG];

// 1. Base cases (j = 0): 2^0 = direct parent
for (int i = 0; i < n; i++) {
    up[i][0] = parent[i];
}

// 2. DP propagation: powers of 2 from 1 to LOG - 1
for (int j = 1; j < LOG; j++) {
    for (int i = 0; i < n; i++) {
        int intermediate = up[i][j - 1];
        if (intermediate == -1) {
            up[i][j] = -1; // Out of bounds past root
        } else {
            up[i][j] = up[intermediate][j - 1];
        }
    }
}
```

* **Preprocessing Time:** $O(N \log N)$
* **Preprocessing Space:** $O(N \log N)$

---

## 4. Problem 1: Finding the $K$-th Ancestor in $O(\log N)$

To find the $K$-th ancestor of a node:
1. Examine the binary representation of $K$.
2. For every bit $j$ that is set (`(k & (1 << j)) != 0`), jump `node = up[node][j]`.
3. If `node == -1` at any point, the tree does not have $K$ ancestors above this node.

```java
public int getKthAncestor(int node, int k) {
    for (int j = 0; j < LOG; j++) {
        if ((k & (1 << j)) != 0) {
            node = up[node][j];
            if (node == -1) {
                return -1; // Jumped past the root
            }
        }
    }
    return node;
}
```

* **Query Time:** $O(\log K) \le O(\log N)$ (checking at most $\approx \log_2 N$ bits).

---

## 5. Problem 2: Lowest Common Ancestor (LCA) in $O(\log N)$

The Lowest Common Ancestor ($\text{LCA}$) of two nodes $u$ and $v$ is the deepest node that is an ancestor of both $u$ and $v$.

### The Algorithm: 3 Distinct Phases

```text
               [ LCA(u, v) ]           <-- Final target: up[u][0]
                 /        \
              [ u' ]     [ v' ]        <-- Where u and v land after Phase 2
                |          |
                |          |
              [ u ]      [ v ]         <-- Equalized depths (after Phase 1)
                |
             [ u_orig ]                <-- Starting position (deeper)
```

#### Phase 1: Leveling (Equalizing Depths)
* Assume without loss of generality that `depth[u] >= depth[v]`.
* Compute the difference: $\Delta d = \text{depth}[u] - \text{depth}[v]$.
* Lift $u$ upward by $\Delta d$ steps using the $K$-th ancestor logic:
  ```java
  int diff = depth[u] - depth[v];
  for (int j = 0; j < LOG; j++) {
      if ((diff & (1 << j)) != 0) {
          u = up[u][j];
      }
  }
  ```
* Now, $u$ and $v$ are at the exact same depth in the tree.

#### Early Exit Check
* If $u == v$, then $v$ was an ancestor of $u$ from the start. Return $u$.

#### Phase 2: Simultaneous Lifting
With $u$ and $v$ at the same depth, we want to lift them upward **without overshooting the LCA**:
* Iterate $j$ in descending order: $j = \text{LOG} - 1, \dots, 1, 0$.
* If `up[u][j] != up[v][j]`:
  * Jumping $2^j$ steps does **not** reach or cross a common ancestor; both are still distinct nodes below the LCA.
  * We make the jump: `u = up[u][j]` and `v = up[v][j]`.
* If `up[u][j] == up[v][j]`:
  * Jumping $2^j$ steps reaches **or overshoots** the LCA (they land on the same node). Because this could be a higher common ancestor (not the lowest), **we do not jump**.
* By iterating from the highest power down to $0$, $u$ and $v$ converge until they sit **directly beneath their LCA**.

#### Result
After the loop finishes, both nodes are direct children of the LCA.
$$\mathbf{\text{LCA}(u, v) = up[u][0]}$$

---

## 6. Complete Java Reference Implementation

```java
import java.util.*;

public class TreeLCA {
    private final int n;
    private final int LOG;
    private final int[][] up;
    private final int[] depth;

    public TreeLCA(int n, List<Integer>[] adj, int root) {
        this.n = n;
        this.LOG = 32 - Integer.numberOfLeadingZeros(n);
        this.up = new int[n][LOG];
        this.depth = new int[n];

        // Step 1: DFS to establish depth and direct parents (up[node][0])
        dfs(root, root, 0, adj);

        // Step 2: Build the binary lifting DP table
        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                int intermediate = up[i][j - 1];
                up[i][j] = up[intermediate][j - 1];
            }
        }
    }

    private void dfs(int curr, int parent, int d, List<Integer>[] adj) {
        depth[curr] = d;
        up[curr][0] = parent;

        for (int next : adj[curr]) {
            if (next != parent) {
                dfs(next, curr, d + 1, adj);
            }
        }
    }

    /**
     * Finds the K-th ancestor of node in O(log K)
     */
    public int getKthAncestor(int node, int k) {
        for (int j = 0; j < LOG; j++) {
            if ((k & (1 << j)) != 0) {
                node = up[node][j];
            }
        }
        return node;
    }

    /**
     * Finds the Lowest Common Ancestor of u and v in O(log N)
     */
    public int getLca(int u, int v) {
        // Ensure u is the deeper node
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        // Phase 1: Lift u to the same depth as v
        int depthDiff = depth[u] - depth[v];
        for (int j = 0; j < LOG; j++) {
            if ((depthDiff & (1 << j)) != 0) {
                u = up[u][j];
            }
        }

        // If v was an ancestor of u, u and v are now the same node
        if (u == v) {
            return u;
        }

        // Phase 2: Lift u and v simultaneously from highest power to lowest
        for (int j = LOG - 1; j >= 0; j--) {
            if (up[u][j] != up[v][j]) {
                u = up[u][j];
                v = up[v][j];
            }
        }

        // Both nodes are now direct children of the LCA
        return up[u][0];
    }

    /**
     * Calculates the number of edges between node u and node v in O(log N)
     */
    public int getDistance(int u, int v) {
        int lca = getLca(u, v);
        return depth[u] + depth[v] - 2 * depth[lca];
    }
}
```

---

## 7. Complexity Summary

| Operation | Time Complexity | Space Complexity | Explanation |
| :--- | :--- | :--- | :--- |
| **DFS Tree Traversal** | $O(N)$ | $O(N)$ | Visits each node and edge once to record `depth` and direct parent. |
| **Table Construction** | $O(N \log N)$ | $O(N \log N)$ | Computes $N \times \log N$ entries with $O(1)$ lookups. |
| **$K$-th Ancestor Query** | $O(\log K)$ | $O(1)$ | Checks each bit of integer $K$. |
| **LCA Query** | $O(\log N)$ | $O(1)$ | Phase 1 takes $\le \log N$ jumps; Phase 2 inspects $\log N$ powers. |
| **Tree Distance Query** | $O(\log N)$ | $O(1)$ | $\text{dist}(u, v) = \text{depth}[u] + \text{depth}[v] - 2 \cdot \text{depth}[\text{LCA}(u, v)]$. |

---

## 8. Essential Applications

1. **Tree Path Queries (Sum / Min / Max along a path):**
   * Extend the DP table to track values: `maxWeight[node][j] = Math.max(maxWeight[node][j - 1], maxWeight[up[node][j - 1]][j - 1])`.
   * Any associative property (Sum, Min, Max, GCD) over paths between $u$ and $v$ can be queried in $O(\log N)$ alongside binary lifting.
2. **Dynamic Distance Queries:**
   * Compute shortest distance between any pair of nodes in $O(\log N)$ using the formula:
     $$\text{dist}(u, v) = \text{depth}[u] + \text{depth}[v] - 2 \cdot \text{depth}[\text{LCA}(u, v)]$$
3. **Prefix Frequency / State Aggregations (e.g., LeetCode 2846):**
   * Use prefix counts from root to node: $\text{count}(u \to v) = \text{pref}[u] + \text{pref}[v] - 2 \cdot \text{pref}[\text{LCA}(u, v)]$.