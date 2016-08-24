package io.paulbaker.springmazes;

import io.paulbaker.springmazes.algorithms.CellAlgorithms;
import io.paulbaker.springmazes.structures.BasicCartesianGrid;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by paulbaker on 8/21/16.
 */
@Controller
@RequestMapping(path = "/maze/binarytree", method = RequestMethod.GET)
@Log4j
public class BinaryTreeMazeController {

    @RequestMapping(params = {"format=text"}, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String simpleMazeText(@RequestParam(name = "rows", defaultValue = "10", required = false) int rows,
                                 @RequestParam(name = "columns", defaultValue = "10", required = false) int columns) throws IOException {
        BasicCartesianGrid requestedMaze = new BasicCartesianGrid(rows, columns);
        requestedMaze.forEach(CellAlgorithms.BINARY_TREE);
        return requestedMaze.toDisplayString();
    }

    @RequestMapping(params = {"format=image"}, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public BufferedImage simpleMazeImage(@RequestParam(name = "rows", defaultValue = "10", required = false) int rows,
                                         @RequestParam(name = "columns", defaultValue = "10", required = false) int columns) throws IOException {
        BasicCartesianGrid requestedMaze = new BasicCartesianGrid(rows, columns);
        requestedMaze.forEach(CellAlgorithms.BINARY_TREE);
        return requestedMaze.toDisplayImage();
    }

    @RequestMapping
    @ResponseBody
    public ResponseEntity<String> simpleMazeInvalid(@RequestParam(name = "format") String invalidFormat) throws IOException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>("Invalid format: " + invalidFormat, headers, HttpStatus.BAD_REQUEST);
    }
}
