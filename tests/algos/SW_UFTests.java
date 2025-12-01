package algos;

import algos.implementations.sw_uf.SW_UF;
import grid.interfaces.StreamingGrid;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class SW_UFTests {

    @Test
    void shouldReturnZero_whenGridIsEmpty() {
        StreamingGrid sg = mock(StreamingGrid.class);

        when(sg.rows()).thenReturn(3);
        when(sg.cols()).thenReturn(3);
        when(sg.hasNextRow()).thenReturn(false);

        SW_UF uf = new SW_UF(sg);
        assertEquals(0, uf.count());
    }

    @Test
    void shouldDetectSingleComponent_whenOneRowAllMarked() {
        StreamingGrid sg = mock(StreamingGrid.class);

        boolean[] row = {true, true, true};

        when(sg.rows()).thenReturn(1);
        when(sg.cols()).thenReturn(3);
        when(sg.hasNextRow()).thenReturn(true, false);
        when(sg.nextRow()).thenReturn(row);

        SW_UF uf = new SW_UF(sg);
        assertEquals(1, uf.count());
    }

    @Test
    void shouldDetectTwoVerticalComponents() {
        StreamingGrid sg = mock(StreamingGrid.class);

        boolean[] r1 = {true};
        boolean[] r2 = {false};
        boolean[] r3 = {true};

        when(sg.rows()).thenReturn(3);
        when(sg.cols()).thenReturn(1);
        when(sg.hasNextRow()).thenReturn(true, true, true, false);
        when(sg.nextRow()).thenReturn(r1, r2, r3);

        SW_UF uf = new SW_UF(sg);
        assertEquals(2, uf.count());
    }

    @Test
    void shouldDetectHorizontalTwoComponents() {
        StreamingGrid sg = mock(StreamingGrid.class);

        boolean[] r = {true, false, true};

        when(sg.rows()).thenReturn(1);
        when(sg.cols()).thenReturn(3);
        when(sg.hasNextRow()).thenReturn(true, false);
        when(sg.nextRow()).thenReturn(r);

        SW_UF uf = new SW_UF(sg);
        assertEquals(2, uf.count());
    }

    @Test
    void shouldDetectLShapeAsOneComponent() {
        StreamingGrid sg = mock(StreamingGrid.class);

        boolean[] r1 = {true, false};
        boolean[] r2 = {true, true};

        when(sg.rows()).thenReturn(2);
        when(sg.cols()).thenReturn(2);
        when(sg.hasNextRow()).thenReturn(true, true, false);
        when(sg.nextRow()).thenReturn(r1, r2);

        SW_UF uf = new SW_UF(sg);
        assertEquals(1, uf.count());
    }

    @Test
    void shouldHandleSingleCell() {
        StreamingGrid sg = mock(StreamingGrid.class);

        boolean[] r = {true};

        when(sg.rows()).thenReturn(1);
        when(sg.cols()).thenReturn(1);
        when(sg.hasNextRow()).thenReturn(true, false);
        when(sg.nextRow()).thenReturn(r);

        SW_UF uf = new SW_UF(sg);
        assertEquals(1, uf.count());
    }
}
