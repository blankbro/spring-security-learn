package io.github.blankbro.authorizationserversocial.social.framework.security;

import org.springframework.security.core.AuthenticationException;

import java.net.URL;

public class SocialAuthenticationRedirectException  extends AuthenticationException {

    private final String redirectUrl;

    public SocialAuthenticationRedirectException(URL redirectUrl) {
        this(redirectUrl.toString(), "");
    }

    public SocialAuthenticationRedirectException(String redirectUrl, String message) {
        super(message);
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
