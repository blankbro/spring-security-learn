package io.github.blankbro.authorizationserversocial.social.framework.core.oauth2;


public interface OAuth2Operations {

    AccessGrant exchangeForAccess(String registrationId, String authorizationCode);

}
