package io.paulbaker.springmazes.structures;

import io.paulbaker.springmazes.algorithms.CellAlgorithms;
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
public class MazeController {

    private Random random = new Random();

    @RequestMapping(method = RequestMethod.GET, path = "/maze")
    @ResponseBody
    public String simpleMaze() {
        return simpleMaze(random.nextInt(50) + 1, random.nextInt(50) + 1);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/maze", params = {"rows", "columns"})
    @ResponseBody
    public String simpleMaze(@RequestParam(name = "rows") int rows, @RequestParam(name = "columns") int columns) {
        SimpleCartesianGrid requestedMaze = new SimpleCartesianGrid(rows, columns);

        requestedMaze.forEach(CellAlgorithms.BINARY_TREE);

        return requestedMaze.toDisplayString();
    }
}
