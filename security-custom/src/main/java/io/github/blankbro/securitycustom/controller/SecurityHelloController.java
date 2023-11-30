package io.github.blankbro.securitycustom.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityHelloController {

    @GetMapping("/hello")
    public String hello(){
        SecurityContextHolder.getContext().getAuthentication().getName();
        return "Hello Security";
    }
}
