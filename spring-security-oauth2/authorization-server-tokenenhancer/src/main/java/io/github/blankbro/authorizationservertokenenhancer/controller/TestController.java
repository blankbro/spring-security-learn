package io.github.blankbro.authorizationservertokenenhancer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String index() {
        return "This is index.";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Security";
    }

    @GetMapping("/test")
    public String test() {
        return "Hello TokenEnhancer";
    }
}
