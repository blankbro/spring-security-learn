package io.github.blankbro.authorizationserversocial.social.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "social")
public class SocialProperties {

    private final Map<String, Registration> registration = new HashMap<>();
    private final Map<String, Provider> provider = new HashMap<>();

    @PostConstruct
    public void validate() {
        this.getRegistration().values().forEach(this::validateRegistration);
    }

    private void validateRegistration(Registration registration) {
        if (!StringUtils.hasText(registration.getProvider())) {
            throw new IllegalStateException("provider must not be empty.");
        }

        if (!StringUtils.hasText(registration.getCorpId())) {
            throw new IllegalStateException("corp id must not be empty.");
        }

        if (!StringUtils.hasText(registration.getAppId())) {
            throw new IllegalStateException("App id must not be empty.");
        }
    }

    @Data
    public static class Registration {
        private String provider;
        private String corpId;
        private String appId;
        private String appSecret;
        private String redirectUri;
    }

    @Data
    public static class Provider {
        private String scope;
        private String authorizationUri;
        private String tokenUri;
        private String userInfoUri;
    }

}
