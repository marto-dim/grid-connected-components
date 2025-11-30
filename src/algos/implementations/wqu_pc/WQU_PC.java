package algos.implementations.wqu_pc;

import algos.ConnectedComponentsQuery;
import grid.interfaces.Grid;
import models.Point2D;

/**
 * Full-grid Weighted Quick Union with Path Compression.
 *
 * Loads the entire grid and supports:
 *  - count()
 *  - find(Point2D)
 *  - connected(Point2D, Point2D)
 *
 * Suitable for medium-sized grids (up to memory limits).
 */
public final class WQU_PC implements ConnectedComponentsQuery {

    private final Grid grid;
    private final int ROWS;
    private final int COLS;

    private final int[] parent;
    private final int[] size;
    private final int[] id;

    private int components;

    public WQU_PC(Grid grid) {
        this.grid = grid;
        ROWS = grid.rows();
        COLS = grid.cols();

        int totalCells = ROWS * COLS;

        parent = new int[totalCells];
        size   = new int[totalCells];
        id     = new int[totalCells];

        initializeIds();
        buildStructure();
    }

    @Override
    public int find(Point2D p) {
        int mapped = id[index(p.row(), p.col())];
        return mapped < 0 ? -1 : findRoot(mapped);
    }

    @Override
    public boolean connected(Point2D a, Point2D b) {
        int ra = find(a);
        int rb = find(b);
        return ra >= 0 && ra == rb;
    }

    @Override public int count() { return components; }

    // === Initialization ===
    private void initializeIds() {
        int next = 0;

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {

                if (grid.isMarked(row, col)) { id[index(row, col)] = next++; }
                else                         { id[index(row, col)] = -1;     }
            }
        }

        components = next;

        for (int i = 0; i < components; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    private void buildStructure() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {

                if ( !grid.isMarked(row, col) ) continue;

                int cur = id[index(row, col)];

                if ( col > 0 && grid.isMarked(row, col - 1) ) {
                    if ( union(cur, id[index(row, col - 1)]) ) components--;
                }

                if ( row > 0 && grid.isMarked(row - 1, col) ) {
                    if ( union(cur, id[index(row - 1, col)]) ) components--;
                }
            }
        }
    }

    // === Union-Find Core ===
    private int findRoot(int x) {
        int root = x;
        while (root != parent[root]) root = parent[root];

        while (x != root) {
            int p = parent[x];
            parent[x] = root;
            x = p;
        }
        return root;
    }

    private boolean union(int a, int b) {
        int ra = findRoot(a);
        int rb = findRoot(b);

        if ( ra == rb ) return false;

        if ( size[ra] < size[rb] ) { parent[ra] = rb; size[rb] += size[ra]; }
        else                       { parent[rb] = ra; size[ra] += size[rb]; }

        return true;
    }

    // === Mapping helpers ===
    private int index(int row, int col) {
        return row * COLS + col;
    }
}

