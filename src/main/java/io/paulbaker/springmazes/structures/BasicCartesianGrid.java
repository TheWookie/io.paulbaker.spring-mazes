package io.paulbaker.springmazes.structures;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

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
public class BasicCartesianGrid implements CartesianGrid {

    @Getter
    private int rows, columns;
    private BasicCartesianCell[][] cells;
    private Random random;

    public BasicCartesianGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        System.out.println("rows: " + rows + ", columns: " + columns);
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
        final int cellEdgeSize = 32;

        BufferedImage buffer = new BufferedImage(getColumns() * cellEdgeSize, getRows() * cellEdgeSize, BufferedImage.TYPE_INT_RGB);
        System.out.println("Width: " + buffer.getWidth() + ", Height: " + buffer.getHeight());

        Color white = new Color(255, 255, 255);
        for (int i = 0; i < buffer.getWidth(); i++) {
            for (int j = 0; j < buffer.getHeight(); j++) {
                buffer.setRGB(i, j, white.getRGB());
            }
        }

        Color black = new Color(0, 0, 0);
        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                System.out.println("row:" + row + ", col:" + column);
                BasicCartesianCell cell = getCell(row, column);
                int xOffset = cellEdgeSize * row;
                int yOffset = cellEdgeSize * column;
                if (!cell.hasNorth() || !cell.isLinked(cell.getNorth())) {
                    System.out.println("Walling north");
                    for (int i = 0; i < cellEdgeSize; i++) {
                        buffer.setRGB(xOffset + i, yOffset, black.getRGB());
                    }
                }
                if (!cell.hasWest() || !cell.isLinked(cell.getWest())) {
                    System.out.println("Walling west");
                    for (int i = 0; i < cellEdgeSize; i++) {
                        buffer.setRGB(xOffset, yOffset + i, black.getRGB());
                    }
                }

                if (!cell.hasSouth() || !cell.isLinked(cell.getSouth())) {
                    System.out.println("Walling south");
                    for (int i = 0; i < cellEdgeSize - 1; i++) {
                        buffer.setRGB(xOffset + i, yOffset + cellEdgeSize - 1, black.getRGB());
                    }
                }
                if (!cell.hasEast() || !cell.isLinked(cell.getEast())) {
                    System.out.println("Walling east");
                    for (int i = 0; i < cellEdgeSize - 1; i++) {
                        buffer.setRGB(xOffset + cellEdgeSize - 1, yOffset + i, black.getRGB());
                    }
                }
            }
        }

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
