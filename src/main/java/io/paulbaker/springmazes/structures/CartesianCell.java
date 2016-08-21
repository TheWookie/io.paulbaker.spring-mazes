package io.paulbaker.springmazes.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Created by paulbaker on 8/21/16.
 */
public interface CartesianCell extends LinkableCell {
    int getRow();

    int getColumn();

    CartesianCell getNorth();

    CartesianCell getSouth();

    CartesianCell getEast();

    CartesianCell getWest();

    default List<CartesianCell> getNeighbors() {
        List<CartesianCell> neighbors = new ArrayList<>();

        BiConsumer<CartesianCell, List<CartesianCell>> addNeighbor = (cell, list) -> {
            if (cell != null)
                list.add(cell);
        };
        addNeighbor.accept(getNorth(), neighbors);
        addNeighbor.accept(getSouth(), neighbors);
        addNeighbor.accept(getEast(), neighbors);
        addNeighbor.accept(getWest(), neighbors);

        return Collections.unmodifiableList(neighbors);
    }
}
