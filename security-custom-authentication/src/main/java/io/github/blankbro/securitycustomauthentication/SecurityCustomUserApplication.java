package io.github.blankbro.securitycustomauthentication;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("io.github.blankbro.securitycustomauthentication.mapper")
@SpringBootApplication
public class SecurityCustomUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityCustomUserApplication.class, args);
    }

}
