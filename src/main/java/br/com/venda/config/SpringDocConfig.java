package br.com.venda.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

	// http://localhost:8080/swagger-ui/index.html
	@Bean
	public GroupedOpenApi swagger() {
		return GroupedOpenApi.builder()
				.group("br.com.venda")
				.packagesToScan("br.com.venda.controller")
				.build();
	}
}
