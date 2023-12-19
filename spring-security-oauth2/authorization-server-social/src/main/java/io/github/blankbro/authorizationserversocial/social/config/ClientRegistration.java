package io.github.blankbro.authorizationserversocial.social.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class ClientRegistration {

    private String registrationId;
    private String corpId;
    private String appId;
    private String appSecret;
    private String redirectUri;
    private String scope;
    private String provider;
    private String authorizationUri;
    private String tokenUri;
    private String userInfoUri;

}
