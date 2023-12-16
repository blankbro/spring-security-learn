package io.github.blankbro.authorizationserversocial.social.framework.security.provider;

import io.github.blankbro.authorizationserversocial.social.framework.core.connect.Connection;
import io.github.blankbro.authorizationserversocial.social.framework.core.connect.ConnectionFactory;
import io.github.blankbro.authorizationserversocial.social.framework.security.SocialAuthenticationRedirectException;
import io.github.blankbro.authorizationserversocial.social.framework.security.SocialAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SocialAuthenticationService<S> {
    enum ConnectionCardinality {
        /**
         * only one connected providerUserId per userId and vice versa
         */
        ONE_TO_ONE(false, false),

        /**
         * many connected providerUserIds per userId, but only one userId per providerUserId
         */
        ONE_TO_MANY(false, true),

        /**
         * only one providerUserId per userId, but many userIds per providerUserId.
         * Authentication of users not possible
         */
        MANY_TO_ONE(true, false),

        /**
         * no restrictions. Authentication of users not possible
         */
        MANY_TO_MANY(true, true);

        private final boolean multiUserId;
        private final boolean multiProviderUserId;

        private ConnectionCardinality(boolean multiUserId, boolean multiProviderUserId) {
            this.multiUserId = multiUserId;
            this.multiProviderUserId = multiProviderUserId;
        }

        public boolean isMultiUserId() {
            return multiUserId;
        }

        public boolean isMultiProviderUserId() {
            return multiProviderUserId;
        }

        public boolean isAuthenticatePossible() {
            return !isMultiUserId();
        }
    }

    ConnectionCardinality getConnectionCardinality();

    ConnectionFactory<S> getConnectionFactory();

    SocialAuthenticationToken getAuthenticationToken(String registration,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) throws SocialAuthenticationRedirectException;

    String getConnectionAddedRedirectUrl(HttpServletRequest request, Connection<?> connection);
}
