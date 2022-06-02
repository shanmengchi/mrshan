package com.shmc.mrshan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MrshanApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrshanApplication.class, args);
    }

}
