package com.vbarjovanu.coderetreat.gameoflife.state;

import com.vbarjovanu.coderetreat.gameoflife.util.DefaultHashMap;

public class CellStateDead extends CellState {
    @Override
    protected void configStateSwitchers() {
        this.stateSwitchers = new DefaultHashMap<>(CellStateDead.class);
        this.stateSwitchers.put(3, CellStateAlive.class);
    }
}
