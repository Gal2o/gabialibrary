package gabia.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookRequestApplication {
    public static void main(String[] args) { SpringApplication.run(BookRequestApplication.class, args); }
}
