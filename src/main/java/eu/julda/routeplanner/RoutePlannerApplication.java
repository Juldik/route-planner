package eu.julda.routeplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableFeignClients
public class RoutePlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoutePlannerApplication.class, args);
	}

}
