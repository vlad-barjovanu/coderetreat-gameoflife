package com.vbarjovanu.coderetreat.gameoflife.state;

import java.util.HashMap;
import java.util.Map;

public class CellStateFactory {
    private static Map<Boolean, Class<? extends CellState>> cellStatesMap = initCellStatesMap();

    private static Map<Boolean, Class<? extends CellState>> initCellStatesMap() {
        Map<Boolean, Class<? extends CellState>> map;
        map = new HashMap<>();
        map.put(false, CellStateDead.class);
        map.put(true, CellStateAlive.class);
        return map;
    }


    public static CellState getCellState(boolean live) {
        CellState cellState = null;
        try {
            cellState = cellStatesMap.get(live).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellState;
    }
}
