package io.paulbaker.springmazes;

import io.paulbaker.springmazes.algorithms.CellAlgorithms;
import io.paulbaker.springmazes.structures.BasicCartesianGrid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(method = RequestMethod.GET, path = "/image")
    public ResponseEntity<byte[]> image() throws IOException, InterruptedException {
        return image(4, 6);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/image", params = {"rows", "columns"})
    @ResponseBody
    public ResponseEntity<byte[]> image(@RequestParam(name = "rows") int rows, @RequestParam(name = "columns") int columns) throws IOException, InterruptedException {

        try {
            BasicCartesianGrid requestedMaze = new BasicCartesianGrid(rows, columns);
            requestedMaze.forEach(CellAlgorithms.BINARY_TREE);
            BufferedImage bufferedImage = requestedMaze.toDisplayImage();
            {
                // Dumping to file
                File outputFile = new File("save.png");
                ImageIO.write(bufferedImage, "png", outputFile);
            }
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", bao);
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(bao.toByteArray(), headers, HttpStatus.CREATED);

        } catch (Throwable e) {
            Thread.sleep(1000);
            System.err.println(e.getLocalizedMessage());
            throw e;
        }
    }
}
