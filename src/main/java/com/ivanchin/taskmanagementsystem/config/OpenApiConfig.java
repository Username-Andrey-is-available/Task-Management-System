package com.ivanchin.taskmanagementsystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Task Management System",
                description = "test task for Effective Mobile", version = "1.0.0",
                contact = @Contact(
                        name = "Ivanchin Andrey",
                        email = "andreyivanchin000@gmail.com",
                        url = "https://t.me/gore_NeBeda"
                )
        )
)
public class OpenApiConfig {
}
