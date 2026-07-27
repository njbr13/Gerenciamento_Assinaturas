package com.nilton.gerenciamento_assinatura;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GerenciamentoAssinaturaApplication {

	@RequestMapping("/")
	String time(){
		return "Melhor time:ggggg ";
	}

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoAssinaturaApplication.class, args);

	}

}
