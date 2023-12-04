package io.github.blankbro.securitysessionredis.controller;

import com.alibaba.fastjson2.JSON;
import io.github.blankbro.securitysessionredis.util.Base64Util;
import io.github.blankbro.securitysessionredis.vo.SessionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class SessionController {

    // 使用了 @EnableRedisHttpSession 才能注入 FindByIndexNameSessionRepository
    @Autowired
    private FindByIndexNameSessionRepository findByIndexNameSessionRepository;

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/session/test")
    public String test(HttpServletRequest request) {
        HttpSession session = request.getSession();

        Map<String, String> user = new HashMap<>();
        user.put("id", "666");
        session.setAttribute("user", user);

        Map<String, Object> result = new HashMap<>();
        result.put("id", session.getId());
        result.put("id(base64)", Base64Util.base64Encode(session.getId()));

        return JSON.toJSONString(result);
    }

    @GetMapping("/session/all")
    public Object allSession() {
        List<SessionVO> result = new ArrayList<>();

        log.info("get all session");
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        for (Object principal : allPrincipals) {
            List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(principal, false);
            for (SessionInformation sessionInformation : sessionInformations) {
                SessionVO sessionVO = new SessionVO();
                sessionVO.setId(sessionInformation.getSessionId());
                sessionVO.setIdOfBase64(Base64Util.base64Encode(sessionInformation.getSessionId()));
                sessionVO.setUsername(((User) sessionInformation.getPrincipal()).getUsername());
                sessionVO.setLastAccessedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sessionInformation.getLastRequest()));
                result.add(sessionVO);
            }
        }

        return result;
    }

    @GetMapping("/session/get")
    public Object getSession(@RequestParam String username) {
        Map<String, Session> sessionMap = findByIndexNameSessionRepository.findByPrincipalName(username);
        log.info("get {} session", username);

        Map<String, SessionVO> result = new HashMap<>();
        sessionMap.forEach((sessionId, session) -> {
            result.put(sessionId, new SessionVO(session));
        });

        return result;
    }

    @DeleteMapping("/session/del")
    public String delSession(@RequestParam String username) {
        Map<String, Session> sessionMap = findByIndexNameSessionRepository.findByPrincipalName(username);
        log.info("del {} session", username);

        sessionMap.forEach((sessionId, session) -> {
            findByIndexNameSessionRepository.deleteById(sessionId);
        });

        return String.format("del %s session of %s", sessionMap.size(), username);
    }
}
