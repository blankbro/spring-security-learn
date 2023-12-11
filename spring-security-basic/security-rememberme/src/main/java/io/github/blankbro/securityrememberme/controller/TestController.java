package io.github.blankbro.securityrememberme.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Security";
    }

    @GetMapping("/update")
    public String update() {
        System.out.println("update...");
        return "update";
    }
}
