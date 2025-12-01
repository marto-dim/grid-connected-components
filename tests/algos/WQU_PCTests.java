package algos;

import algos.implementations.wqu_pc.WQU_PC;
import grid.interfaces.Grid;
import models.Point2D;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class WQU_PCTests {

    @Test
    void shouldReturnZero_whenNoMarkedCells() {
        Grid grid = mock(Grid.class);

        when(grid.rows()).thenReturn(3);
        when(grid.cols()).thenReturn(3);
        when(grid.isMarked(anyInt(), anyInt())).thenReturn(false);

        WQU_PC uf = new WQU_PC(grid);
        assertEquals(0, uf.count());
    }

    @Test
    void shouldDetectSingleComponent_whenAllMarked() {
        Grid grid = mock(Grid.class);

        when(grid.rows()).thenReturn(3);
        when(grid.cols()).thenReturn(3);
        when(grid.isMarked(anyInt(), anyInt())).thenReturn(true);

        WQU_PC uf = new WQU_PC(grid);

        assertEquals(1, uf.count());
        assertEquals(0, uf.find(new Point2D(0, 0)));
        assertEquals(0, uf.find(new Point2D(2, 2)));
        assertTrue(uf.connected(new Point2D(0, 0), new Point2D(2, 2)));
    }

    @Test
    void shouldDetectTwoDiagonalComponents() {
        Grid grid = mock(Grid.class);

        when(grid.rows()).thenReturn(2);
        when(grid.cols()).thenReturn(2);

        when(grid.isMarked(0, 0)).thenReturn(true);
        when(grid.isMarked(0, 1)).thenReturn(false);
        when(grid.isMarked(1, 0)).thenReturn(false);
        when(grid.isMarked(1, 1)).thenReturn(true);

        WQU_PC uf = new WQU_PC(grid);

        assertEquals(2, uf.count());
        assertNotEquals(uf.find(new Point2D(0, 0)),
                uf.find(new Point2D(1, 1)));
    }

    @Test
    void shouldReturnMinusOne_whenCellIsUnmarked() {
        Grid grid = mock(Grid.class);

        when(grid.rows()).thenReturn(2);
        when(grid.cols()).thenReturn(2);

        when(grid.isMarked(0, 0)).thenReturn(false);
        when(grid.isMarked(0, 1)).thenReturn(true);
        when(grid.isMarked(1, 0)).thenReturn(true);
        when(grid.isMarked(1, 1)).thenReturn(true);

        WQU_PC uf = new WQU_PC(grid);

        assertEquals(-1, uf.find(new Point2D(0, 0)));
    }

    // EXTRA TESTS ------------------------------------------------------

    @Test
    void shouldDetectLShapedComponent() {
        Grid grid = mock(Grid.class);

        when(grid.rows()).thenReturn(2);
        when(grid.cols()).thenReturn(2);

        when(grid.isMarked(0, 0)).thenReturn(true);
        when(grid.isMarked(0, 1)).thenReturn(false);
        when(grid.isMarked(1, 0)).thenReturn(true);
        when(grid.isMarked(1, 1)).thenReturn(true);

        WQU_PC uf = new WQU_PC(grid);

        assertEquals(1, uf.count());
    }

    @Test
    void shouldDetectThreeSeparateCells() {
        Grid grid = mock(Grid.class);

        when(grid.rows()).thenReturn(3);
        when(grid.cols()).thenReturn(1);

        when(grid.isMarked(0, 0)).thenReturn(true);
        when(grid.isMarked(1, 0)).thenReturn(false);
        when(grid.isMarked(2, 0)).thenReturn(true);

        WQU_PC uf = new WQU_PC(grid);

        assertEquals(2, uf.count());
    }
}
