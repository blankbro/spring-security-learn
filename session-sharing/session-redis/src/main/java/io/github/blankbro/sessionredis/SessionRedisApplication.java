package io.github.blankbro.sessionredis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.servlet.Filter;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class SessionRedisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SessionRedisApplication.class, args);
        log.info("=========>>> successful <<<=========");
        log.info("=========>>> successful <<<=========");
        Map<String, Filter> filterMap = applicationContext.getBeansOfType(Filter.class);
        log.info("=========>>> successful <<<=========");
    }

}
