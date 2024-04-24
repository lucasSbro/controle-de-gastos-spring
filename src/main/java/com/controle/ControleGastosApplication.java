package com.controle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableMongoRepositories(basePackages = "com.controle.repository")
public class ControleGastosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleGastosApplication.class, args);
	}

}
