package io.paulbaker.springmazes.structures;

import java.util.Set;

/**
 * Created by paulbaker on 8/21/16.
 */
public interface LinkableCell {

    boolean isLinked(LinkableCell other);

    default void link(LinkableCell adjacent) {
        link(adjacent, true);
    }

    void link(LinkableCell adjacent, boolean bidirectional);

    default void unlink(LinkableCell adjacent) {
        unlink(adjacent, true);
    }

    void unlink(LinkableCell adjacent, boolean bidirectional);

    Set<LinkableCell> getLinks();
}
