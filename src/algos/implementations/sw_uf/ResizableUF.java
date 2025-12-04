package algos.implementations.sw_uf;

/**
 * Dynamically resizable Union–Find.
 *
 * Supports:
 *  - find(int)
 *  - ensureCapacity(int)
 *
 * Used exclusively by SW_UF, where labels grow gradually.
 */
public final class ResizableUF {

    private int[] parent;
    private short[] rank;
    private int capacity;

    public ResizableUF(int initialCapacity) {
        if ( initialCapacity < 2 ) initialCapacity = 2;

        capacity = initialCapacity;
        parent   = new int[initialCapacity];
        rank     = new short[initialCapacity];

        for (int i = 0; i < initialCapacity; i++) {
            parent[i] = i;
        }
    }

    /**
     * Ensures arrays can address label IDs up to `needed`.
     */
    public void ensureCapacity(int needed) {
        if ( needed < capacity ) return;

        int newCap = Math.max(needed + 1, capacity * 2);

        int[] newParent = new int[newCap];
        short[] newRank = new short[newCap];

        System.arraycopy(parent, 0, newParent, 0, capacity);
        System.arraycopy(rank, 0, newRank, 0, capacity);

        for (int i = capacity; i < newCap; i++) {
            newParent[i] = i;
            newRank[i] = 0;
        }

        parent = newParent;
        rank = newRank;
        capacity = newCap;
    }

    public int find(int x) {
        int root = x;

        while ( root != parent[root] ) { root = parent[root]; }

        while (x != root) {
            int p = parent[x];
            parent[x] = root;
            x = p;
        }

        return root;
    }

    /**
     * Union by rank.
     */
    public void union(int a, int b) {
        int ra = find(a);
        int rb = find(b);

        if (ra == rb) return;

        if      (rank[ra] < rank[rb]) { parent[ra] = rb; }
        else if (rank[ra] > rank[rb]) { parent[rb] = ra; }
        else                          { parent[rb] = ra; rank[ra]++; }
    }
}
