package io.paulbaker.springmazes.structures;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Created by paulbaker on 8/21/16.
 */
@ToString
@EqualsAndHashCode
@Log4j
public class BasicCartesianGrid implements CartesianGrid {

    @Getter
    private int rows, columns;
    private BasicCartesianCell[][] cells;
    private Random random;

    public BasicCartesianGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        log.debug("rows: " + rows + ", columns: " + columns);
        random = new Random();
        prepareGrid();
        configureGrid();
    }

    @Override
    public void prepareGrid() {
        cells = new BasicCartesianCell[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cells[row][column] = new BasicCartesianCell(row, column);
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
    public BasicCartesianCell getCell(int row, int column) {
        if (row < 0 || column < 0)
            return null;
        if (row >= rows || column >= columns)
            return null;
        return cells[row][column];
    }

    @Override
    public BasicCartesianCell getRandomCell() {
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
                BasicCartesianCell currentCell = getCell(i, j);
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

    @Override
    public BufferedImage toDisplayImage() {
//        final int cellSize = (int) (32 * (random.nextDouble() + 1));
        final int cellSize = 32;

        int rows = getRows();
        int columns = getColumns();

        BufferedImage buffer = new BufferedImage(columns * cellSize, rows * cellSize, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = buffer.createGraphics();

        graphics.setColor(new Color(180, 180, 255));
        graphics.fillRect(0, 0, buffer.getWidth() - 1, buffer.getHeight() - 1);
        graphics.setColor(new Color(40, 40, 90));
        graphics.drawRect(0, 0, buffer.getWidth() - 1, buffer.getHeight() - 1);

        forEach(cell -> {
            int x = cell.getColumn() * cellSize;
            int y = cell.getRow() * cellSize;
            if (cell.hasNorth() && !cell.isLinked(cell.getNorth())) {
                graphics.drawLine(x, y, x + cellSize, y);
            }
            if (cell.hasEast() && !cell.isLinked(cell.getEast())) {
                graphics.drawLine(x + cellSize - 1, y, x + cellSize - 1, y + cellSize);
            }
            if (cell.hasSouth() && !cell.isLinked(cell.getSouth())) {
                graphics.drawLine(x, y + cellSize - 1, x + cellSize, y + cellSize - 1);
            }
            if (cell.hasWest() && !cell.isLinked(cell.getWest())) {
                graphics.drawLine(x, y, x, y + cellSize);
            }
        });

        return buffer;
    }

    private class CellIterator implements Iterator<BasicCartesianCell> {

        private int rows, columns, index;

        CellIterator(int rows, int columns) {
            index = 0;
            this.rows = rows;
            this.columns = columns;
        }

        @Override
        public boolean hasNext() {
            return index < (rows * columns);
        }

        @Override
        public BasicCartesianCell next() {
            int row = index / columns;
            int column = index % columns;
            index++;
            return cells[row][column];
        }
    }

    @Override
    public Iterator<BasicCartesianCell> iterator() {
        return new CellIterator(rows, columns);
    }

    @Override
    public void forEach(Consumer<? super BasicCartesianCell> action) {
        iterator().forEachRemaining(action);
    }

    public void forEachRow(Consumer<? super BasicCartesianCell[]> action) {
        for (BasicCartesianCell[] row : cells) {
            action.accept(row);
        }
    }
}
