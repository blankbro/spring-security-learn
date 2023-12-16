package io.github.blankbro.authorizationserversocial.social.framework.security;

import io.github.blankbro.authorizationserversocial.social.framework.core.connect.support.ConnectionFactoryRegistry;
import io.github.blankbro.authorizationserversocial.social.framework.security.provider.SocialAuthenticationService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SocialAuthenticationServiceRegistry extends ConnectionFactoryRegistry implements SocialAuthenticationServiceLocator {

    private final Map<String, SocialAuthenticationService<?>> authenticationServices = new HashMap<>();

    public SocialAuthenticationService<?> getAuthenticationService(String providerId) {
        SocialAuthenticationService<?> authenticationService = authenticationServices.get(providerId);
        if (authenticationService == null) {
            throw new IllegalArgumentException("No authentication service for service provider '" + providerId + "' is registered");
        }
        return authenticationService;
    }

    public void addAuthenticationService(SocialAuthenticationService<?> authenticationService) {
        addConnectionFactory(authenticationService.getConnectionFactory());
        authenticationServices.put(authenticationService.getConnectionFactory().getProviderId(), authenticationService);
    }

    public void setAuthenticationServices(Iterable<SocialAuthenticationService<?>> authenticationServices) {
        for (SocialAuthenticationService<?> authenticationService : authenticationServices) {
            addAuthenticationService(authenticationService);
        }
    }

    public Set<String> registeredAuthenticationProviderIds() {
        return authenticationServices.keySet();
    }
}
