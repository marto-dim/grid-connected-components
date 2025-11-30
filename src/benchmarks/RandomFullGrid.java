package benchmarks;

import grid.interfaces.Grid;
import java.util.Random;

public final class RandomFullGrid implements Grid {

    private final int rows, cols;
    private final boolean[][] data;

    public RandomFullGrid(int rows, int cols, double density, long seed) {
        this.rows = rows;
        this.cols = cols;
        data = new boolean[rows][cols];

        Random random = new Random(seed);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                data[r][c] = random.nextDouble() < density;
            }
        }
    }

    @Override
    public boolean isMarked(int r, int c) { return data[r][c]; }

    @Override
    public int rows() { return rows; }

    @Override
    public int cols() { return cols; }
}
