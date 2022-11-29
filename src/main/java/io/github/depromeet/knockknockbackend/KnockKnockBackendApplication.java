package io.github.depromeet.knockknockbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
public class KnockKnockBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnockKnockBackendApplication.class, args);
    }

}
