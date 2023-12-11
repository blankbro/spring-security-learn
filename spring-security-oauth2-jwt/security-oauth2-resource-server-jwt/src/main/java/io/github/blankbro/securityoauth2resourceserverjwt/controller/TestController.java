package io.github.blankbro.securityoauth2resourceserverjwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/resource-api/test")
    public Object test() {
        return "Hello Spring Security OAuth2.0";
    }
}
