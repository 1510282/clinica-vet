package es.universidad.murcia.hospital_clinico.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Hospital Clinico")
                        .version("1.0")
                        .description("Documentación de la API para el Hospital Clinico"));
    }
}
