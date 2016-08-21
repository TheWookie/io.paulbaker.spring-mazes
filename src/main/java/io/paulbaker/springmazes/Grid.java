package io.paulbaker.springmazes;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/**
 * Created by paulbaker on 8/21/16.
 */
@ToString
@EqualsAndHashCode
public class Grid implements PreparableGrid {
    private int rows, columns;
    private Cell[][] cells;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        prepareGrid();
        configureGrid();
    }

    @Override
    public void prepareGrid() {
        cells = new Cell[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cells[row][column] = new Cell(row, column);
            }
        }
    }

    @Override
    public void configureGrid() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Cell currentCell = cells[row][column];
                currentCell.setNorth(getCell(row - 1, column));
                currentCell.setSouth(getCell(row + 1, column));
                currentCell.setEast(getCell(row, column + 1));
                currentCell.setWest(getCell(row, column - 1));
            }
        }
    }

    @Override
    public Cell getCell(int row, int column) {
        if (row < 0 || column < 0)
            return null;
        if (row >= rows || column >= columns)
            return null;
        return cells[row][column];
    }

    @Override
    public String toDisplayString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                sb.append('0');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private class CellIterator implements Iterator<Cell> {

        private int rows, columns, index;

        CellIterator(int rows, int columns) {
            index = 0;
            this.rows = rows;
            this.columns = columns;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return index < (rows * columns) - 1;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Cell next() {
            int row = index / columns;
            int column = index % columns;
            return cells[row][column];
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Cell> iterator() {
        return new CellIterator(rows, columns);
    }

    /**
     * Performs the given action for each element of the {@code Iterable}
     * until all elements have been processed or the action throws an
     * exception.  Unless otherwise specified by the implementing class,
     * actions are performed in the order of iteration (if an iteration order
     * is specified).  Exceptions thrown by the action are relayed to the
     * caller.
     *
     * @param action The action to be performed for each element
     * @throws NullPointerException if the specified action is null
     * @implSpec <p>The default implementation behaves as if:
     * <pre>{@code
     *     for (T t : this)
     *         action.accept(t);
     * }</pre>
     * @since 1.8
     */
    @Override
    public void forEach(Consumer<? super Cell> action) {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                action.accept(cell);
            }
        }
    }
}
