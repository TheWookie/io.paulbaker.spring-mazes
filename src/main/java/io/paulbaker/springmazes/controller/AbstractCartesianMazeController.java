package io.paulbaker.springmazes.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by paulbaker on 8/24/16.
 */
public abstract class AbstractCartesianMazeController {

    private final MazeSupplier mazeSupplier;

    public AbstractCartesianMazeController(MazeSupplier mazeSupplier) {
        this.mazeSupplier = mazeSupplier;
    }

    @RequestMapping(params = {"format=text"}, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String mazeText(@RequestParam(name = "rows", defaultValue = "10", required = false) int rows,
                           @RequestParam(name = "columns", defaultValue = "10", required = false) int columns) throws IOException {
        return mazeSupplier.Supply(rows, columns).toDisplayString();
    }

    @RequestMapping(params = {"format=image"}, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public BufferedImage mazeImage(@RequestParam(name = "rows", defaultValue = "10", required = false) int rows,
                                   @RequestParam(name = "columns", defaultValue = "10", required = false) int columns) throws IOException {
        return mazeSupplier.Supply(rows, columns).toDisplayImage();
    }

    @RequestMapping
    @ResponseBody
    public ResponseEntity<String> mazeInvalid(@RequestParam(name = "format") String invalidFormat) throws IOException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>("Invalid format: " + invalidFormat, headers, HttpStatus.BAD_REQUEST);
    }
}
