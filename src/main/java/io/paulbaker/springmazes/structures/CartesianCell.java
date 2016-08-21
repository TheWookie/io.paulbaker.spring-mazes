package io.paulbaker.springmazes.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    default boolean hasNorth() {
        return getNorth() != null;
    }

    default boolean hasSouth() {
        return getSouth() != null;
    }

    default boolean hasEast() {
        return getEast() != null;
    }

    default boolean hasWest() {
        return getWest() != null;
    }

    default List<CartesianCell> getNeighbors() {
        List<CartesianCell> neighbors = new ArrayList<>();

        if (hasNorth())
            neighbors.add(getNorth());
        if (hasSouth())
            neighbors.add(getSouth());
        if (hasEast())
            neighbors.add(getEast());
        if (hasWest())
            neighbors.add(getWest());

        return Collections.unmodifiableList(neighbors);
    }
}
