package io.github.blankbro.securityauthorization;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

// @EnableGlobalMethodSecurity(securedEnabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan("io.github.blankbro.securityauthorization.mapper")
@SpringBootApplication
public class SecurityAuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityAuthorizationApplication.class, args);
    }

}
