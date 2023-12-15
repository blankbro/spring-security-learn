package io.github.blankbro.securitysession.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("123")
                .authorities("admin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .defaultSuccessUrl("/hello")
                .failureHandler(authenticationFailureHandler)
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/").permitAll() // “/” 路径不需要权限，即可访问
                .anyRequest().authenticated() // 其它路径需要认证之后才能访问
        ;

        /**
         * Session 管理
         * 初始化详见 {@link org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer#init(org.springframework.security.config.annotation.web.HttpSecurityBuilder)}
         */
        http.sessionManagement()
                /**
                 * 指定会话创建策略 详见：{@link org.springframework.security.config.http.SessionCreationPolicy}
                 * SessionCreationPolicy 为 ALWAYS 或 IF_REQUIRED 时，就会创建 session
                 * 最终创建 session 的地方在 {@link org.springframework.security.web.savedrequest.HttpSessionRequestCache#saveRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
                 */
                // .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .maximumSessions(2)
                .sessionRegistry(sessionRegistry)
        ;
    }
}
