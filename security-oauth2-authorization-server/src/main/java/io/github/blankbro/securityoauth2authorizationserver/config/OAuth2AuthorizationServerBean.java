package io.github.blankbro.securityoauth2authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.sql.DataSource;

@Configuration
public class OAuth2AuthorizationServerBean {


    /**
     * 客户端详情服务
     *
     * @param dataSource
     * @param passwordEncoder
     * @return
     */
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource, PasswordEncoder passwordEncoder) {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
        return jdbcClientDetailsService;
    }

    /**
     * 令牌存储策略
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        // 内存存储令牌
        return new InMemoryTokenStore();
    }

    /**
     * 令牌管理服务
     *
     * @param clientDetailsService
     * @param tokenStore
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices(ClientDetailsService clientDetailsService, TokenStore tokenStore) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setSupportRefreshToken(true); // 是否允许令牌自动刷新
        tokenServices.setTokenStore(tokenStore); // 令牌存储策略
        tokenServices.setAccessTokenValiditySeconds(7200); // 令牌默认有效期 2 小时
        tokenServices.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期 3 天
        return tokenServices;
    }

    /**
     * 授权码服务
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        // 暂时用内存方式
        return new InMemoryAuthorizationCodeServices();
    }

}
