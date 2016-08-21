package io.paulbaker.springmazes;

import io.paulbaker.springmazes.algorithms.CellAlgorithms;
import io.paulbaker.springmazes.structures.BasicCartesianGrid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

/**
 * Created by paulbaker on 8/21/16.
 */
@Controller
@RequestMapping(path = "/maze")
public class MazeController {

    private Random random = new Random();

    @RequestMapping(method = RequestMethod.GET, path = "/maze")
    @ResponseBody
    public String simpleMaze() {
        return simpleMaze(random.nextInt(50) + 1, random.nextInt(50) + 1);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/basic", params = {"rows", "columns"})
    @ResponseBody
    public String simpleMaze(@RequestParam(name = "rows") int rows, @RequestParam(name = "columns") int columns) {
        BasicCartesianGrid requestedMaze = new BasicCartesianGrid(rows, columns);

        requestedMaze.forEach(CellAlgorithms.BINARY_TREE);

        return requestedMaze.toDisplayString();
    }
}
