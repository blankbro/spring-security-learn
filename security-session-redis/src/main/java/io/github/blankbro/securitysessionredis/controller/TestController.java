package io.github.blankbro.securitysessionredis.controller;

import com.alibaba.fastjson2.JSON;
import io.github.blankbro.securitysessionredis.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private FindByIndexNameSessionRepository findByIndexNameSessionRepository;

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user", Collections.singletonMap("id", "666"));
        Map<String, Object> result = new HashMap<>();
        result.put("id", session.getId());
        result.put("id(base64)", Base64Util.base64Encode(session.getId()));
        return JSON.toJSONString(result);
    }

    @GetMapping("/getsession")
    public String getsession(@RequestParam String username) {
        Map<String, Session> sessionData = findByIndexNameSessionRepository.findByPrincipalName(username);
        System.out.println("getsession...");
        return "getsession";
    }
}
