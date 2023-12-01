package io.github.blankbro.securitysessionredis.vo;

import io.github.blankbro.securitysessionredis.util.Base64Util;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.session.Session;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Data
public class SessionVO {
    private String id;
    private String idOfBase64;
    private String username;
    private String lastAccessedTime;
    private String expirationTime;

    public SessionVO(Session session) {
        SecurityContext securityContext = session.getAttribute(WebSessionServerSecurityContextRepository.DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME);

        this.id = session.getId();
        this.idOfBase64 = Base64Util.base64Encode(session.getId());
        this.username = securityContext.getAuthentication().getName();
        this.lastAccessedTime = session.getLastAccessedTime()
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.expirationTime = session.getLastAccessedTime()
                .plusSeconds(session.getMaxInactiveInterval().getSeconds())
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
