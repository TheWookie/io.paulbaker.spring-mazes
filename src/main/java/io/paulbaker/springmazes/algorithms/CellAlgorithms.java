package io.paulbaker.springmazes.algorithms;

import io.paulbaker.springmazes.structures.CartesianCell;
import io.paulbaker.springmazes.structures.SimpleCartesianCell;

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

    public static Consumer<SimpleCartesianCell> BINARY_TREE = cell -> {
        List<SimpleCartesianCell> neighbors = new ArrayList<>();
        SimpleCartesianCell north = cell.getNorth();
        SimpleCartesianCell east = cell.getEast();
        if (north != null)
            neighbors.add(north);
        if (east != null)
            neighbors.add(east);

        if (!neighbors.isEmpty()) {
            int index = RANDOM.nextInt(neighbors.size());
            SimpleCartesianCell chosenNeighbor = neighbors.get(index);
            cell.link(chosenNeighbor);
        }
    };

    private CellAlgorithms() {
    }
}
