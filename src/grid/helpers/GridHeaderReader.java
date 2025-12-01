package grid.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads only the grid dimensions from a grid file.
 */
public final class GridHeaderReader {

    private GridHeaderReader() {}

    public static int[] readHeader(String file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String[] parts = br.readLine().trim().split("\\s+");

            int rows = Integer.parseInt(parts[0]);
            int cols = Integer.parseInt(parts[1]);

            return new int[]{ rows, cols };
        }
    }
}
