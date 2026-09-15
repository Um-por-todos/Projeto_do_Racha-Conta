package com.br.edu.iff.rachaconta.webproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI rachaContaOpenApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Racha-Conta API")
                .version("v1")
                .description("API REST para gerenciamento de moradores, casas, despesas, dívidas e pagamentos."));
    }
}
