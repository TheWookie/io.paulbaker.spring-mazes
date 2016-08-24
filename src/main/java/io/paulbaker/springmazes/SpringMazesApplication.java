package io.paulbaker.springmazes;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;
import java.io.IOException;

@SpringBootApplication
@Log4j
public class SpringMazesApplication {

    @Bean
    public HttpMessageConverter<BufferedImage> bufferedImageHttpMessageConverter() {
        log.debug("Registering BufferedImage converter");
        return new BufferedImageHttpMessageConverter();
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringMazesApplication.class, args);
    }
}
