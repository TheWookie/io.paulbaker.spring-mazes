package io.paulbaker.springmazes;

/**
 * Created by paulbaker on 8/21/16.
 */
public interface PreparableGrid extends Iterable<Cell> {

    void prepareGrid();

    void configureGrid();

    Cell getCell(int row, int column);

    String toDisplayString();
}
