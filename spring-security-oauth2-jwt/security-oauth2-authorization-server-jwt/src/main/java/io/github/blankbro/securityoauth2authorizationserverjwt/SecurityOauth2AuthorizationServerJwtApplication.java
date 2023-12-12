package io.github.blankbro.securityoauth2authorizationserverjwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Slf4j
@SpringBootApplication
public class SecurityOauth2AuthorizationServerJwtApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SecurityOauth2AuthorizationServerJwtApplication.class, args);
        log.info("=========>>> successful <<<=========");
        log.info("=========>>> successful <<<=========");
        // Spring Security 本质就是一连串的过滤器，下面就是所有的过滤器链了
        FilterChainProxy springSecurityFilterChain = applicationContext.getBean("springSecurityFilterChain", FilterChainProxy.class);
        List<SecurityFilterChain> filterChains = springSecurityFilterChain.getFilterChains();
        log.info("=========>>> successful <<<=========");
    }
}
