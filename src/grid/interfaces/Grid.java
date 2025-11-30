package grid.interfaces;

/**
 * Represents a fully loaded 2D boolean grid.
 */
public interface Grid {

    int rows();
    int cols();

    /**
     * @return true if cell (row, col) is marked.
     */
    boolean isMarked(int row, int col);
}
