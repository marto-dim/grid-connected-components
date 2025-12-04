package grid;

import grid.interfaces.StreamingGrid;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.*;

public class StreamingFileGridTests {

    @Test
    void readsRowsSequentially() throws Exception {

        String file = "stream_test.txt";

        try (FileWriter fw = new FileWriter(file)) {
            fw.write("3 4\n");
            fw.write("1010\n");
            fw.write("1110\n");
            fw.write("0001\n");
        }

        StreamingGrid sg = new StreamingFileGrid(file);

        assertEquals(3, sg.rows());
        assertEquals(4, sg.cols());
        assertTrue(sg.hasNextRow());

        boolean[] r1 = sg.nextRow();
        boolean[] r2 = sg.nextRow();
        boolean[] r3 = sg.nextRow();

        assertArrayEquals(new boolean[]{true, false, true, false}, r1);
        assertArrayEquals(new boolean[]{true, true, true, false}, r2);
        assertArrayEquals(new boolean[]{false, false, false, true}, r3);

        assertFalse(sg.hasNextRow());
    }
}

