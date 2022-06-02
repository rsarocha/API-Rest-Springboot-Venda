package br.com.venda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class VendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
