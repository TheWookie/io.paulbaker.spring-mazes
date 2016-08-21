package io.paulbaker.springmazes.structures;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Iterator;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Created by paulbaker on 8/21/16.
 */
@ToString
@EqualsAndHashCode
public class SimpleCartesianGrid implements CartesianGrid {

    @Getter
    private int rows, columns;
    private SimpleCartesianCell[][] cells;
    private Random random;

    public SimpleCartesianGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        random = new Random();
        prepareGrid();
        configureGrid();
    }

    @Override
    public void prepareGrid() {
        cells = new SimpleCartesianCell[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cells[row][column] = new SimpleCartesianCell(row, column);
            }
        }
    }

    @Override
    public void configureGrid() {
        configureGrid(cell -> {
            int row = cell.getRow();
            int column = cell.getColumn();
            cell.setNorth(getCell(row - 1, column));
            cell.setSouth(getCell(row + 1, column));
            cell.setEast(getCell(row, column + 1));
            cell.setWest(getCell(row, column - 1));
        });
    }

    @Override
    public SimpleCartesianCell getCell(int row, int column) {
        if (row < 0 || column < 0)
            return null;
        if (row >= rows || column >= columns)
            return null;
        return cells[row][column];
    }

    @Override
    public SimpleCartesianCell getRandomCell() {
        int row = random.nextInt(getRows());
        int column = random.nextInt(getColumns());
        return getCell(row, column);
    }

    @Override
    public String toDisplayString() {
        String emptyHorizontal = "   ";
        String takenHorizontal = "---";
        String emptyVertical = " ";
        String takenVertical = "|";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getRows(); i++) {
            StringBuilder top = new StringBuilder();
            StringBuilder mid = new StringBuilder();
            StringBuilder bot = new StringBuilder();
            for (int j = 0; j < getColumns(); j++) {
                SimpleCartesianCell currentCell = getCell(i, j);
                if (i == 0) {
                    if (j == 0)
                        top.append('+');
                    if (currentCell.isLinked(currentCell.getNorth())) {
                        top.append(emptyHorizontal);
                    } else {
                        top.append(takenHorizontal);
                    }
                    top.append('+');
                }

                if (j == 0) {
                    if (currentCell.isLinked(currentCell.getWest())) {
                        mid.append(emptyVertical);
                    } else {
                        mid.append(takenVertical);
                    }
                }
                mid.append(emptyHorizontal);
                if (currentCell.isLinked(currentCell.getEast())) {
                    mid.append(emptyVertical);
                } else {
                    mid.append(takenVertical);
                }

                if (j == 0)
                    bot.append('+');
                if (currentCell.isLinked(currentCell.getSouth())) {
                    bot.append(emptyHorizontal);
                } else {
                    bot.append(takenHorizontal);
                }
                bot.append('+');
            }
            if (top.length() != 0)
                sb.append(top).append('\n');
            if (mid.length() != 0)
                sb.append(mid).append('\n');
            if (bot.length() != 0)
                sb.append(bot).append('\n');
        }
        return sb.toString();
    }

    private class CellIterator implements Iterator<SimpleCartesianCell> {

        private int rows, columns, index;

        CellIterator(int rows, int columns) {
            index = 0;
            this.rows = rows;
            this.columns = columns;
        }

        @Override
        public boolean hasNext() {
            return index < (rows * columns) - 1;
        }

        @Override
        public SimpleCartesianCell next() {
            int row = index / columns;
            int column = index % columns;
            index++;
            return cells[row][column];
        }
    }

    @Override
    public Iterator<SimpleCartesianCell> iterator() {
        return new CellIterator(rows, columns);
    }

    @Override
    public void forEach(Consumer<? super SimpleCartesianCell> action) {
        iterator().forEachRemaining(action);
    }

    public void forEachRow(Consumer<? super SimpleCartesianCell[]> action) {
        for (SimpleCartesianCell[] row : cells) {
            action.accept(row);
        }
    }
}
