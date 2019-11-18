package com.vbarjovanu.coderetreat.gameoflife;

import java.util.Objects;

public class CellCoordinates {
    private final int line;
    private final int column;

    public CellCoordinates(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CellCoordinates) {
            return this.equals((CellCoordinates) obj);
        }
        return super.equals(obj);
    }

    private boolean equals(CellCoordinates cellCoordinates) {
        return cellCoordinates != null && this.getLine() == cellCoordinates.getLine() && this.getColumn() == cellCoordinates.getColumn();
    }

    public boolean areEqual(int line, int column) {
        return this.line == line && this.column == column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line,column);
    }
}
