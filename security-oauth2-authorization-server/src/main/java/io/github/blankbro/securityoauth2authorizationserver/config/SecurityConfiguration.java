package io.github.blankbro.securityoauth2authorizationserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionRegistry sessionRegistry;

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
                .permitAll();
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll();
        http.authorizeRequests()
                .antMatchers("/", "/hello").permitAll()
                .antMatchers("/getsession").hasAuthority("admin")
                .anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/unauth.html");
        http.csrf().disable();

        http.sessionManagement()
                // 用于设置允许同一用户在多个设备/浏览器同时登录的最大会话数。超过这个数量,最早的会话将被终止。
                .maximumSessions(2)
                // 用于将创建和删除的会话注册到SessionRegistry中。SessionRegistry可以用于跟踪会话,例如查找处于活动状态的所有会话或特定principal的会话数。
                .sessionRegistry(sessionRegistry);
    }
}
