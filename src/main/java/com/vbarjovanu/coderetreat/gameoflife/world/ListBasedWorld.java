package com.vbarjovanu.coderetreat.gameoflife.world;

import com.vbarjovanu.coderetreat.gameoflife.CellCoordinates;
import com.vbarjovanu.coderetreat.gameoflife.state.CellState;
import com.vbarjovanu.coderetreat.gameoflife.state.CellStateFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Not optimized world that stores only the alive cell coordinates
 * Calculates next state for not alive neighbours, but this may lead to same cell to be processed more than once
 */
public class ListBasedWorld implements World {
    private int linesCount;
    private int colsCount;
    private List<CellCoordinates> liveCells;

    public ListBasedWorld() {
        this.linesCount = 0;
        this.colsCount = 0;
        this.liveCells = new ArrayList<>();
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
        CellCoordinates cellCoordinates = new CellCoordinates(line, col);
        if (alive) {
            if (!liveCells.contains(cellCoordinates)) {
                liveCells.add(cellCoordinates);
            }
        } else {
            liveCells.remove(cellCoordinates);
        }
        this.linesCount = Math.max(this.linesCount, line + 1 + (alive ? 1 : 0));
        this.colsCount = Math.max(this.colsCount, col + 1 + (alive ? 1 : 0));
    }

    @Override
    public boolean isCellAlive(int line, int col) {
        if (this.areCoordinatesWithinBounds(line, col)) {
            CellCoordinates cellCoordinates = new CellCoordinates(line, col);
            return this.liveCells.contains(cellCoordinates);
        }
        return false;
    }

    @Override
    public List<CellCoordinates> getAliveCells() {
        return new ArrayList<>(this.liveCells);
    }

    @Override
    public void nextGeneration() {
        ListBasedWorld nextGenWorld = new ListBasedWorld();
        for (CellCoordinates cellCoordinates : liveCells) {
            CellState cellState;
            cellState = CellStateFactory.getCellState(true).nextState(this.countAliveNeighboursCount(cellCoordinates));
            nextGenWorld.setCellAlive(cellCoordinates.getLine(), cellCoordinates.getColumn(), cellState.isAlive());
            //for dead neighbours
            for (int line = cellCoordinates.getLine() - 1; line <= cellCoordinates.getLine() + 1; line++) {
                for (int col = cellCoordinates.getColumn() - 1; col <= cellCoordinates.getColumn() + 1; col++) {
                    if (!this.isCellAlive(line, col)) {
                        cellState = CellStateFactory.getCellState(false).nextState(this.countAliveNeighboursCount(line, col));
                        nextGenWorld.setCellAlive(line, col, cellState.isAlive());
                    }
                }

            }
        }
        this.copyFrom(nextGenWorld);
    }

    private void copyFrom(ListBasedWorld world) {
        if (world != null) {
            this.liveCells = world.liveCells;
            this.linesCount = world.linesCount;
            this.colsCount = world.colsCount;
        }
    }

    private int countAliveNeighboursCount(CellCoordinates cellCoordinates) {
        return this.countAliveNeighboursCount(cellCoordinates.getLine(), cellCoordinates.getColumn());
    }

    private int countAliveNeighboursCount(int line, int col) {
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

    private boolean areCoordinatesWithinBounds(int line, int col) {
        return line >= 0 && line < this.linesCount && col >= 0 && col < colsCount;
    }
}
