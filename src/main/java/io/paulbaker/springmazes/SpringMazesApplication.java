package io.paulbaker.springmazes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SpringMazesApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringMazesApplication.class, args);

//        BasicCartesianGrid grid = new BasicCartesianGrid(2, 2);
//        BufferedImage bufferedImage = grid.toDisplayImage();
//        File outputFile = new File("save.png");
//        ImageIO.write(bufferedImage, "png", outputFile);
    }
}
