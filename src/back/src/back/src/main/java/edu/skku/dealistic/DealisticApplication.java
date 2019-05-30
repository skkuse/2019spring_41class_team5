package edu.skku.dealistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"controller","service"})
public class DealisticApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealisticApplication.class, args);
    }

}
