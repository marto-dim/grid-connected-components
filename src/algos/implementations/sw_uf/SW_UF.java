package algos.implementations.sw_uf;

import algos.ConnectedComponentsBasic;
import grid.StreamingGrid;

import java.util.Arrays;

/**
 * Sliding-Window Union–Find (SW_UF).
 *
 * Streaming connected components labeling for extremely large grids.
 * Keeps only two rows of labels + a dynamically growing union–find structure.
 *
 * Memory usage: O(COLS) at most O(ROWS * COLS) but more rarely
 * Output: number of connected components (no connected(p,q) queries).
 */
public final class SW_UF implements ConnectedComponentsBasic {

    private final StreamingGrid grid;

    public SW_UF(StreamingGrid grid) {
        this.grid = grid;
    }

    @Override public int count() { return compute(); }

    // ============================================================
    // === Core compute() =========================================
    // ============================================================

    private int compute() {

        final int ROWS = grid.rows();
        final int COLS = grid.cols();
        if ( !grid.hasNextRow() ) return 0;

        final int MAX_LABELS = safeMaxLabels(ROWS, COLS);

        ResizableUF uf = new ResizableUF(1024);

        int[] prevLab = new int[COLS];
        int[] currLab = new int[COLS];
        boolean[] prevRow = new boolean[COLS];

        int nextLabel = 1;

        boolean[] row = grid.nextRow();
        nextLabel = processFirstRow(row, currLab, uf, nextLabel, MAX_LABELS);

        System.arraycopy(row, 0, prevRow, 0, COLS);
        swap(prevLab, currLab);
        Arrays.fill(currLab, 0);

        // --- process remaining rows ---
        while ( grid.hasNextRow() ) {
            row = grid.nextRow();
            Arrays.fill(currLab, 0);

            nextLabel = processRow(row, prevRow, prevLab, currLab,
                    uf, nextLabel, MAX_LABELS);

            // slide the window
            System.arraycopy(row, 0, prevRow, 0, COLS);
            swap(prevLab, currLab);
        }

        return countUniqueRoots(uf, nextLabel - 1);
    }

    // ============================================================
    // === Helpers: MAX_LABELS ====================================
    // ============================================================

    private int safeMaxLabels(int ROWS, int COLS) {
        long maxLong = (long) (0.25 * ROWS * (long) COLS);
        if (maxLong > Integer.MAX_VALUE - 5)
            throw new IllegalArgumentException("Grid too large for SW_UF.");
        return (int) maxLong;
    }

    // ============================================================
    // === First row ===============================================
    // ============================================================

    private int processFirstRow(boolean[] row,
                                int[] currLab,
                                ResizableUF uf,
                                int nextLabel,
                                int MAX_LABELS) {

        final int COLS = row.length;

        // first cell
        if ( row[0] ) {
            checkLabel(nextLabel, MAX_LABELS);
            uf.ensureCapacity(nextLabel);
            currLab[0] = nextLabel++;
        }

        // remaining cells
        for (int col = 1; col < COLS; col++) {
            if ( !row[col] ) continue;

            if ( row[col - 1] ) {
                currLab[col] = currLab[col - 1];
            } else {
                checkLabel(nextLabel, MAX_LABELS);
                uf.ensureCapacity(nextLabel);
                currLab[col] = nextLabel++;
            }
        }

        return nextLabel;
    }

    // ============================================================
    // === Middle rows =============================================
    // ============================================================

    private int processRow(boolean[] row,
                           boolean[] prevRow,
                           int[] prevLab,
                           int[] currLab,
                           ResizableUF uf,
                           int nextLabel,
                           int MAX_LABELS) {

        final int COLS = row.length;

        // first col
        if ( row[0] ) {
            if ( prevRow[0] ) {
                currLab[0] = prevLab[0];
            } else {
                checkLabel(nextLabel, MAX_LABELS);
                uf.ensureCapacity(nextLabel);
                currLab[0] = nextLabel++;
            }
        }

        // remaining cols
        for (int col = 1; col < COLS; col++) {
            if ( !row[col] ) continue;

            int left = row[col - 1] ? currLab[col - 1] : 0;
            int up   = prevRow[col] ? prevLab[col] : 0;

            if ( left != 0 ) {
                if ( up != 0 && up != left )
                    uf.union(left, up);

                currLab[col] = left;
            }
            else if ( up != 0 ) {
                currLab[col] = up;
            }
            else {
                checkLabel(nextLabel, MAX_LABELS);
                uf.ensureCapacity(nextLabel);
                currLab[col] = nextLabel++;
            }
        }

        return nextLabel;
    }

    // ============================================================
    // === Finish: count unique UF roots ===========================
    // ============================================================

    private int countUniqueRoots(ResizableUF uf, int usedLabels) {
        boolean[] seen = new boolean[usedLabels + 1];
        int count = 0;

        for (int lbl = 1; lbl <= usedLabels; lbl++) {
            int root = uf.find(lbl);
            if ( !seen[root] ) {
                seen[root] = true;
                count++;
            }
        }
        return count;
    }

    // ============================================================
    // === Misc small utilities ===================================
    // ============================================================

    private void checkLabel(int nextLabel, int MAX_LABELS) {
        if (nextLabel >= MAX_LABELS)
            throw new IllegalStateException("Increase MAX_LABELS");
    }

    private void swap(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            int tmp = a[i];
            a[i] = b[i];
            b[i] = tmp;
        }
    }
}
