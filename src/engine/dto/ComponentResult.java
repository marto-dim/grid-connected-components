package engine.dto;

import algos.ConnectedComponentsQuery;

/**
 * Immutable DTO holding:
 *  - number of connected components
 *  - whether WQU_PC (Full Grid Union-Find) was used
 *  - optional WQU_PC query API (null when SW_UF is used)
 */
public record ComponentResult (
        int components,
        boolean usedWQU_PC,
        ConnectedComponentsQuery queryApi
) { }