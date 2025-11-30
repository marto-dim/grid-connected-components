package engine;

/**
 * Simple immutable holder for grid metadata.
 */
public final class GridSizeInfo {

    private final int rows;
    private final int cols;
    private final long totalCells;
    private final boolean dsuAllowed;

    public GridSizeInfo(int rows, int cols, long totalCells, boolean dsuAllowed) {
        this.rows = rows;
        this.cols = cols;
        this.totalCells = totalCells;
        this.dsuAllowed = dsuAllowed;
    }

    public int rows() { return rows; }
    public int cols() { return cols; }
    public long totalCells() { return totalCells; }
    public boolean dsuAllowed() { return dsuAllowed; }
}