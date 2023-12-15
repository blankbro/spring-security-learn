package io.github.blankbro.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.servlet.Filter;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class SessionApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SessionApplication.class, args);
        log.info("=========>>> successful <<<=========");
        log.info("=========>>> successful <<<=========");
        Map<String, Filter> filterMap = applicationContext.getBeansOfType(Filter.class);
        log.info("=========>>> successful <<<=========");
    }

}
