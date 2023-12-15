package io.github.blankbro.resourceserver;

import io.github.blankbro.resourceserver.config.AuthorizationServerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({AuthorizationServerProperties.class})
@SpringBootApplication
public class SecurityOauth2ResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityOauth2ResourceServerApplication.class, args);
    }

}
