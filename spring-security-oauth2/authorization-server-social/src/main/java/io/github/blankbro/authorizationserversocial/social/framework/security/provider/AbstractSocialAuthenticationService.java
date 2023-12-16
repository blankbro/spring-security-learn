package io.github.blankbro.authorizationserversocial.social.framework.security.provider;

import io.github.blankbro.authorizationserversocial.social.framework.core.connect.Connection;
import org.springframework.beans.factory.InitializingBean;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractSocialAuthenticationService<S> implements SocialAuthenticationService<S>, InitializingBean {

    private ConnectionCardinality connectionCardinality = ConnectionCardinality.ONE_TO_ONE;

    private String connectionAddedRedirectUrl;

    public void afterPropertiesSet() throws Exception {
    }

    public ConnectionCardinality getConnectionCardinality() {
        return connectionCardinality;
    }

    public void setConnectionCardinality(ConnectionCardinality connectionCardinality) {
        if (connectionCardinality == null) {
            throw new NullPointerException("connectionCardinality");
        }
        this.connectionCardinality = connectionCardinality;
    }

    public String getConnectionAddedRedirectUrl(HttpServletRequest request, Connection<?> connection) {
        return connectionAddedRedirectUrl;
    }

    public void setConnectionAddedRedirectUrl(String connectionAddedRedirectUrl) {
        this.connectionAddedRedirectUrl = connectionAddedRedirectUrl;
    }

}
