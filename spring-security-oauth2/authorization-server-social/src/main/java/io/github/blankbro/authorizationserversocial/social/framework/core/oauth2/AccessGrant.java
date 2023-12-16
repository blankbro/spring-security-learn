package io.github.blankbro.authorizationserversocial.social.framework.core.oauth2;

import lombok.Data;

@Data
public class AccessGrant {

	private final String registrationId;
	private final String providerUserId;
	private final String accessToken;
	private final String refreshToken;
	private final Long expireTime;

	public AccessGrant(String registrationId, String providerUserId, String accessToken) {
		this(registrationId, providerUserId, accessToken, null, null);
	}

	public AccessGrant(String registrationId, String providerUserId, String accessToken, String refreshToken, Long expiresIn) {
		this.registrationId = registrationId;
		this.providerUserId = providerUserId;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expireTime = expiresIn;
	}
	
}
