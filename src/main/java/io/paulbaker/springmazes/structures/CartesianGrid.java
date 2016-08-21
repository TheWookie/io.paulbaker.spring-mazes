package io.paulbaker.springmazes.structures;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;

/**
 * Created by paulbaker on 8/21/16.
 */
public interface CartesianGrid extends Iterable<BasicCartesianCell> {

    void prepareGrid();

    void configureGrid();

    default void configureGrid(Consumer<BasicCartesianCell> action) {
        iterator().forEachRemaining(action);
    }

    int getRows();

    int getColumns();

    default int size() {
        return getRows() * getColumns();
    }

    BasicCartesianCell getCell(int row, int column);

    BasicCartesianCell getRandomCell();

    String toDisplayString();

    BufferedImage toDisplayImage();
}
