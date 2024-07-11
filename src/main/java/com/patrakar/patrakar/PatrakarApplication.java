package com.patrakar.patrakar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PatrakarApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatrakarApplication.class, args);
	}

}
