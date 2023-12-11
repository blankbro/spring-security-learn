package io.github.blankbro.securityauthenticationloginpage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityHelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello Security";
    }
}
