package org.example.casodeuso2.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração dos metadados da documentação OpenAPI do segundo caso de uso.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API REST - Caso de Uso 2: Monitoramento Ambiental",
                version = "1.0.0",
                description = "API para operações relacionadas ao monitoramento ambiental e às entidades associadas."
        )
)
public class OpenApiConfig {
}
