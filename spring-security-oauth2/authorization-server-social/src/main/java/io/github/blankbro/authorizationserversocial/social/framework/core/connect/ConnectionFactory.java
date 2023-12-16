package io.github.blankbro.authorizationserversocial.social.framework.core.connect;

import io.github.blankbro.authorizationserversocial.social.framework.core.ServiceProvider;

public abstract class  ConnectionFactory<A> {

    private final String providerId;

    private final ServiceProvider<A> serviceProvider;

    private final ApiAdapter<A> apiAdapter;

    public ConnectionFactory(String providerId, ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter) {
        this.providerId = providerId;
        this.serviceProvider = serviceProvider;
        this.apiAdapter = apiAdapter;
    }

    public String getProviderId() {
        return providerId;
    }

    protected ServiceProvider<A> getServiceProvider() {
        return serviceProvider;
    }

    protected ApiAdapter<A> getApiAdapter() {
        return apiAdapter;
    }
}
