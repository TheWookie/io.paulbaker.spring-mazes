package io.paulbaker.springmazes;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by paulbaker on 8/21/16.
 */
@ToString
@EqualsAndHashCode
public class Cell {
    @Getter
    private int row, column;
    @Setter
    @Getter
    private Cell north, east, south, west;
    private Set<Cell> links;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        links = new HashSet<>();
    }

    public void link(Cell adjacent) {
        link(adjacent, true);
    }

    public void link(Cell adjacent, boolean bidirectional) {
        links.add(adjacent);
        if (bidirectional) {
            adjacent.link(this, false);
        }
    }

    public void unlink(Cell adjacent) {
        unlink(adjacent, true);
    }

    public void unlink(Cell adjacent, boolean bidirectional) {
        links.remove(adjacent);
        if (bidirectional) {
            adjacent.unlink(this, false);
        }
    }

    public Set<Cell> getLinks() {
        return Collections.unmodifiableSet(links);
    }

    public Set<Cell> getNeighbors() {
        Set<Cell> neighbors = new HashSet<>();
        if (north != null)
            neighbors.add(north);
        if (south != null)
            neighbors.add(south);
        if (east != null)
            neighbors.add(east);
        if (west != null)
            neighbors.add(west);
        return Collections.unmodifiableSet(neighbors);
    }
}
