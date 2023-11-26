package io.github.blankbro.securitycustomauthorization.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Security";
    }

    // 方式5. @EnableGlobalMethodSecurity(securedEnabled = true) + @Secured()  方法执行之前做权限拦截
    // 注：@Secured() 角色名需要加前缀 ROLE_
    // @Secured({"ROLE_admin", "ROLE_root"})
    // 方式6. @EnableGlobalMethodSecurity(prePostEnabled = true) + @PreAuthorize()  方法执行之前做权限拦截
    // @PreAuthorize("hasAnyAuthority('admin')")
    // 方式7. @EnableGlobalMethodSecurity(prePostEnabled = true) + @PostAuthorize()  方法执行之后做权限拦截
    @PostAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/update")
    public String update() {
        System.out.println("update...");
        return "update";
    }
}
