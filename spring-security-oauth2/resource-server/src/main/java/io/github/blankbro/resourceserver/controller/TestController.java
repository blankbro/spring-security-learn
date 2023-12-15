package io.github.blankbro.resourceserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/resource-api/test")
    public Object test() {
        return "Hello Spring Security OAuth2.0";
    }
}
