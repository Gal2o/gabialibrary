package gabia.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication(exclude = { KafkaAutoConfiguration.class })
public class AlertServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlertServiceApplication.class, args);
    }

}