package benchmarks;

import grid.interfaces.StreamingGrid;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public final class RandomStreamingGrid implements StreamingGrid {

    private final int rows, cols;
    private final double density;

    private int currentRow = 0;
    private final boolean[] buffer;
    private final Random random;

    public RandomStreamingGrid(int rows, int cols, double density, long seed) {
        this.rows = rows;
        this.cols = cols;
        this.density = density;
        this.buffer = new boolean[cols];
        this.random = new Random(seed);
    }

    @Override
    public boolean hasNextRow() {
        return currentRow < rows;
    }

    @Override
    public boolean[] nextRow() {
        if (!hasNextRow())
            throw new NoSuchElementException("No more rows.");

        for (int c = 0; c < cols; c++) {
            buffer[c] = random.nextDouble() < density;
        }
        currentRow++;
        return buffer;
    }

    @Override public int cols() { return cols; }
    @Override public int rows() { return rows; }

    @Override
    public Iterator<boolean[]> iterator() {
        return new Iterator<>() {
            @Override public boolean hasNext() { return hasNextRow(); }
            @Override public boolean[] next() { return nextRow(); }
        };
    }
}
