package com.myquickfixj.springsecuritypractice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
            info= @Info(
                    title = "Spring Security Practice API",
                    description = "Spring Security Practice API Documentation created by Pranjal",
                    version = "1.0",
                    contact = @Contact(
                            name="Security App",
                            email = "scurity@securityapp.com",
                            url = "http://securityapp.com"
                    )
            ),
        security = @SecurityRequirement(
                name = "jwtScheme"
        )
)
@SecurityScheme(
        name = "jwtScheme",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class Config {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
