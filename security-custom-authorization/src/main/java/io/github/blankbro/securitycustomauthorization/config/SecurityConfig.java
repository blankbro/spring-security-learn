package io.github.blankbro.securitycustomauthorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/login_success.html")
                .permitAll();
        // 自定义退出登录逻辑
        http.logout()
                .logoutUrl("/logout") // 退出登录路径
                .logoutSuccessUrl("/") // 成功退出后，重定向地址。"/" 即 "/index.html"
                .permitAll(); // 放开 logoutUrl, logoutSuccessUrl 访问权限
        http.authorizeRequests()
                .antMatchers("/", "/hello").permitAll()
                // 自定义接口访问权限
                // 方式1. hasAuthority 表示 URL 只能通过特定角色访问，否则 403 forbidden
                // .antMatchers("/index").hasAuthority("admin")
                // 方式2. hasAnyAuthority 表示 URL 可通过任意一个角色访问，否则 403 forbidden
                // .antMatchers("/index").hasAnyAuthority("admin", "root")
                // 方式3. hasRole 表示 URL 只能通过特定角色访问，否则 403 forbidden。注意：角色名会加 ROLE_ 前缀
                // .antMatchers("/index").hasRole("admin")
                // 方式4. hasAnyRole 表示 URL 可通过任意一个角色访问，否则 403 forbidden。注意：角色名会加 ROLE_ 前缀
                // .antMatchers("/index").hasAnyRole("admin", "root")
                .anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/unauth.html");
        http.csrf().disable();
    }
}
