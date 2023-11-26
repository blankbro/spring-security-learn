package io.github.blankbro.securitycustomrememberme;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("io.github.blankbro.securitycustomrememberme.mapper")
@SpringBootApplication
public class SecurityCustomRemembermeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityCustomRemembermeApplication.class, args);
    }

}
