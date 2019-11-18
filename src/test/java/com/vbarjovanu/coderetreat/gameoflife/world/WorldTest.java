package com.vbarjovanu.coderetreat.gameoflife.world;

import com.vbarjovanu.coderetreat.gameoflife.CellCoordinates;
import com.vbarjovanu.coderetreat.gameoflife.state.CellState;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class WorldTest {
    World world;

    @Test
    void notInitializedWorldShouldHaveZeroBounds() {
        assertEquals(0, this.world.getLinesCount());
        assertEquals(0, this.world.getColsCount());
    }

    @Test
    void initialisedAliveCellShouldBeAlive() {
        CellState cellState;
        this.world.setCellAlive(1, 1, true);
        assertTrue(this.world.isCellAlive(1, 1));
    }

    @Test
    void initialisedAliveAndThanDeadCellShouldBeDead() {
        CellState cellState;
        this.world.setCellAlive(1, 1, true);
        this.world.setCellAlive(1, 1, false);
        assertFalse(this.world.isCellAlive(1, 1));
    }

    @Test
    void initialisedDeadCellShouldBeDead() {
        CellState cellState;
        this.world.setCellAlive(1, 1, false);
        assertFalse(this.world.isCellAlive(1, 1));
    }

    @Test
    void initialisedDeadAndThanAliveCellShouldBeAlive() {
        CellState cellState;
        this.world.setCellAlive(1, 1, false);
        this.world.setCellAlive(1, 1, true);
        assertTrue(this.world.isCellAlive(1, 1));
    }

    @Test
    void notInitializedCellShouldBeDead() {
        assertFalse(this.world.isCellAlive(1, 1));
    }

    @Test
    void aWorldInitializedWithOneAliveCellShouldHaveTheBoundsAfterThatCellsCoordinates() {
        this.world.setCellAlive(2, 1, true);
        assertEquals(4, this.world.getLinesCount());
        assertEquals(3, this.world.getColsCount());
    }

    @Test
    void aWorldInitializedWithOneDeadCellShouldHaveTheBoundsOnThatCellsCoordinates(){
        this.world.setCellAlive(2, 1, false);
        assertEquals(3, this.world.getLinesCount());
        assertEquals(2, this.world.getColsCount());
    }

    @Test
    void aWorldInitializedWithMoreAliveCellShouldHaveTheBoundsAfterTheOutMostCellsCoordinates() {
        this.world.setCellAlive(2, 1, true);
        this.world.setCellAlive(2, 2, true);
        this.world.setCellAlive(3, 2, true);
        assertEquals(5, this.world.getLinesCount());
        assertEquals(4, this.world.getColsCount());
    }

    @Test
    void aWorldInitializedWithMoreDeadCellShouldHaveTheBoundsOnTheOutMostCellsCoordinates() {
        this.world.setCellAlive(2, 1, false);
        this.world.setCellAlive(2, 2, false);
        this.world.setCellAlive(3, 2, false);
        assertEquals(4, this.world.getLinesCount());
        assertEquals(3, this.world.getColsCount());
    }

    @Test
    void worldWithInitialisedAliveCellsShouldReturnThemAsAList() {
        List<CellCoordinates> list;
        this.world.setCellAlive(1, 1, true);
        this.world.setCellAlive(2, 1, true);
        this.world.setCellAlive(2, 2, false);
        list = this.world.getAliveCells();
        assertNotNull(list);
        assertEquals(2, list.size());
        assertTrue(list.contains(new CellCoordinates(1, 1)));
        assertTrue(list.contains(new CellCoordinates(2, 1)));
        assertFalse(list.contains(new CellCoordinates(2, 2)));
    }

    @Test
    void worldWithInitialisedAliveCellsShouldEvolveOnNextGeneration() {
        List<CellCoordinates> list;

        this.world.setCellAlive(1, 1, true);
        this.world.setCellAlive(2, 1, true);
        this.world.setCellAlive(3, 1, true);
        this.world.nextGeneration();
        assertFalse(this.world.isCellAlive(1, 1));
        assertTrue(this.world.isCellAlive(2, 1));
        assertFalse(this.world.isCellAlive(3, 1));
        assertFalse(this.world.isCellAlive(1, 0));
        assertTrue(this.world.isCellAlive(2, 0));
        assertFalse(this.world.isCellAlive(1, 2));
        assertTrue(this.world.isCellAlive(2, 2));
        list = this.world.getAliveCells();
        assertNotNull(list);
        assertEquals(3, list.size());
        assertFalse(list.contains(new CellCoordinates(1, 1)));
        assertTrue(list.contains(new CellCoordinates(2, 1)));
        assertFalse(list.contains(new CellCoordinates(1, 0)));
        assertTrue(list.contains(new CellCoordinates(2, 0)));
        assertFalse(list.contains(new CellCoordinates(1, 2)));
        assertTrue(list.contains(new CellCoordinates(2, 2)));
    }
}
