package io.paulbaker.springmazes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by paulbaker on 8/21/16.
 */
@Controller
public class MazeController {


    @RequestMapping(method = RequestMethod.GET, path = "/maze"/*, params = {"rows", "columns"}*/)
    @ResponseBody
    public String simpleMaze(@RequestParam(name = "rows") int rows, @RequestParam(name = "columns") int columns) {
        Grid requestedMaze = new Grid(rows, columns);
        return requestedMaze.toDisplayString();
    }
}
