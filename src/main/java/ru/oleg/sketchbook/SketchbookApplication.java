package ru.oleg.sketchbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@SpringBootApplication
public class SketchbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(SketchbookApplication.class, args);
    }

}
