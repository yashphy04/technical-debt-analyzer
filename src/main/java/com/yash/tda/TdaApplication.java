package com.yash.tda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.yash.tda.repository")
public class TdaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TdaApplication.class, args);
	}

}