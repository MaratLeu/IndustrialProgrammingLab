package org.example.web_rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WebRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebRestApplication.class, args);
    }
}