package com.vbarjovanu.coderetreat.gameoflife.state;

import com.vbarjovanu.coderetreat.gameoflife.state.CellState;
import com.vbarjovanu.coderetreat.gameoflife.state.CellStateAlive;
import com.vbarjovanu.coderetreat.gameoflife.state.CellStateDead;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;

class CellStateAliveTest {
    private CellState cellState;

    @BeforeEach
    void setUp() {
        this.cellState = new CellStateAlive();
    }

    @Test
    void shoudDieIfLessThan2Neighbours() {
        CellState nextCellState = this.cellState.nextState(1);
        assertThat(nextCellState, instanceOf(CellStateDead.class));
        assertFalse(nextCellState.isAlive());
    }

    @Test
    void shouldRemainAliveIf2Or3Neighbours() {
        CellState nextCellState;
        nextCellState = this.cellState.nextState(2);
        assertThat(nextCellState, instanceOf(CellStateAlive.class));
        assertTrue(nextCellState.isAlive());
        nextCellState = this.cellState.nextState(3);
        assertThat(nextCellState, instanceOf(CellStateAlive.class));
        assertTrue(nextCellState.isAlive());
    }


    @Test
    void shoudDieIfMoreThan3Neighbours() {
        CellState nextCellState = this.cellState.nextState(4);
        assertThat(nextCellState, instanceOf(CellStateDead.class));
        assertFalse(nextCellState.isAlive());
    }
}
