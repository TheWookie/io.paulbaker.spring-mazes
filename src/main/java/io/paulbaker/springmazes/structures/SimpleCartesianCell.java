package io.paulbaker.springmazes.structures;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by paulbaker on 8/21/16.
 */
public class SimpleCartesianCell implements CartesianCell {
    @Getter
    private int row, column;
    @Setter
    @Getter
    private SimpleCartesianCell north, east, south, west;
    private Set<LinkableCell> links;

    public SimpleCartesianCell(int row, int column) {
        this.row = row;
        this.column = column;
        links = new HashSet<>();
    }

    @Override
    public boolean isLinked(LinkableCell other) {
        return links.contains(other);
    }

    @Override
    public void link(LinkableCell adjacent, boolean bidirectional) {
        links.add(adjacent);
        if (bidirectional) {
            adjacent.link(this, false);
        }
    }

    @Override
    public void unlink(LinkableCell adjacent, boolean bidirectional) {
        links.remove(adjacent);
        if (bidirectional) {
            adjacent.unlink(this, false);
        }
    }

    @Override
    public Set<LinkableCell> getLinks() {
        return Collections.unmodifiableSet(links);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleCartesianCell that = (SimpleCartesianCell) o;

        if (row != that.row) return false;
        return column == that.column;

    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%03d, %03d)", getRow(), getColumn());
    }
}
