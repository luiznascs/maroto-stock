package com.javaday.maroto_stock.config.documentation

import io.swagger.v3.oas.models.OpenAPI
import org.springframework.context.annotation.Bean
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .openapi("3.1.0")
            .info(
                Info()
                    .title("Meu App API")
                    .version("v1")
                    .description("Documentação da API do Meu App")
            )
    }
}