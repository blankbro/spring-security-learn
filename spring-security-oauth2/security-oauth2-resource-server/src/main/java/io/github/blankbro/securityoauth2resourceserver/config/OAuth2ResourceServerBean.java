package io.github.blankbro.securityoauth2resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
public class OAuth2ResourceServerBean {

    @Bean
    public ResourceServerTokenServices resourceServerTokenServices(AuthorizationServerProperties authorizationServerProperties) {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl(authorizationServerProperties.getCheckTokenUri());
        tokenServices.setClientId(authorizationServerProperties.getClientId());
        tokenServices.setClientSecret(authorizationServerProperties.getClientSecret());
        return tokenServices;
    }

}
