package footballcrawler;

import footballcrawler.service.FootballCrawlerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(FootballCrawlerService.class, args);
    }

}
