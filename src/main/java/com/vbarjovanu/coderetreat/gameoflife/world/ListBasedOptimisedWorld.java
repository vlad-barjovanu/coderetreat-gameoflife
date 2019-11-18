package com.vbarjovanu.coderetreat.gameoflife.world;

import com.vbarjovanu.coderetreat.gameoflife.CellCoordinates;
import com.vbarjovanu.coderetreat.gameoflife.state.CellStateFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ListBasedOptimisedWorld implements World {
    private int linesCount;
    private int colsCount;
    private List<CellCoordinates> liveCells;

    public ListBasedOptimisedWorld() {
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
            if (!liveCells.contains(cellC)) {
                liveCells.add(cellC);
            }
        } else {
            liveCells.remove(cellC);
        }
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
        ListBasedOptimisedWorld nextGenWorld;
        CellCoordinates cell;
        NeighboursData neighbours;
        NeighboursData deadCellNeighbours;

        nextGenWorld = new ListBasedOptimisedWorld();
        liveCells.sort(new CellCordinatesComparator());
        for (int index = 0; index < liveCells.size(); index++) {
            cell = this.liveCells.get(index);
            neighbours = getNeighbours(cell.getLine(), cell.getColumn(), index);
            int liveNeighboursCount = neighbours.liveCells.size();
            nextGenWorld.setCellAlive(cell.getLine(), cell.getColumn(), CellStateFactory.getCellState(true).nextState(liveNeighboursCount).isAlive());
            for (CellCoordinates deadCell : neighbours.deadCells) {
                deadCellNeighbours = getNeighbours(deadCell.getLine(), deadCell.getColumn(), index);
                liveNeighboursCount = deadCellNeighbours.liveCells.size();
                nextGenWorld.setCellAlive(deadCell.getLine(), deadCell.getColumn(), CellStateFactory.getCellState(false).nextState(liveNeighboursCount).isAlive());
            }
        }
        this.copyFrom(nextGenWorld);
    }

    private void copyFrom(ListBasedOptimisedWorld world) {
        this.liveCells = world.liveCells;
        this.linesCount = world.linesCount;
        this.colsCount = world.colsCount;
    }

    private NeighboursData getNeighbours(int line, int col, int index) {
        List<CellCoordinates> liveCells = new ArrayList<>();
        List<CellCoordinates> deadCells = new ArrayList<>();
        int minLine = Math.max(0, line - 1);
        int maxLine = line + 1;
        int minCol = Math.max(0, col - 1);
        int maxCol = col + 1;
        CellCoordinates cell;

        for (int i = index; i >= 0; i--) {
            cell = this.liveCells.get(i);
            if (isNeighbour(line, col, cell.getLine(), cell.getColumn())) {
                liveCells.add(cell);
            }
            if (cell.getLine() < minLine || (cell.getLine() == minLine && cell.getColumn() < minCol)) {
                break;
            }
        }

        for (int i = index + 1; i < this.liveCells.size(); i++) {
            cell = this.liveCells.get(i);
            if (isNeighbour(line, col, cell.getLine(), cell.getColumn())) {
                liveCells.add(cell);
            }
            if (cell.getLine() > maxLine || (cell.getLine() == maxLine && cell.getColumn() > maxCol)) {
                break;
            }
        }

        for (int l = minLine; l <= maxLine; l++) {
            for (int c = minCol; c <= maxCol; c++) {
                CellCoordinates cellCoordinates = new CellCoordinates(l, c);
                if (!liveCells.contains(cellCoordinates) && isNeighbour(line, col, l, c)) {
                    deadCells.add(cellCoordinates);
                }
            }
        }

        return new NeighboursData(deadCells, liveCells);
    }

    private boolean isNeighbour(int line, int col, int neighLine, int neighColumn) {
        return (neighLine >= line - 1 && neighLine <= line + 1 && neighColumn >= col - 1 && neighColumn <= col + 1) && !(line == neighLine && col == neighColumn);
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

    private static class NeighboursData{
        private final List<CellCoordinates> deadCells;
        private final List<CellCoordinates> liveCells;

        NeighboursData(List<CellCoordinates> deadCells, List<CellCoordinates> liveCells) {
            this.deadCells = deadCells;
            this.liveCells = liveCells;
        }
    }

}
