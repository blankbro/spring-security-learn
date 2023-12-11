package io.github.blankbro.sessionredis.controller;

import com.alibaba.fastjson2.JSON;
import io.github.blankbro.sessionredis.util.Base64Util;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user", Collections.singletonMap("id", "666"));
        Map<String, Object> result = new HashMap<>();
        result.put("id", session.getId());
        result.put("id(base64)", Base64Util.base64Encode(session.getId()));
        return JSON.toJSONString(result);
    }
}
