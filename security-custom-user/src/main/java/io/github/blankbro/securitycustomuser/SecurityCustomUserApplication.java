package io.github.blankbro.securitycustomuser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("io.github.blankbro.securitycustomuser.mapper")
@SpringBootApplication
public class SecurityCustomUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityCustomUserApplication.class, args);
    }

}
