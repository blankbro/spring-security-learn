package io.github.blankbro.authorizationserversocial.social.framework.config;

import java.util.Map;

public interface SocialRepository {
    ClientRegistration findByRegistrationId(String registrationId);

    Map<String, ClientRegistration> getRegistrations();

    Map<String, Map<String, ClientRegistration>> getProviders();
}
