package io.github.blankbro.securityoauth2authorizationserverjwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // 从父类加载认证管理器
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/login_success.html")
                .failureHandler(authenticationFailureHandler)
                .permitAll();
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll();
        http.authorizeRequests()
                .antMatchers("/", "/hello").permitAll()
                .antMatchers("/getsession").hasAuthority("admin")
                .anyRequest().authenticated();
        http.exceptionHandling()
                // 发起授权时，如果没有登录会走到该处理器。一般情况下是需要跳转到登录页面的，此处不能直接返回 JSON 😂
                // .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .accessDeniedPage("/unauth.html");
        http.csrf().disable();

        http.sessionManagement()
                // 用于设置允许同一用户在多个设备/浏览器同时登录的最大会话数。超过这个数量,最早的会话将被终止。
                .maximumSessions(2)
                // 用于将创建和删除的会话注册到SessionRegistry中。SessionRegistry可以用于跟踪会话,例如查找处于活动状态的所有会话或特定principal的会话数。
                .sessionRegistry(sessionRegistry);

        // 资源服务器配置
        http.oauth2ResourceServer()
                // 此处为了方便，就共用了
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .jwt();
    }
}
