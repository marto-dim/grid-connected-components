package algos;

import algos.implementations.sw_uf.SW_UF;
import algos.implementations.wqu_pc.WQU_PC;
import benchmarks.RandomFullGrid;
import benchmarks.RandomStreamingGrid;
import grid.interfaces.Grid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsistencyTests {

    // ===============================================================
    // Test 1 — base random grid
    // ===============================================================
    @Test
    void shouldMatchOnRandomGrid_small() {
        int rows = 40, cols = 40;
        double density = 0.35;
        long seed = 999;

        Grid full = new RandomFullGrid(rows, cols, density, seed);
        RandomStreamingGrid stream = new RandomStreamingGrid(rows, cols, density, seed);

        int wqu = new WQU_PC(full).count();
        int sw  = new SW_UF(stream).count();

        assertEquals(wqu, sw);
    }

    // ===============================================================
    // Test 2 — several random seeds
    // ===============================================================
    @Test
    void shouldMatchForMultipleSeeds() {
        int rows = 50, cols = 50;
        double density = 0.45;

        for (long seed = 1; seed <= 10; seed++) {
            Grid full = new RandomFullGrid(rows, cols, density, seed);
            RandomStreamingGrid stream = new RandomStreamingGrid(rows, cols, density, seed);

            assertEquals(
                    new WQU_PC(full).count(),
                    new SW_UF(stream).count(),
                    "Mismatch at seed=" + seed
            );
        }
    }

    // ===============================================================
    // Test 3 — density = 0 (all empty)
    // ===============================================================
    @Test
    void shouldMatchOnAllEmpty() {
        int rows = 100, cols = 100;
        double density = 0.0;
        long seed = 123;

        Grid full = new RandomFullGrid(rows, cols, density, seed);
        RandomStreamingGrid stream = new RandomStreamingGrid(rows, cols, density, seed);

        assertEquals(0, new WQU_PC(full).count());
        assertEquals(0, new SW_UF(stream).count());
    }

    // ===============================================================
    // Test 4 — density = 1 (all filled → 1 component)
    // ===============================================================
    @Test
    void shouldMatchOnAllFilled() {
        int rows = 120, cols = 120;
        double density = 1.0;
        long seed = 777;

        Grid full = new RandomFullGrid(rows, cols, density, seed);
        RandomStreamingGrid stream = new RandomStreamingGrid(rows, cols, density, seed);

        assertEquals(1, new WQU_PC(full).count());
        assertEquals(1, new SW_UF(stream).count());
    }

    // ===============================================================
    // Test 5 — skinny grids Nx1
    // ===============================================================
    /*@Test
    void shouldMatchOnSkinnyGrid() {
        int rows = 200, cols = 1;
        double density = 0.5;
        long seed = 42;

        Grid full = new RandomFullGrid(rows, cols, density, seed);
        RandomStreamingGrid stream = new RandomStreamingGrid(rows, cols, density, seed);

        assertEquals(
                new WQU_PC(full).count(),
                new SW_UF(stream).count()
        );
    }*/

    // ===============================================================
    // Test 6 — wide grids 1xN
    // ===============================================================
    /*@Test
    void shouldMatchOnWideGrid() {
        int rows = 1, cols = 200;
        double density = 0.5;
        long seed = 1234;

        Grid full = new RandomFullGrid(rows, cols, density, seed);
        RandomStreamingGrid stream = new RandomStreamingGrid(rows, cols, density, seed);

        assertEquals(
                new WQU_PC(full).count(),
                new SW_UF(stream).count()
        );
    }*/

    // ===============================================================
    // Test 7 — medium random grids of several densities
    // ===============================================================
    @Test
    void shouldMatchOnMultipleDensities() {
        int rows = 60, cols = 60;
        double[] densities = {0.10, 0.30, 0.50, 0.80};
        long seed = 121212;

        for (double density : densities) {
            Grid full = new RandomFullGrid(rows, cols, density, seed);
            RandomStreamingGrid stream = new RandomStreamingGrid(rows, cols, density, seed);

            int wqu = new WQU_PC(full).count();
            int sw  = new SW_UF(stream).count();

            assertEquals(
                    wqu,
                    sw,
                    "Mismatch at density=" + density
            );
        }
    }
}

