package algos;

/**
 * Minimal interface for algorithms that only compute
 * the total number of connected components in a grid.
 */
public interface ConnectedComponentsBasic {

    /**
     * Counts the number of connected components.
     *
     * @return total connected components.
     */
    int count();
}
