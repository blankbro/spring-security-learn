package io.github.blankbro.authorizationserversocial.social.framework.security;

import io.github.blankbro.authorizationserversocial.social.framework.core.connect.Connection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;

public class SocialAuthenticationToken extends AbstractAuthenticationToken {

    private final String providerId;
    private final Serializable principle;
    private final Connection<?> connection;

    public SocialAuthenticationToken(final Connection<?> connection) {
        super(null);
        Assert.notNull(connection, "SocialAuthenticationToken: connection can't be null !");
        this.connection = connection;
        this.providerId = connection.getKey().getProviderId();
        this.principle = null;
        super.setAuthenticated(false);
    }

    public SocialAuthenticationToken(final Connection<?> connection, final Serializable details, final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        Assert.notNull(connection, "SocialAuthenticationToken: connection can't be null !");
        this.connection = connection;
        this.providerId = connection.getKey().getProviderId();
        this.principle = details;
        super.setAuthenticated(true);
    }


    public String getProviderId() {
        return providerId;
    }

    public Object getCredentials() {
        return null;
    }

    public Serializable getPrincipal() {
        return principle;
    }

    public Connection<?> getConnection() {
        return connection;
    }

    @Override
    public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {
        if (!isAuthenticated) {
            super.setAuthenticated(false);
        } else if (!super.isAuthenticated()) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
    }
}
