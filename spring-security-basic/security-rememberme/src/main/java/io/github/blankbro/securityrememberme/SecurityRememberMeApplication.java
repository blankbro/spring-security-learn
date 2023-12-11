package io.github.blankbro.securityrememberme;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("io.github.blankbro.securityrememberme.mapper")
@SpringBootApplication
public class SecurityRememberMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityRememberMeApplication.class, args);
    }

}
