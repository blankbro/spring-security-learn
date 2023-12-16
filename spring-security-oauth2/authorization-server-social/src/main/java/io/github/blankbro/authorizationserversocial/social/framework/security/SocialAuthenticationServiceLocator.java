package io.github.blankbro.authorizationserversocial.social.framework.security;

import io.github.blankbro.authorizationserversocial.social.framework.core.connect.ConnectionFactoryLocator;
import io.github.blankbro.authorizationserversocial.social.framework.security.provider.SocialAuthenticationService;

import java.util.Set;

public interface SocialAuthenticationServiceLocator extends ConnectionFactoryLocator {

    SocialAuthenticationService<?> getAuthenticationService(String providerId);

    Set<String> registeredAuthenticationProviderIds();
}
