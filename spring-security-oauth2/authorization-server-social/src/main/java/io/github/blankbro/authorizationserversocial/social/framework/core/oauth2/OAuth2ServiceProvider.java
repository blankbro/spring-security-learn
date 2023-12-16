package io.github.blankbro.authorizationserversocial.social.framework.core.oauth2;


import io.github.blankbro.authorizationserversocial.social.framework.core.ServiceProvider;

public interface OAuth2ServiceProvider<A> extends ServiceProvider<A> {

	OAuth2Operations getOAuthOperations();

	A getApi(String registrationId, String providerUserId);

}
