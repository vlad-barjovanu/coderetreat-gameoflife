package com.vbarjovanu.coderetreat.gameoflife.world;

import com.vbarjovanu.coderetreat.gameoflife.CellCoordinates;

import java.util.List;

public interface World {
    int getLinesCount();

    int getColsCount();

    void setCellAlive(int line, int col, boolean alive);

    boolean isCellAlive(int line, int col);

    List<CellCoordinates> getAliveCells();

    void nextGeneration();
}
