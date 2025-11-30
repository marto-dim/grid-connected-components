package benchmarks;

import algos.ConnectedComponentsBasic;
import algos.ConnectedComponentsQuery;
import algos.implementations.sw_uf.SW_UF;
import algos.implementations.wqu_pc.WQU_PC;

import grid.Grid;
import grid.StreamingGrid;

public final class BenchmarkRunner {

    public static void main(String[] args) {

        for (int size : BenchmarkConfig.SIZES) {
            for (double density : BenchmarkConfig.DENSITIES) {

                long seed = 123456789L;
                System.out.printf("%n===== %dx%d  density=%.2f =====%n",
                        size, size, density);

                // ------------------- SW_UF STREAMING -------------------
                StreamingGrid sg = new RandomStreamingGrid(size, size, density, seed);

                var swResult = BenchmarkUtils.measureWithPeak(tracker -> {
                    ConnectedComponentsBasic sw = new SW_UF(sg);
                    int comp = sw.count();
                    tracker.check();
                    return comp;
                });

                System.out.printf(
                        "SW_UF: components=%d  time=%.2f ms  peakMem=%d MB%n",
                        swResult.result(),
                        BenchmarkUtils.nanosToMs(swResult.nanos()),
                        (swResult.peakBytes() / (1024 * 1024))
                );


                // ------------------- WQU_PC FULL GRID -------------------
                // Limit to avoid OOM
                if (size <= 20_000) {

                    Grid fg = new RandomFullGrid(size, size, density, seed);

                    var dsuResult = BenchmarkUtils.measureWithPeak(tracker -> {
                        ConnectedComponentsQuery wqu = new WQU_PC(fg);
                        int comp = wqu.count();
                        tracker.check();
                        return comp;
                    });

                    System.out.printf(
                            "WQU_PC: components=%d  time=%.2f ms  peakMem=%d MB%n",
                            dsuResult.result(),
                            BenchmarkUtils.nanosToMs(dsuResult.nanos()),
                            (dsuResult.peakBytes() / (1024 * 1024))
                    );

                    if (!swResult.result().equals(dsuResult.result()))
                        System.out.println("MISMATCH!");
                    else
                        System.out.println("Results match!");

                } else {
                    System.out.println("WQU_PC skipped (grid too large)");
                }
            }
        }
    }
}
