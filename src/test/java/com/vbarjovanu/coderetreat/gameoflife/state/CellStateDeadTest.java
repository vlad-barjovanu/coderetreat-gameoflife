package com.vbarjovanu.coderetreat.gameoflife.state;

import com.vbarjovanu.coderetreat.gameoflife.state.CellState;
import com.vbarjovanu.coderetreat.gameoflife.state.CellStateAlive;
import com.vbarjovanu.coderetreat.gameoflife.state.CellStateDead;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CellStateDeadTest {
    private CellState cellState;

    @BeforeEach
    void setUp() {
        this.cellState = new CellStateDead();
    }

    @Test
    void shoudBecomeAliveIfHas3Neighbours() {
        CellState nextCellState = this.cellState.nextState(3);
        assertThat(nextCellState, instanceOf(CellStateAlive.class));
        assertTrue(nextCellState.isAlive());
    }

    @Test
    void shouldRemainDeadIfDoesntHave3Neighbours() {
        CellState nextCellState;
        int[] neighboursCountArray = new int[]{1, 2, 4, 5};
        for (int neighboursCount : neighboursCountArray) {
            nextCellState = this.cellState.nextState(neighboursCount);
            assertThat(nextCellState, instanceOf(CellStateDead.class));
            assertFalse(nextCellState.isAlive());
        }
    }
}
