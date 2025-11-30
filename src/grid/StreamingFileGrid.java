package grid;

import grid.interfaces.StreamingGrid;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Streaming reader for grid files of format:
 *   rows cols
 *   010101010
 *   110001101
 *
 * Only keeps ONE row in memory.
 */
public final class StreamingFileGrid implements StreamingGrid {

    private final int rows;
    private final int cols;

    private final FileInputStream in;
    private final byte[] buffer;

    private int currentRow = 0;

    public StreamingFileGrid(String file) throws IOException {

        in = new FileInputStream(file);

        // Read header: "ROWS COLS"
        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = in.read()) != -1 && ch != '\n') {
            sb.append((char) ch);
        }

        String[] parts = sb.toString().trim().split("\\s+");
        rows = Integer.parseInt(parts[0]);
        cols = Integer.parseInt(parts[1]);

        buffer = new byte[cols];
    }

    @Override public int rows() { return rows; }
    @Override public int cols() { return cols; }

    @Override
    public boolean hasNextRow() {
        return currentRow < rows;
    }

    @Override
    public boolean[] nextRow() {

        if (!hasNextRow())
            throw new RuntimeException("No more rows");

        try {
            int n = in.read(buffer, 0, cols);
            if (n != cols)
                throw new IOException("Unexpected EOF at row " + currentRow);

            // read \n or \r\n
            int newline = in.read();
            if (newline == '\r') {
                in.read(); // read the following \n
            }

            boolean[] row = new boolean[cols];
            for (int c = 0; c < cols; c++) {
                row[c] = (buffer[c] == '1');
            }

            currentRow++;
            return row;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterator<boolean[]> iterator() {
        return new Iterator<>() {
            @Override public boolean hasNext() { return hasNextRow(); }
            @Override public boolean[] next() { return nextRow(); }
        };
    }
}

