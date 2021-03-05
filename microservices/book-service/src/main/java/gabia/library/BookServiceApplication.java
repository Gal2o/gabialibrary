package gabia.library;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(BookServiceApplication.class, args);
    }

    // TODO: 추후에 공통으로 뺄 코드
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
