package io.github.blankbro.securitycustomauthentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 方式二：通过配置类，自定义用户名和密码
 * 使用方式二之后，方式一配置的用户会无效
 */
// @Configuration
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 认证的时候用 PasswordEncoder.matches(明文密码, 密文密码) 方法校验密码是否正确
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin-configuration")
                // 密码保存的时候要加密保存
                .password(passwordEncoder.encode("123"))
                .roles("admin");
    }
}
