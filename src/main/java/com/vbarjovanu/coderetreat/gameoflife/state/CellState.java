package com.vbarjovanu.coderetreat.gameoflife.state;

import com.vbarjovanu.coderetreat.gameoflife.util.DefaultHashMap;

public abstract class CellState {
    DefaultHashMap<Integer, Class<? extends CellState>> stateSwitchers;

    protected CellState() {
        this.configStateSwitchers();
    }

    protected abstract void configStateSwitchers();

    public boolean isAlive() {
        return false;
    }

    public CellState nextState(int liveNeighboursCount) {
        CellState cellState = null;
        try {
            cellState = this.stateSwitchers.get(liveNeighboursCount).newInstance();
        } catch (Exception e) {
            //if state can't be switched it will return null and tests should fail
            e.printStackTrace();
        }
        return cellState;
    }
}
