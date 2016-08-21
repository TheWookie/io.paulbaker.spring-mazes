package io.paulbaker.springmazes.algorithms;

import io.paulbaker.springmazes.structures.BasicCartesianCell;
import io.paulbaker.springmazes.structures.CartesianCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Created by paulbaker on 8/21/16.
 */
public class CellAlgorithms {

    private static Random RANDOM = new Random();

    public static Consumer<CartesianCell> UNLINK_ALL = cell -> cell.getLinks().forEach(adjCell -> adjCell.unlink(cell));

    public static Consumer<BasicCartesianCell> BINARY_TREE = cell -> {
        List<BasicCartesianCell> neighbors = new ArrayList<>();
        if (cell.hasNorth())
            neighbors.add(cell.getNorth());
        if (cell.hasEast())
            neighbors.add(cell.getEast());

        if (!neighbors.isEmpty()) {
            int index = RANDOM.nextInt(neighbors.size());
            BasicCartesianCell chosenNeighbor = neighbors.get(index);
            cell.link(chosenNeighbor);
        }
    };

    public static Consumer<BasicCartesianCell> SIDEWINDER = cell -> {

    };

    private CellAlgorithms() {
    }
}
