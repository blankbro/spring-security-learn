package io.github.blankbro.authorizationserversocial.social.framework.config;

import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class DefaultSocialRepository implements SocialRepository, Iterable<ClientRegistration> {

    private final Map<String, ClientRegistration> registrations;

    private final Map<String, Map<String, ClientRegistration>> providers;

    public DefaultSocialRepository(Map<String, ClientRegistration> registrations, Map<String, Map<String, ClientRegistration>> providers) {
        Assert.notNull(registrations, "registrations cannot be null");
        Assert.notNull(providers, "providers cannot be null");
        this.registrations = Collections.unmodifiableMap(registrations);
        this.providers = Collections.unmodifiableMap(providers);
    }

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        Assert.hasText(registrationId, "registrationId cannot be empty");
        return this.registrations.get(registrationId);
    }

    @Override
    public Map<String, ClientRegistration> getRegistrations() {
        return registrations;
    }

    @Override
    public Map<String, Map<String, ClientRegistration>> getProviders() {
        return providers;
    }

    @Override
    public Iterator<ClientRegistration> iterator() {
        return this.registrations.values().iterator();
    }
}
