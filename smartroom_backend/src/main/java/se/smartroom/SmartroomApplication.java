package se.smartroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartroomApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartroomApplication.class, args);
	}
}
