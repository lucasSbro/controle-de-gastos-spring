package com.controle;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Controle de Gastos API", version = "1.0", description = "dev. Lucas Sbroglia"))
@SecurityScheme(name = "contas", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)

//@EnableMongoRepositories(basePackages = "com.controle.repository")
public class ControleGastosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleGastosApplication.class, args);
	}
}
