package com.meet.springref;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringRefApplication {

    static void main(String[] args) {
        SpringApplication.run(SpringRefApplication.class, args);
    }

}
