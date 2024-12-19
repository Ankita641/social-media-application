package com.learning.socialmediablog.social_media_blog_app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springdoc.core.GroupedOpenApi;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Social Media Blog API",
                version = "v1.0",
                description = "This API allows users to create, fetch, update, and delete posts and comments on a social media blog platform.",
               // license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT"),
                contact = @Contact(
                        name = "Ankita Patel",
                        email = "ankitak9891@gmail.com",
                        url = "https://ankitaportfolio.com"
                )
        ),
        servers = @Server(url = "http://localhost:8080/v1/api", description = "Local Server")
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi postsApi() {
        return GroupedOpenApi.builder()
                .group("Posts API")
                .packagesToScan("com.learning.socialmediablog.social_media_blog_app.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi commentsApi() {
        return GroupedOpenApi.builder()
                .group("Comments API")
                .packagesToScan("com.learning.socialmediablog.social_media_blog_app.controller")
                .build();
    }
}
