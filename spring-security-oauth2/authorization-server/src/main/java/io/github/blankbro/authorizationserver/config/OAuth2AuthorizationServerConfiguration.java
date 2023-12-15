package io.github.blankbro.authorizationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.annotation.Resource;

@EnableAuthorizationServer
@Configuration
public class OAuth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Resource(name = "myClientDetailsService")
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    /**
     * 用来配置令牌端点（Token Endpoint）的安全约束
     *
     * @param security a fluent configurer for security features
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()") // /oauth/token_key 公开访问
                .checkTokenAccess("permitAll()") // /oauth/check_token 公开访问
                .allowFormAuthenticationForClients(); // 表单认证，申请令牌
    }

    /**
     * 用来配置客户端详情服务(ClientDetailsService) ，客户端详情信息在这里进行初始化
     * 你能够把客户端详情信息写死在这里，或者是通过数据库来存储调取详情信息。
     * 只有注册过的 Client 才能从 authorization server 申请 access token
     *
     * @param clients the client details configurer
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 用来配置令牌 (token) 的访问端点和令牌服务(token services).
     *
     * @param endpoints the endpoints configurer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // .pathMapping("/oauth/confirm_access", "/custom/confirm_access") // 自定义授权同意页面
                .authenticationManager(authenticationManager) // 认证管理器
                .userDetailsService(userDetailsService) // 密码模式用户信息查询
                .authorizationCodeServices(authorizationCodeServices) // 授权码服务
                .tokenServices(authorizationServerTokenServices) // 令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST)
        ;
    }


}
