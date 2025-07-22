package com.example.travelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TraveLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraveLogApplication.class, args);
    }
}
