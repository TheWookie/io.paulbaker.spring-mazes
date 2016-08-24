package io.paulbaker.springmazes;

import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by paulbaker on 8/23/16.
 */
@Configuration
@ComponentScan
@Log4j
public class MazeConfig extends WebMvcConfigurerAdapter {

    public MazeConfig() {
        log.debug("MazeConfig instantiated");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.debug("Registering BufferedImageHttpMessageConverter");
        converters.add(new BufferedImageHttpMessageConverter());
    }
}
