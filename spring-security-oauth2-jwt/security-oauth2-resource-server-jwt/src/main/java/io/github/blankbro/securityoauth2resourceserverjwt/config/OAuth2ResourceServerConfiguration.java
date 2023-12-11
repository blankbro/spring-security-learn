package io.github.blankbro.securityoauth2resourceserverjwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@EnableResourceServer
@Configuration
public class OAuth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    /**
     * tokenServices：ResourceServerTokenServices 类的实例，用来实现令牌服务，即如何验证令牌
     * tokenStore：TokenStore 类的实例，指定令牌如何访问，与 tokenServices 配置可选。
     * resourceId：这个资源服务的 ID，是可选的。但是推荐设置并在授权服务中进行验证。
     * tokenExtractor：令牌提取器用来提取请求中的令牌。
     *
     * @param resources configurer for the resource server
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                .resourceId("resource-id-01") // 资源 ID
                .tokenStore(tokenStore)
                .stateless(true); // 无状态模式，即不依靠 session
    }

    /**
     * Spring Security 相关配置
     *
     * @param http the current http filter configuration
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 校验请求
                .antMatchers("/resource-api/**") // 路径匹配规则
                .access("#oauth2.hasScope('resource-scope-01')"); // 需要匹配 Scope
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
