package com.vbarjovanu.coderetreat.gameoflife.world;

import com.vbarjovanu.coderetreat.gameoflife.CellCoordinates;
import com.vbarjovanu.coderetreat.gameoflife.state.CellStateFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ViewportMatrixWorld implements World {

    private int linesCount;
    private int colsCount;
    private List<CellCoordinates> liveCells;

    public ViewportMatrixWorld() {
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
        CellCoordinates cellC = new CellCoordinates(line, col);

        if (alive) {
            this.liveCells.add(cellC);
        } else
            this.liveCells.remove(cellC);
        this.linesCount = Math.max(this.linesCount, line + 1 + (alive ? 1 : 0));
        this.colsCount = Math.max(this.colsCount, col + 1 + (alive ? 1 : 0));
    }

    @Override
    public boolean isCellAlive(int line, int col) {
        CellCoordinates cellCoordinates = new CellCoordinates(line, col);
        return this.liveCells.contains(cellCoordinates);
    }

    @Override
    public List<CellCoordinates> getAliveCells() {
        return new ArrayList<>(this.liveCells);
    }

    @Override
    public void nextGeneration() {
        ViewportMatrixWorld nextGenWorld;
        boolean stillHasWork;
        int minLine, minCol, maxLine, maxCol, batchSize;
        nextGenWorld = new ViewportMatrixWorld();
        batchSize = 100;
        minLine = 0;
        minCol = 0;
        maxLine = Math.min(minLine + batchSize, this.linesCount) - 1;
        maxCol = Math.min(minCol + batchSize, this.colsCount) - 1;
        do {
            stillHasWork = true;
            this.nextGenWorldInViewPort(nextGenWorld, minLine, minCol, maxLine, maxCol);
            minCol = maxCol;
            maxCol = Math.min(minCol + batchSize, this.colsCount) - 1;
            if (minCol == maxCol) {
                minCol = 0;
                minLine = maxLine;
                maxLine = Math.min(minLine + batchSize, this.linesCount) - 1;
                if (minLine == maxLine) {
                    stillHasWork = false;
                }
            }
        } while (stillHasWork);
        this.copyFrom(nextGenWorld);
    }

    private void nextGenWorldInViewPort(ViewportMatrixWorld nextGenWorld, int minLine, int minCol, int maxLine, int maxCol) {
        boolean[][] viewPortCells;
        int lineCount, colCount;
        viewPortCells = this.buildViewPort(minLine, minCol, maxLine, maxCol);
        lineCount = maxLine - minLine + 1;
        colCount = maxCol - minCol + 1;

        for (int line = 0; line < lineCount; line++) {
            for (int col = 0; col < colCount; col++) {
                boolean alive;
                int liveNeighboursCount;
                liveNeighboursCount = this.countLiveNeighbours(viewPortCells, line + 1, col + 1);
                alive = CellStateFactory.getCellState(viewPortCells[line + 1][col + 1]).nextState(liveNeighboursCount).isAlive();
                nextGenWorld.setCellAlive(line + minLine, col + minCol, alive);
            }
        }
    }

    private int countLiveNeighbours(boolean[][] viewPortCells, int cellLine, int cellCol) {
        int count = 0;
        for (int line = cellLine - 1; line <= cellLine + 1; line++) {
            for (int col = cellCol - 1; col <= cellCol + 1; col++) {
                if (line >= 0 && line < viewPortCells.length && col >= 0 && col < viewPortCells[line].length && !(line == cellLine && col == cellCol)) {
                    count += viewPortCells[line][col] ? 1 : 0;
                }
            }
        }
        return count;
    }

    private boolean[][] buildViewPort(int minLine, int minCol, int maxLine, int maxCol) {
        //the viewport is 1 line and 1 col bigger at begin and 1 line and 1 col bigger at end
        minLine--;
        minCol--;
        maxLine++;
        maxCol++;
        int lines = maxLine - minLine + 1;
        int cols = maxCol - minCol + 1;
        boolean[][] viewPort = new boolean[lines][cols];
        for (CellCoordinates cell : this.liveCells) {
            if (cell.getLine() >= minLine && cell.getLine() <= maxLine && cell.getColumn() >= minCol && cell.getColumn() <= maxCol) {
                viewPort[cell.getLine() - minLine][cell.getColumn() - minCol] = true;
            }
        }
        return viewPort;
    }

    private void copyFrom(ViewportMatrixWorld world) {
        this.liveCells = world.getAliveCells();
        this.linesCount = world.linesCount;
        this.colsCount = world.colsCount;
    }

    static class CellCordinatesComparator implements Comparator<CellCoordinates> {
        @Override
        public int compare(CellCoordinates o1, CellCoordinates o2) {
            int result = Integer.compare(o1.getLine(), o2.getLine());
            if (result == 0) {
                result = Integer.compare(o1.getColumn(), o2.getColumn());
            }
            return result;
        }
    }
}
