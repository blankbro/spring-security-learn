package io.github.blankbro.securitycustomlogin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("io.github.blankbro.securitycustomlogin.mapper")
@SpringBootApplication
public class SecurityCustomLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityCustomLoginApplication.class, args);
    }

}
