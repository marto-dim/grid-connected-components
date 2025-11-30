package generators;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Generates large grid files for testing SW_UF (streaming CCL)
 * and WQU_PC (full-grid DSU).
 *
 * Format:
 *   rows cols
 *   0101101001...
 *
 * Supports files up to ~10 GB safely.
 */
public final class FileGenerator {

    private static final Logger log = Logger.getLogger(FileGenerator.class.getName());

    /** Deterministic generation for reproducible tests */
    private static final long SEED = 123456789L;

    private FileGenerator() {}

    /**
     * Generates a grid file with the given dimensions + density.
     * Supports very large outputs (GB-scale).
     */
    public static void generate(String fileName, int rows, int cols, double density) throws IOException {

        long cells = (long) rows * cols;
        double estimatedGB = cells / 1_000_000_000.0;

        log.info("Generating " + fileName + " (" + rows + "x" + cols +
                ", p=" + density + ", approx=" + String.format("%.2f", estimatedGB) + " GB)");

        Random rng = new Random(SEED);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            bw.write(rows + " " + cols);
            bw.newLine();

            StringBuilder sb = new StringBuilder(cols);

            for (int r = 0; r < rows; r++) {
                sb.setLength(0);
                for (int c = 0; c < cols; c++) {
                    sb.append(rng.nextDouble() < density ? '1' : '0');
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        }

        log.info("✔ Finished: " + fileName);
    }

    /** Only blocks files larger than ~15GB */
    private static void confirmIfAbove15GB(int rows, int cols) throws IOException {
        long cells = (long) rows * cols;
        double estimatedGB = cells / 1_000_000_000.0;

        if (estimatedGB > 15.0) {
            throw new IOException(
                    "Grid too large: " + rows + "x" + cols +
                            " ≈ " + estimatedGB + " GB. Limit = 15GB."
            );
        }
    }

    /**
     * Entrypoint for generating test suites.
     *
     * You can run ONLY the subsets you want.
     */
    public static void main(String[] args) throws Exception {

        log.info("=== Generating test grids ===");

        // Small
        generate("small_100x100_low.txt", 100, 100, 0.10);
        generate("small_100x100_mid.txt", 100, 100, 0.50);

        // Medium
        generate("med_1000x1000_low.txt", 1000, 1000, 0.10);
        generate("med_2000x2000_mid.txt", 2000, 2000, 0.40);

        // Large (up to ~1GB)
        generate("large_10k_10k_low.txt", 10_000, 10_000, 0.10);
        generate("large_10k_10k_mid.txt", 10_000, 10_000, 0.40);

        // Very large (2.5GB)
        confirmIfAbove15GB(50_000, 50_000);
        generate("huge_50k_50k_low.txt", 50_000, 50_000, 0.10);

        // MONSTER (10GB) — ONLY uncomment when you are *sure* you want it:
        confirmIfAbove15GB(100_000, 100_000);
        generate("monster_100k_100k_low.txt", 100_000, 100_000, 0.10);

        log.info("=== All grids generated ===");
    }
}
