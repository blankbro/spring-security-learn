package io.github.blankbro.authorizationserversocial.social.framework.core.connect.support;

import io.github.blankbro.authorizationserversocial.social.framework.core.connect.ApiAdapter;
import io.github.blankbro.authorizationserversocial.social.framework.core.connect.Connection;
import io.github.blankbro.authorizationserversocial.social.framework.core.connect.ConnectionFactory;
import io.github.blankbro.authorizationserversocial.social.framework.core.oauth2.AccessGrant;
import io.github.blankbro.authorizationserversocial.social.framework.core.oauth2.OAuth2Operations;
import io.github.blankbro.authorizationserversocial.social.framework.core.oauth2.OAuth2ServiceProvider;

public class OAuth2ConnectionFactory<S> extends ConnectionFactory<S> {

    private String scope = null;

    public OAuth2ConnectionFactory(String providerId, OAuth2ServiceProvider<S> serviceProvider, ApiAdapter<S> apiAdapter) {
        super(providerId, serviceProvider, apiAdapter);
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public OAuth2Operations getOAuthOperations() {
        return getOAuth2ServiceProvider().getOAuthOperations();
    }

    public Connection<S> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<S>(getProviderId(), accessGrant.getProviderUserId(),
                accessGrant.getRegistrationId(),
                accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(),
                accessGrant.getExpireTime(),
                getOAuth2ServiceProvider(),
                getApiAdapter());
    }

    private OAuth2ServiceProvider<S> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<S>) getServiceProvider();
    }

}
