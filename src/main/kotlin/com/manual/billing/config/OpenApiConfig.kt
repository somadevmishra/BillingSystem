package com.manual.billing.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Cafe Billing System API")
                    .version("1.0")
                    .description("REST APIs for cafe billing and inventory management")
                    .contact(
                        Contact()
                            .name("Development Team")
                            .email("support@example.com")
                    )
            )
    }
}