package com.vbarjovanu.coderetreat.gameoflife.world;

import com.vbarjovanu.coderetreat.gameoflife.CellCoordinates;
import com.vbarjovanu.coderetreat.gameoflife.state.CellState;
import com.vbarjovanu.coderetreat.gameoflife.state.CellStateFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapBasedWorld implements World {
    private int linesCount;
    private int colsCount;
    private Map<CellCoordinates, CellData> knownCells;

    public MapBasedWorld() {
        this.knownCells = new HashMap<>();
    }

    @Override
    public int getLinesCount() {
        return this.linesCount;
    }

    @Override
    public int getColsCount() {
        return this.colsCount;
    }

    @Override
    public void setCellAlive(int line, int col, boolean alive) {
        CellData cellData;
        CellCoordinates cellCoordinates = new CellCoordinates(line, col);

        cellData = new CellData(alive);
        this.knownCells.put(cellCoordinates, cellData);

        this.linesCount = Math.max(this.linesCount, line + 1 + (alive ? 1 : 0));
        this.colsCount = Math.max(this.colsCount, col + 1 + (alive ? 1 : 0));
    }

    @Override
    public boolean isCellAlive(int line, int col) {
        CellCoordinates cellCoordinates = new CellCoordinates(line, col);
        CellData cellData = this.knownCells.get(cellCoordinates);
        return cellData != null && cellData.isAlive();
    }

    @Override
    public List<CellCoordinates> getAliveCells() {
        List<CellCoordinates> list = new ArrayList<>();
        this.knownCells.forEach((cellCoordinates, cellData) -> {
            if (cellData.isAlive()) {
                list.add(cellCoordinates);
            }
        });
        return list;
    }

    @Override
    public void nextGeneration() {
        MapBasedWorld nextGenWorld;
        nextGenWorld = new MapBasedWorld();
        this.addNeighboursCellsToKnownCells();
        this.updateNeighboursCountForKnownCells();
        this.knownCells.forEach((cellCoordinates, cellData) -> {
            CellState nextState;
            nextState = CellStateFactory.getCellState(cellData.isAlive).nextState(this.computeLiveNeighboursCount(cellCoordinates));
            nextGenWorld.setCellAlive(cellCoordinates.getLine(), cellCoordinates.getColumn(), nextState.isAlive());
        });
        this.copyFrom(nextGenWorld);
    }

    private void copyFrom(MapBasedWorld world) {
        if (world != null) {
            this.knownCells = world.knownCells;
            this.linesCount = world.linesCount;
            this.colsCount = world.colsCount;
        }
    }

    private int computeLiveNeighboursCount(CellCoordinates cellCoordinates) {
        return this.computeLiveNeighboursCount(cellCoordinates.getLine(), cellCoordinates.getColumn());
    }

    private int computeLiveNeighboursCount(int line, int col) {
        int count = 0;
        for (int i = line - 1; i <= line + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (!(i == line && j == col) && this.isCellAlive(i, j)) {
                    count++;
                }
            }
        }

        return count;
    }

    private void addNeighboursCellsToKnownCells() {
        Map<CellCoordinates, CellData> newCells = new HashMap<>();
        this.knownCells.forEach((cellCoordinates, cellData) -> {
            if (cellData.isAlive()) {
                for (int line = 0; line <= cellCoordinates.getLine() + 1; line++) {
                    for (int column = 0; column <= cellCoordinates.getColumn() + 1; column++) {
                        newCells.put(new CellCoordinates(line,column),new CellData(isCellAlive(line,column)));
                    }
                }
            }
        });
        this.knownCells.putAll(newCells);
    }

    private void updateNeighboursCountForKnownCells() {
        this.knownCells.forEach((cellCoordinates, cellData) -> {
            if (!cellData.isLiveNeighboursCountComputed()) {
                cellData.setLiveNeighboursCount(this.computeLiveNeighboursCount(cellCoordinates));
            }
        });
    }

    private static class CellData {
        private final boolean isAlive;
        private Integer liveNeighboursCount;

        CellData(boolean isAlive) {
            this.isAlive = isAlive;
            this.liveNeighboursCount = null;
        }

        boolean isAlive() {
            return isAlive;
        }

        public int getLiveNeighboursCount() {
            return liveNeighboursCount;
        }

        void setLiveNeighboursCount(int liveNeighboursCount) {
            this.liveNeighboursCount = liveNeighboursCount;
        }

        public void resetLiveNeighboursCountComputed() {
            this.liveNeighboursCount = null;
        }

        boolean isLiveNeighboursCountComputed() {
            return this.liveNeighboursCount != null;
        }
    }
}
