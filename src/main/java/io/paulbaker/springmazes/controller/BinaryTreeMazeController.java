package io.paulbaker.springmazes.controller;

import io.paulbaker.springmazes.algorithms.CellAlgorithms;
import io.paulbaker.springmazes.structures.BasicCartesianGrid;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by paulbaker on 8/21/16.
 */
@Controller
@RequestMapping(path = "/maze/binary-tree", method = RequestMethod.GET)
@Log4j
public class BinaryTreeMazeController extends AbstractCartesianMazeController {

    public BinaryTreeMazeController() {
        super((rows, columns) -> {
            BasicCartesianGrid basicCartesianGrid = new BasicCartesianGrid(rows, columns);
            basicCartesianGrid.forEach(CellAlgorithms.BINARY_TREE);
            return basicCartesianGrid;
        });
    }
}
