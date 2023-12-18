package io.github.blankbro.authorizationservertokenenhancer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 只有请求 /oauth/token 接口的时候（也就是申请 token 的时候）才会走到这里，其它情况不会走到这里
 */
@Slf4j
@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        OAuth2AccessToken enhanceOAuth2AccessToken = accessToken;
        log.info("CustomTokenEnhancer...");
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            enhanceOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
            Map<String, Object> additionalInformation = enhanceOAuth2AccessToken.getAdditionalInformation();
            additionalInformation.put("custom-enhance", "oh yeah");
        }
        return enhanceOAuth2AccessToken;
    }

}
