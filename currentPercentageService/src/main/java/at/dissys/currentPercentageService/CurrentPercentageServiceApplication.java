package at.dissys.currentPercentageService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"at.dissys.currentPercentageService"})
@EnableJpaRepositories(basePackages = {"at.dissys.currentPercentageService"})
public class CurrentPercentageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrentPercentageServiceApplication.class, args);
    }

}
