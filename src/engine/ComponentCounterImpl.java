package engine;


import algos.ConnectedComponentsBasic;
import algos.ConnectedComponentsQuery;
import algos.implementations.sw_uf.SW_UF;
import algos.implementations.wqu_pc.WQU_PC;
import engine.dto.ComponentResult;
import engine.dto.GridSizeInfo;
import engine.interfaces.ComponentCounter;
import grid.FileGrid;
import grid.StreamingFileGrid;
import grid.interfaces.Grid;

/**
 * Selects algorithm based on grid size,
 * runs WQU_PC or SW_UF,
 * and returns results.
 */
public final class ComponentCounterImpl implements ComponentCounter {

    private static final long WQU_PC_LIMIT = 200_000_000L;  // 200M cells

    @Override
    public GridSizeInfo inspect(String file) throws Exception {
        StreamingFileGrid sg = new StreamingFileGrid(file);
        long cells = (long) sg.rows() * sg.cols();
        boolean dsuAllowed = (cells <= WQU_PC_LIMIT);
        return new GridSizeInfo(sg.rows(), sg.cols(), cells, dsuAllowed);
    }

    @Override
    public ComponentResult compute(String file, boolean useWQU_PC) throws Exception {

        GridSizeInfo info = inspect(file);

        if ( useWQU_PC && !info.wqu_pcPossible() )
            throw new IllegalArgumentException("WQU_PC not allowed for grid this large");

        if ( useWQU_PC ) {
            return runWQU_PC(file);
        }

        return runSWUF(file);
    }

    private ComponentResult runWQU_PC(String file) throws Exception {
        Grid grid = new FileGrid(file);

        long start = System.nanoTime();
        ConnectedComponentsQuery wqu_pc_query = new WQU_PC(grid);
        int comps = wqu_pc_query.count();
        long time = System.nanoTime() - start;

        System.out.printf("WQU_PC runtime: %.3f ms%n", time / 1_000_000.0);

        return new ComponentResult(comps, true, wqu_pc_query);
    }

    private ComponentResult runSWUF(String file) throws Exception {
        StreamingFileGrid sg = new StreamingFileGrid(file);

        long start = System.nanoTime();
        ConnectedComponentsBasic ccl = new SW_UF(sg);
        int comps = ccl.count();
        long time = System.nanoTime() - start;

        System.out.printf("SW_UF runtime: %.3f ms%n", time / 1_000_000.0);

        return new ComponentResult(comps, false, null);
    }
}
