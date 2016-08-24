package io.paulbaker.springmazes;

import io.paulbaker.springmazes.algorithms.CellAlgorithms;
import io.paulbaker.springmazes.structures.BasicCartesianGrid;
import lombok.extern.log4j.Log4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by paulbaker on 8/21/16.
 */
@Controller
@RequestMapping(path = "/maze")
@Log4j
public class MazeController {
    
    @RequestMapping(path = "/basic", method = RequestMethod.GET, params = {"format", "format=text"}, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String simpleMazeText(@RequestParam(name = "rows", defaultValue = "10", required = false) int rows,
                                 @RequestParam(name = "columns", defaultValue = "10", required = false) int columns) throws IOException {
        BasicCartesianGrid requestedMaze = new BasicCartesianGrid(rows, columns);
        requestedMaze.forEach(CellAlgorithms.BINARY_TREE);
        return requestedMaze.toDisplayString();
    }

    @RequestMapping(path = "/basic", method = RequestMethod.GET, params = {"format=image"}, produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public BufferedImage simpleMazeImage(@RequestParam(name = "rows", defaultValue = "10", required = false) int rows,
                                         @RequestParam(name = "columns", defaultValue = "10", required = false) int columns) throws IOException {
        log.debug("Starting image generation");
        BasicCartesianGrid requestedMaze = new BasicCartesianGrid(rows, columns);
        requestedMaze.forEach(CellAlgorithms.BINARY_TREE);
        log.debug("Returning from image generation");
        return requestedMaze.toDisplayImage();
    }

    @RequestMapping(path = "/basic", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> simpleMazeInvalid(@RequestParam(name = "rows", defaultValue = "10", required = false) int rows,
                                                    @RequestParam(name = "columns", defaultValue = "10", required = false) int columns,
                                                    @RequestParam(name = "format") String format) throws IOException {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        return new ResponseEntity<>("Invalid format: " + format, headers, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/image")
    @ResponseBody
    public ResponseEntity<byte[]> image(@RequestParam(name = "rows", defaultValue = "10", required = false) int rows,
                                        @RequestParam(name = "columns", defaultValue = "10", required = false) int columns) throws IOException, InterruptedException {
        try {
            BasicCartesianGrid requestedMaze = new BasicCartesianGrid(rows, columns);
            requestedMaze.forEach(CellAlgorithms.BINARY_TREE);
            BufferedImage bufferedImage = requestedMaze.toDisplayImage();
            {   // Dumping to file for debugging <- this works as expected
                File outputFile = new File("save.png");
                ImageIO.write(bufferedImage, "png", outputFile);
            }
            ByteArrayOutputStream pngByteStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", pngByteStream);
            byte[] pngBytes = pngByteStream.toByteArray();
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(pngBytes.length);
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());

            return new ResponseEntity<>(pngBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            // This hasn't occurred yet, but is for just in case
            Thread.sleep(1000);
            System.err.println(e.getLocalizedMessage());

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            return new ResponseEntity<>(e.getLocalizedMessage().getBytes("ASCII"), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
