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
    private final boolean usedDSU;
    private final ConnectedComponentsQuery queryApi;

    public ComponentResult(int comps, boolean usedDSU, ConnectedComponentsQuery queryApi) {
        this.components = comps;
        this.usedDSU = usedDSU;
        this.queryApi = queryApi;
    }

    public int components() { return components; }
    public boolean usedDSU() { return usedDSU; }

    public ConnectedComponentsQuery queryApi() {
        if (!usedDSU)
            throw new UnsupportedOperationException("Query API not available for SW_UF");
        return queryApi;
    }
}
