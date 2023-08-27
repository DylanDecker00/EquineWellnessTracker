package equinetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // This annotation implicitly defines @ComponentScan, @EnableAutoConfiguration, and @SpringBootConfiguration.
public class EquineWellnessTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquineWellnessTrackerApplication.class, args);
    }
}
