package com.example.miniproject14;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MiniProject14Application {

    public static void main(String[] args) {
        SpringApplication.run(MiniProject14Application.class, args);
    }

}
