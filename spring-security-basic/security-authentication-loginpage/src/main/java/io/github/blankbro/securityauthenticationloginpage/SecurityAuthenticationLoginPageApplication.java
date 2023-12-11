package io.github.blankbro.securityauthenticationloginpage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("io.github.blankbro.securityauthenticationloginpage.mapper")
@SpringBootApplication
public class SecurityAuthenticationLoginPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityAuthenticationLoginPageApplication.class, args);
    }

}
