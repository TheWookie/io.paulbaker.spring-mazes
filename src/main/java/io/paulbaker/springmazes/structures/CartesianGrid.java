package io.paulbaker.springmazes.structures;

import java.util.function.Consumer;

/**
 * Created by paulbaker on 8/21/16.
 */
public interface CartesianGrid extends Iterable<SimpleCartesianCell> {

    void prepareGrid();

    void configureGrid();

    default void configureGrid(Consumer<SimpleCartesianCell> action) {
        iterator().forEachRemaining(action);
    }

    int getRows();

    int getColumns();

    default int size() {
        return getRows() * getColumns();
    }

    SimpleCartesianCell getCell(int row, int column);

    SimpleCartesianCell getRandomCell();

    String toDisplayString();
}
