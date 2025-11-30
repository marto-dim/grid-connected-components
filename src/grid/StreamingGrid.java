package grid;

/**
 * Stream-based grid where rows are consumed one by one.
 */
public interface StreamingGrid extends Iterable<boolean[]> {

    int rows();
    int cols();

    /**
     * @return true if more rows are available.
     */
    boolean hasNextRow();

    /**
     * @return next row as a boolean array.
     */
    boolean[] nextRow();
}
