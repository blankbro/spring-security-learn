package io.github.blankbro.authorizationserversocial.social.framework.security.provider;

import io.github.blankbro.authorizationserversocial.social.framework.core.connect.Connection;
import io.github.blankbro.authorizationserversocial.social.framework.core.connect.support.OAuth2ConnectionFactory;
import io.github.blankbro.authorizationserversocial.social.framework.core.oauth2.AccessGrant;
import io.github.blankbro.authorizationserversocial.social.framework.security.SocialAuthenticationRedirectException;
import io.github.blankbro.authorizationserversocial.social.framework.security.SocialAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class OAuth2AuthenticationService<S> extends AbstractSocialAuthenticationService<S> {

	private final OAuth2ConnectionFactory<S> connectionFactory;

	public OAuth2AuthenticationService(OAuth2ConnectionFactory<S> connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public OAuth2ConnectionFactory<S> getConnectionFactory() {
		return connectionFactory;
	}

	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		Assert.notNull(getConnectionFactory(), "connectionFactory can't be null!");
	}

	public SocialAuthenticationToken getAuthenticationToken(String registrationId, HttpServletRequest request, HttpServletResponse response) throws SocialAuthenticationRedirectException {
		String code = request.getParameter("code");
		if (!StringUtils.hasText(code)) {
			throw new SocialAuthenticationRedirectException("/login?error", "code is null!");
		}

		try {
			// AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(registrationId, code);
			// Connection<S> connection = connectionFactory.createConnection(accessGrant);
			// return new SocialAuthenticationToken(connection);
		} catch (RestClientException e) {
			log.error("failed to exchange for access", e);
			throw new SocialAuthenticationRedirectException("/login?error", "failed to exchange for access!");
		}
		return null;
	}

}
