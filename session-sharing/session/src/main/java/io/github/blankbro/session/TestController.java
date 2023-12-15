package io.github.blankbro.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/hello-session")
    public String helloSession(HttpServletRequest request) {
        /**
         * 一个 SpringBoot Web 项目，只有 getSession() 的时候才会生成 session，否则不会
         * 所以，也就是只要调用了 getSession() Response 中就会 set-Cookie JSESSIONID
         */
        HttpSession session = request.getSession();
        log.info("sessionId = {}", session.getId());
        return "Hello Session";
    }

}
