package algos;

import models.Point2D;

/**
 * Extended interface allowing interactive queries,
 * supported only by full-grid WQU_PC algorithms.
 */
public interface ConnectedComponentsQuery extends ConnectedComponentsBasic {

    /**
     * Finds the component ID of a given cell.
     *
     * @param point (row, col)
     * @return component id or -1 if the cell is unmarked.
     */
    int find(Point2D point);

    /**
     * Checks whether two marked cells belong
     * to the same connected component.
     *
     * @param a first point
     * @param b second point
     * @return true if connected.
     */
    boolean connected(Point2D a, Point2D b);
}
