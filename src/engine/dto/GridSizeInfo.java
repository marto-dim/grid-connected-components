package engine.dto;

/**
 * Simple immutable holder for grid metadata.
 */
public record GridSizeInfo (
        int rows,
        int cols,
        long totalCells,
        boolean wqu_pcPossible
) { }