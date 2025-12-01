package engine;

import algos.ConnectedComponentsQuery;

/**
 * Holds:
 *  - number of components
 *  - whether DSU (WQU_PC) was used
 *  - optional DSU query API
 */
public final class ComponentResult {

    private final int components;
    private final boolean usedWQU_PC;
    private final ConnectedComponentsQuery queryApi;

    public ComponentResult(int comps, boolean usedWQU_PC, ConnectedComponentsQuery queryApi) {
        this.components = comps;
        this.usedWQU_PC = usedWQU_PC;
        this.queryApi = queryApi;
    }

    public int components() { return components; }
    public boolean usedWQU_PC() { return usedWQU_PC; }

    public ConnectedComponentsQuery queryApi() {
        if ( !usedWQU_PC )
            throw new UnsupportedOperationException("Query API not available for SW_UF");
        return queryApi;
    }
}
