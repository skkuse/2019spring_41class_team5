package edu.skku.dealistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application.
 *
 * @author Junhyun Kim
 */
@SpringBootApplication(scanBasePackages = "edu.skku.dealistic")
public class DealisticApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealisticApplication.class, args);
    }

}
