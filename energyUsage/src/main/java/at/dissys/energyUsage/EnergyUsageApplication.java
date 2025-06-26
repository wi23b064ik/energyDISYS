package at.dissys.energyUsage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "at.dissys.energyUsage")
@EntityScan(basePackages = "at.dissys.energyUsage")
@EnableScheduling
public class EnergyUsageApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnergyUsageApplication.class, args);
    }
    @Bean
    CommandLineRunner testConnection(HourlyUsageRepository repo) {
        return args -> {
            System.out.println("Usage entries in DB: " + repo.count());
        };
    }

}

