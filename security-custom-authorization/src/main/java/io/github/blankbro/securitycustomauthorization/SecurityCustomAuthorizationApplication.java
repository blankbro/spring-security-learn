package io.github.blankbro.securitycustomauthorization;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

// @EnableGlobalMethodSecurity(securedEnabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan("io.github.blankbro.securitycustomauthorization.mapper")
@SpringBootApplication
public class SecurityCustomAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityCustomAuthorizationApplication.class, args);
    }

}