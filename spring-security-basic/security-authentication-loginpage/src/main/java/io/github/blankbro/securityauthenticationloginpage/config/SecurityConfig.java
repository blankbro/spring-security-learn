package io.github.blankbro.securityauthenticationloginpage.config;

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
        http
                // 自定义登录页面，以及跳转逻辑
                .formLogin()
                .loginPage("/login.html") // 登录页面
                .loginProcessingUrl("/login") // 登录访问路径（POST 方式），不需要我们自己实现，Spring Security 帮我们实现了
                // .successForwardUrl("/index") // 登录成功后的转发路径。转发的时候会拿着 /login 接口的请求方式、请求参数等数据去请求 /index
                .defaultSuccessUrl("/index") // 登录成功后的重定向路径。
                .usernameParameter("username") // 用户名的参数名
                .passwordParameter("password") // 密码的参数名
                .permitAll() // 为 loginPage, loginProcessingUrl, failureUrl 放开访问权限
                // 自定义请求的访问控制规则
                .and().authorizeRequests()
                .antMatchers("/", "/hello").permitAll() // 匹配路径，并开放访问
                .anyRequest().authenticated() // 匹配其它任意路径，并需要认证才能通过
                // 跨域
                .and().csrf().disable() // 关闭跨域防护，有啥作用？注释之后登录成功没有跳转到/index，而是回到了/login.html
        ;
    }
}
