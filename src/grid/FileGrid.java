package grid;

import grid.interfaces.Grid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Full in-memory grid loader.
 *
 * Suitable for WQU_PC (query-enabled DSU).
 * Loads the entire grid into a boolean[][] matrix.
 *
 * Format:
 *   rows cols
 *   101010110010
 *   110010101011
 */
public final class FileGrid implements Grid, Iterable<boolean[]> {

    private final int rows;
    private final int cols;
    private final boolean[][] matrix;

    /**
     * Loads the entire file into memory.
     */
    public FileGrid(String file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String[] dims = br.readLine().trim().split("\\s+");
            this.rows = Integer.parseInt(dims[0]);
            this.cols = Integer.parseInt(dims[1]);

            matrix = new boolean[rows][cols];

            for (int r = 0; r < rows; r++) {
                String line = br.readLine();

                if (line == null)
                    throw new IOException("Unexpected EOF at row " + r);

                line = line.trim();

                if (line.length() != cols)
                    throw new IOException(
                            "Invalid row length at line " + r + ": expected " + cols
                    );

                for (int c = 0; c < cols; c++) {
                    matrix[r][c] = (line.charAt(c) == '1');
                }
            }
        }
    }

    @Override public int rows() { return rows; }

    @Override public int cols() { return cols; }

    @Override
    public boolean isMarked(int row, int col) {
        return matrix[row][col];
    }

    @Override
    public Iterator<boolean[]> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override public boolean hasNext() { return index < rows; }

            @Override public boolean[] next() { return matrix[index++]; }
        };
    }
}

