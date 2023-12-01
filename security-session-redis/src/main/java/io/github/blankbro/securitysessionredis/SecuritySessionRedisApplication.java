package io.github.blankbro.securitysessionredis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("io.github.blankbro.securitysessionredis.mapper")
@SpringBootApplication
public class SecuritySessionRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuritySessionRedisApplication.class, args);
	}

}
