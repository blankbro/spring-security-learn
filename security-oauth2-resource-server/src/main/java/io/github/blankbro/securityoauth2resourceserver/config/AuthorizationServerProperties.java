package io.github.blankbro.securityoauth2resourceserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "authorization-server")
public class AuthorizationServerProperties {
    private String checkTokenUri;
    private String clientId;
    private String clientSecret;
}
