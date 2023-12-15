package io.github.blankbro.authorizationserverjwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.Filter;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class SecurityOauth2AuthorizationServerJwtApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SecurityOauth2AuthorizationServerJwtApplication.class, args);
        log.info("=========>>> successful <<<=========");
        log.info("=========>>> successful <<<=========");
        // Spring Security 本质就是一连串的过滤器，下面就是所有的过滤器链了
        Map<String, Filter> filterMap = applicationContext.getBeansOfType(Filter.class);
        FilterChainProxy springSecurityFilterChain = (FilterChainProxy) filterMap.get("springSecurityFilterChain");
        List<SecurityFilterChain> filterChains = springSecurityFilterChain.getFilterChains();
        log.info("=========>>> successful <<<=========");
    }
}
