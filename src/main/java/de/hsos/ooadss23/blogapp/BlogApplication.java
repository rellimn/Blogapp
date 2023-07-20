package de.hsos.ooadss23.blogapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Application, Einstiegspunkt der App
 * @author Roman Wasenmiller
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class BlogApplication {
    /**
     * Main-Methode
     * @param args Kommandozeilenparameter
     */
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }


}
