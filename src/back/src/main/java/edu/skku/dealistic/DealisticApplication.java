package edu.skku.dealistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackages = "edu.skku.dealistic")
public class DealisticApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealisticApplication.class, args);
    }

}
