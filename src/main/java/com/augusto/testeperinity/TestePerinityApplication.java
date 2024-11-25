package com.augusto.testeperinity;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Teste Perinity",
		version = "1.0", description = "Documentação para API de gerenciamento de pessoas, tarefas e departamentos"))
@SpringBootApplication
public class TestePerinityApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestePerinityApplication.class, args);
	}

}