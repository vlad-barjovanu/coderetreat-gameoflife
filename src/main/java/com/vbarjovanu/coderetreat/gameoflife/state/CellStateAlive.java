package com.vbarjovanu.coderetreat.gameoflife.state;

import com.vbarjovanu.coderetreat.gameoflife.util.DefaultHashMap;

public class CellStateAlive extends CellState {

    @Override
    protected void configStateSwitchers() {
        this.stateSwitchers = new DefaultHashMap<>(CellStateDead.class);
        this.stateSwitchers.put(2, CellStateAlive.class);
        this.stateSwitchers.put(3, CellStateAlive.class);
    }

    @Override
    public boolean isAlive() {
        return true;
    }
}
