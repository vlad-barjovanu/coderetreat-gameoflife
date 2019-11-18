package com.vbarjovanu.coderetreat.gameoflife.world;

import com.vbarjovanu.coderetreat.gameoflife.CellCoordinates;
import com.vbarjovanu.coderetreat.gameoflife.state.CellState;
import com.vbarjovanu.coderetreat.gameoflife.state.CellStateFactory;

import java.util.*;

/**
 * Not memory optimized and perf optimized world that stores a matrix with all world's cells
 */
public class MatrixBasedWorld implements World {
    private boolean[][] cells;
    private int linesCount, colsCount;

    public MatrixBasedWorld() {
        this.linesCount = 0;
        this.colsCount = 0;
        this.cells = new boolean[0][0];
    }

    public int getLinesCount() {
        return linesCount;
    }

    public int getColsCount() {
        return colsCount;
    }

    public void setCellAlive(int line, int col, boolean alive) {
        int newLinesCount = line + 1 + (alive ? 1 : 0);
        int newColsCount = col + 1 + (alive ? 1 : 0);
        if (this.linesCount < newLinesCount || this.colsCount < newColsCount) {
            this.linesCount = Math.max(newLinesCount, this.linesCount);
            this.colsCount = Math.max(newColsCount, this.colsCount);
            boolean[][] cells = new boolean[linesCount][colsCount];
            this.cells = copyCells(this.cells, cells);
        }
        this.cells[line][col] = alive;
    }

    public boolean isCellAlive(int line, int col) {
        try {
            return this.cells[line][col];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public List<CellCoordinates> getAliveCells() {
        List<CellCoordinates> list;
        list = new ArrayList<>();
        for (int line = 0; line < cells.length; line++) {
            for (int col = 0; col < cells[line].length; col++) {
                if (cells[line][col]) {
                    list.add(new CellCoordinates(line, col));
                }
            }
        }
        return list;
    }

    public void nextGeneration() {
        CellState cellState;
        MatrixBasedWorld nextGenMatrixBasedWorld;

        nextGenMatrixBasedWorld = new MatrixBasedWorld();
        for (int line = 0; line < cells.length; line++) {
            for (int col = 0; col < cells[line].length; col++) {
                cellState = this.getCellState(line, col);
                nextGenMatrixBasedWorld.setCellAlive(line, col, cellState.nextState(this.getAliveNeighboursCount(line, col)).isAlive());
            }
        }
        this.copyFrom(nextGenMatrixBasedWorld);
    }

    private boolean[][] copyCells(boolean[][] cellsSource, boolean[][] cellsDest) {
        for (int i = 0; i < cellsSource.length; i++) {
            System.arraycopy(cellsSource[i], 0, cellsDest[i], 0, cellsSource[i].length);
        }
        return cellsDest;
    }

    private CellState getCellState(int line, int col) {
        CellState cellState;
        try {
            cellState = CellStateFactory.getCellState(this.cells[line][col]);
        } catch (ArrayIndexOutOfBoundsException e) {
            cellState = CellStateFactory.getCellState(false);
        }
        return cellState;

    }

    private void copyFrom(MatrixBasedWorld world) {
        if (world != null) {
            this.cells = world.cells;
            this.linesCount = world.linesCount;
            this.colsCount = world.colsCount;
        }
    }

    private int getAliveNeighboursCount(int line, int col) {
        int count = 0;
        for (int i = line - 1; i <= line + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (!(i == line && j == col)) {
                    count += (this.isCellAlive(i, j) ? 1 : 0);
                }
            }
        }
        return count;
    }
}
