package grid;

import grid.interfaces.Grid;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

public class FileGridTests {
    @Test
    void loadsGridCorrectly() throws Exception {

        String file = "test_grid.txt";

        try (FileWriter fw = new FileWriter(file)) {
            fw.write("3 3\n");
            fw.write("101\n");
            fw.write("010\n");
            fw.write("111\n");
        }

        Grid g = new FileGrid(file);

        assertEquals(3, g.rows());
        assertEquals(3, g.cols());
        assertTrue(g.isMarked(0, 0));
        assertFalse(g.isMarked(1, 0));
        assertTrue(g.isMarked(2, 2));
    }

    @Test
    void throwsWhenRowTooShort() throws Exception {

        File f = File.createTempFile("grid_bad", ".txt");
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println("3 3");
            pw.println("01");    // too short
        }

        assertThrows(Exception.class, () -> new FileGrid(f.getAbsolutePath()));
    }
}
